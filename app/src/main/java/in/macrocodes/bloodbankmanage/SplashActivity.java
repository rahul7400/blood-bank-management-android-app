package in.macrocodes.bloodbankmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser == null){
            Intent startIntent = new Intent(this, MainActivity.class);
            startActivity(startIntent);
            finish();

        } else{
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(currentUser.getUid());

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild("type")){
                        String type = Objects.requireNonNull(snapshot.child("type").getValue()).toString();
                        if (type.equalsIgnoreCase("Hospital")){

                            Intent startIntent = new Intent(SplashActivity.this, HospitalMainPage.class);
                            startActivity(startIntent);
                            finish();

                        }else{

                            Intent startIntent = new Intent(SplashActivity.this, UserMainPage.class);
                            startActivity(startIntent);
                            finish();
                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}