package in.macrocodes.bloodbankmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HospitalMainPage extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<UserModel> mList = new ArrayList<>();
    int count=0;
    String Mycity;
    private Button logout;
    Button getList;
    private HospitalMainAdapter mAdpater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_main_page);
        mList.clear();
        getList = (Button) findViewById(R.id.getlist);
        mRecyclerView=(RecyclerView) findViewById(R.id.view2);
        logout = (Button) findViewById(R.id.logout);
        mAdpater = new HospitalMainAdapter(HospitalMainPage.this,mList);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.setAdapter(mAdpater);
        getMyCity();

        getList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent startIntent = new Intent(HospitalMainPage.this,MainActivity.class);
                startActivity(startIntent);
                finish();
            }
        });

    }
    private void getMyCity(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Mycity = snapshot.child("city").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    getUsers(snapshot1.getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUsers(String key) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String type = Objects.requireNonNull(snapshot.child("type").getValue()).toString();
                if (type.equalsIgnoreCase("User")){
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    assert userModel != null;
                    if (userModel.getDonator().equalsIgnoreCase("yes")){
                        if (userModel.getCity().equalsIgnoreCase(Mycity)){
                            mList.add(userModel);
                            mAdpater.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}