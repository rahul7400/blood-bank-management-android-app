package in.macrocodes.bloodbankmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DonateBlood extends AppCompatActivity {

    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);
        cancel = (Button) findViewById(R.id.cancel);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("donator","yes");
        reference.child(currentUser).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                cancel.setVisibility(View.VISIBLE);
                Toast.makeText(DonateBlood.this, "Successfully Registered for blood donation", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("donator","no");
                reference.child(currentUser).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        cancel.setVisibility(View.GONE);
                        Toast.makeText(DonateBlood.this, "Blood Donation Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}