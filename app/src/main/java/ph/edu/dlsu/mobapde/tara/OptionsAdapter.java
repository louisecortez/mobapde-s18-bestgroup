package ph.edu.dlsu.mobapde.tara;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 10/11/2017.
 */

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    ArrayList<String> data;

    public OptionsAdapter (ArrayList<String> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // of swapping data into the text view
        if(position%2==0){
            holder.tvContent.setBackgroundColor(Color.parseColor("#cdcdcd"));
        }

        Log.i("blah", position+"");
        final String currentOption = data.get(position);
        holder.tvContent.setText(currentOption);

        //pass data
        holder.tvContent.setTag(currentOption);

        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // view is whatever view this listener was assigned to
                // in our case it is tvFood

                //get data
                String s = (String) v.getTag();
                Log.i("adapter", "user clicked " + s);
                onItemClickListener.onItemClick(s);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);

            tvContent = (TextView) itemView.findViewById(R.id.tv_content);

        }
    }

    public interface OnItemClickListener{
        public void onItemClick(String s);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
