package ph.edu.dlsu.mobapde.tara;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import ph.edu.dlsu.mobapde.tara.R;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvsignup;
    TextView tvBack;

    Button btlogin;
    EditText etemail;
    EditText etpassword;

    ProgressBar progressbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        tvsignup = (TextView) findViewById(R.id.tv_signup);
        tvsignup.setOnClickListener(this);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btlogin = (Button) findViewById(R.id.bt_login);
        btlogin.setOnClickListener(this);

        etemail = (EditText) findViewById(R.id.et_email);
        etpassword = (EditText) findViewById(R.id.et_password);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

    }


    private void loginUser(){

        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString();

        if(email.isEmpty()){
            etemail.setError("Email is required");
            etemail.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.setError("Email is invalid");
            etemail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etpassword.setError("Password is required");
            etpassword.requestFocus();
            return;

        }


        if (password.length() < 6){
            etpassword.setError("Password should be equal or more than 6 characters");
            etpassword.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressbar.setVisibility(View.GONE);
                    Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.tv_signup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.bt_login:
                loginUser();
                break;
        }
    }
}