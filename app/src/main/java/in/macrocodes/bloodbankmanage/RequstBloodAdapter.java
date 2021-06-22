package in.macrocodes.bloodbankmanage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class RequstBloodAdapter extends RecyclerView.Adapter<RequstBloodAdapter.Viewholder>{
    private List<HospitalModel>mHlist = new ArrayList<>();
    private Context mContext;
    public RequstBloodAdapter(AppCompatActivity activity, List<HospitalModel> mHlist) {

        this.mHlist = mHlist;
        this.mContext = activity;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospitalview,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        HospitalModel hospitalModel = mHlist.get(position);
        holder.name.setText("Hospital Name - "+hospitalModel.getName());
        holder.city.setText("Hospital City - "+hospitalModel.getCity());
        holder.number.setText("Hospital Contact - "+hospitalModel.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+hospitalModel.getPhone()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHlist.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        private TextView name,city,number;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.hname);
            number = (TextView) itemView.findViewById(R.id.hnumber);
            city = (TextView) itemView.findViewById(R.id.hcity);
        }
    }
}
