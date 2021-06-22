package in.macrocodes.bloodbankmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestBlood extends AppCompatActivity {
    String check = "no";
    TextView txt2;
    String bloodg;
    private EditText cityName;
    private RadioGroup bloodGroups;
    private Button Search;
    private RequstBloodAdapter mAdapter;
    private final List<HospitalModel> mHlist = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHlist.clear();
        setContentView(R.layout.activity_request_bloof);
        txt2 = (TextView) findViewById(R.id.txt2);
        cityName = (EditText) findViewById(R.id.city);
        Search = (Button) findViewById(R.id.search);
        bloodGroups = (RadioGroup) findViewById(R.id.bloodGroups);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new RequstBloodAdapter(RequestBlood.this, mHlist);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.setAdapter(mAdapter);

        bloodGroups.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    bloodg = checkedRadioButton.getText().toString();
                }
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityName.getText().toString();
                if (bloodg != null && city != null) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                getCount(dataSnapshot.getKey());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }


    private void getCount(String key) {
        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {

                String type = Objects.requireNonNull(snapshot2.child("type").getValue()).toString();
                if (!type.equals("Hospital")) {
                    UserModel userModel = snapshot2.getValue(UserModel.class);
                    assert userModel != null;
                    if (userModel.getDonator().equalsIgnoreCase("yes")) {
                        String city2 = cityName.getText().toString();
                        if (userModel.getCity().equalsIgnoreCase(city2) && bloodg.equals(userModel.getBloodgroup())) {
                            showHospital();
                            txt2.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            txt2.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showHospital() {
        mHlist.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    reference.child(Objects.requireNonNull(dataSnapshot.getKey())).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            String type = Objects.requireNonNull(snapshot.child("type").getValue()).toString();
                            if (type.equals("Hospital")) {

                                HospitalModel hospitalModel = snapshot.getValue(HospitalModel.class);
                                String city2 = cityName.getText().toString();
                                assert hospitalModel != null;

                                if (hospitalModel.getCity().equalsIgnoreCase(city2)) {
                                    mHlist.add(hospitalModel);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}