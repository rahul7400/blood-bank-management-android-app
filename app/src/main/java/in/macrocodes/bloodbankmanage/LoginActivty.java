package in.macrocodes.bloodbankmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivty extends AppCompatActivity {

    private EditText mEmail,mPassword;
    private Button login;
    private ProgressDialog mRegProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);
        mEmail = (EditText) findViewById(R.id.reg_email);
        mPassword = (EditText) findViewById(R.id.reg_pass);
        login = (Button) findViewById(R.id.login);
        mRegProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString();
                String password=mPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()){

                    mRegProgress.setTitle("Loggin In User");
                    mRegProgress.setMessage("Please wait while we log you in !");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    loginUser(email,password);
                }
                else {
                    Toast.makeText(LoginActivty.this, "Please fill all info", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loginUser(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    mRegProgress.hide();
                    String current_user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                    reference.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String type = snapshot.child("type").getValue().toString();
                            if (type.equals("Hospital")){

                                Intent mainIntent = new Intent(LoginActivty.this, HospitalMainPage.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }else{
                                Intent mainIntent = new Intent(LoginActivty.this, UserMainPage.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
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
}