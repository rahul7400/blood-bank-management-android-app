package in.macrocodes.bloodbankmanage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HospitalMainAdapter extends RecyclerView.Adapter<HospitalMainAdapter.Viewholder> {
    private List<UserModel> mList = new ArrayList<>();
    private Context mContext;
    public HospitalMainAdapter(HospitalMainPage hospitalMainPage, List<UserModel> mList) {
        mContext = hospitalMainPage;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospitalview,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        UserModel userModel = mList.get(position);
        holder.bloodGroup.setVisibility(View.VISIBLE);
        holder.adharCard.setVisibility(View.VISIBLE);


        holder.name.setText("Donar Name - "+userModel.getName());
        holder.city.setText("Donar City - "+userModel.getCity());
        holder.number.setText("Donar Contact - "+userModel.getPhone());
        holder.bloodGroup.setText("Donor Blood Group - "+userModel.getBloodgroup());
        holder.adharCard.setText("Donar Adharcard no. - "+userModel.getAdharcard());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+userModel.getPhone()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        private TextView name,city,number,bloodGroup,adharCard;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.hname);
            number = (TextView) itemView.findViewById(R.id.hnumber);
            city = (TextView) itemView.findViewById(R.id.hcity);
            bloodGroup = (TextView) itemView.findViewById(R.id.bloodgroup);
            adharCard = (TextView) itemView.findViewById(R.id.adharCard);
        }
    }
}
