package ph.edu.dlsu.mobapde.tara;

import android.*;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleMap mMap;

    //Play Services location
    private static final int MY_PERMISSION_REQUEST_CODE = 1234;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 2629;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private static int UPDATE_INTERVAL = 5000;
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT= 10;

    DatabaseReference ref;

    DatabaseReference onlineRef, currentUserRef, counterRef; // from PlayersActivity
    ArrayList<Tracking> users = new ArrayList<>();              //made to get all users
    ArrayList<Marker> markers = new ArrayList<>();
    com.firebase.geofire.GeoFire geofire;
    Marker myCurrLoc;

    VerticalSeekBar mSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        ref = FirebaseDatabase.getInstance().getReference("Users");

        //added these stuff
        onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected");
        counterRef = FirebaseDatabase.getInstance().getReference("lastOnline");
        currentUserRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()); //creates child in lastonline with key uid

        geofire = new com.firebase.geofire.GeoFire(ref);

        mSeekBar = (VerticalSeekBar) findViewById(R.id.verticalSeekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(progress), 2000, null);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setupLocation();
    }

    //Press Ctrl+O


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(checkPlayServices()){
                        buildGoogleApiClient();
                        createLocationRequest();
                        displayLocation();
                    }
                }
                break;
        }
    }

    //this checks if the app can access the coarse and the fine location
    private void setupLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //REQUEST RUNTIME PERMISSION
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);



        }
        else{
            if(checkPlayServices()){
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }


    }

    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLastLocation != null){
            final double latitude = mLastLocation.getLatitude();
            final double longitude = mLastLocation.getLongitude();

            ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(new Tracking(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            String.valueOf(mLastLocation.getLatitude()),String.valueOf(mLastLocation.getLongitude()
                    ), "Active"));


            // ______________________________________________________________________________
            onlineRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue(Boolean.class)){
                        currentUserRef.onDisconnect().removeValue(); // delete old value

                        //adds an online user to the list
                        counterRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(new Tracking(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                        String.valueOf(mLastLocation.getLatitude()),String.valueOf(mLastLocation.getLongitude()
                                ), "Active"));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //____________________________________________________________________


            //ref.addValueEventListener(new ValueEventListener() {
            counterRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                        Tracking note = noteSnapshot.getValue(Tracking.class);
                        if (!users.contains(note)) //added this
                            users.add(note);


                       /*
                        //update the location in firebase
                        geofire.setLocation("You", new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {
                                //add the marker
                                if(myCurrLoc != null)
                                    myCurrLoc.remove(); //removes the outdated marker
                                myCurrLoc = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(latitude, longitude))
                                        .title("You"));


                                //move camera to this position
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12.0f));
                            }
                        });
                        */
                        //Log.d("hey", String.format("Your location was changed %f/%f", latitude, longitude));
                        //track.add(note);
                    }

                    for (int i=0; i<users.size(); i++){
                        Log.d("ITO", users.get(i).getEmail() + " is here");
                        Marker m = null;    //******************* added this
                        if(myCurrLoc != null)
                            m.remove(); //removes the outdated marker
                        m = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(users.get(i).getLat()), Double.parseDouble(users.get(i).getLng())))
                                .title(users.get(i).getEmail())
                                .snippet(users.get(i).getEmail()));
                        markers.add(m);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            //List<Tracking> track = new ArrayList<>();

/*
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                        Tracking note = noteSnapshot.getValue(Tracking.class);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Integer.parseInt(note.getLat()), Integer.parseInt(note.getLng()))).title(note.getEmail()));



                        //update the location in firebase
                        geofire.setLocation("You", new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {
                                //add the marker
                                if(myCurrLoc != null)
                                    myCurrLoc.remove(); //removes the outdated marker
                                myCurrLoc = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(latitude, longitude))
                                        .title("You"));


                                //move camera to this position
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12.0f));
                            }
                        });

                        Log.d("hey", String.format("Your location was changed %f/%f", latitude, longitude));
                        //track.add(note);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        */
        }
        else
            Log.d("hey", "Cannot get your location");
    }

    //sets up the locaiton request
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

    }

    //This method creates a user who will use the API of Google
    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    //This method checks if the device is supported
    private boolean checkPlayServices() {
        // GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) is depracated daw
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            else{
                Toast.makeText(this, "This device is not supported", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;

        }
        return true;
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //create an area where you will get a notif

        LatLng notifArea = new LatLng(14.657540, 121.013261);    //BASTA DAPAT PWEDENG PALITAN 'TO!!!
        mMap.addCircle(new CircleOptions()
                .center(notifArea)
                .radius(500) //THIS IS IN METERS PO
                .strokeColor(Color.BLUE)
                .fillColor(0x220000FF)
                .strokeWidth(5.0f)
        );

        //ADD GEOQUERY HERE
        //0.05f = 50 meters

        GeoQuery geoQuery = geofire.queryAtLocation(new GeoLocation(notifArea.latitude, notifArea.longitude), 0.5f);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                sendNotification("hey", String.format("Almost there, %s !", key));

            }

            @Override
            public void onKeyExited(String key) {
                sendNotification("hey", String.format("But... %s ?", key));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                Log.d("move",String.format("yas we moved to this location: [%f/%f]", location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                Log.e("error", ""+error);
            }
        });

    }

    private void sendNotification(String hey, String format) {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(hey)
                .setContentText(format);
        NotificationManager notifManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MapsActivity.class);

        // FLAG_IMMUTABLE > FLAG_UPDATE_CURRENT
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);

        Notification notification = builder.build();

        //notification.flags != Notification.FLAG_AUTO_CANCEL;
        //notification.defaults != Notification.DEFAULT_SOUND;

        notifManager.notify(new Random().nextInt(), notification);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();
    }
}