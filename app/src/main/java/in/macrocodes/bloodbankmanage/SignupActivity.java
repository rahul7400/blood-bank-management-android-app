package in.macrocodes.bloodbankmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private LinearLayout individual,hospital;
    private EditText name,email,password,city,phoneNumber2,hospitalreffNo,adharcardNo,bloodGroup;
    private Button regBtn;
    String accType;
    ProgressDialog mRegProgress;
    private RadioGroup type;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        regBtn = (Button) findViewById(R.id.register);
        individual = (LinearLayout) findViewById(R.id.individual);
        hospital = (LinearLayout) findViewById(R.id.hospital);
        name = (EditText) findViewById(R.id.reg_name);
        email = (EditText)findViewById(R.id.reg_email);
        city = (EditText)findViewById(R.id.reg_city);
        password = (EditText) findViewById(R.id.reg_pass);
        phoneNumber2 = (EditText) findViewById(R.id.number);
        hospitalreffNo = (EditText) findViewById(R.id.hospitalregno);
        adharcardNo = (EditText) findViewById(R.id.adharCard);
        bloodGroup = (EditText) findViewById(R.id.bloodgroup);
        mRegProgress = new ProgressDialog(this);
        type = (RadioGroup) findViewById(R.id.type);



        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked){
                    if (checkedRadioButton.getText().toString().equals("Hospital")){
                        hospital.setVisibility(View.VISIBLE);
                        individual.setVisibility(View.GONE);
                        accType = "Hospital";
                    }else{
                        hospital.setVisibility(View.GONE);
                        individual.setVisibility(View.VISIBLE);
                        accType = "User";
                    }
                }
            }
        });



        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String display_name=name.getText().toString();
                final String  email2=email.getText().toString();
                final String password2=password.getText().toString();
                final String city2=city.getText().toString();
                final String phoneNumber=phoneNumber2.getText().toString();
                final String hospitalref=hospitalreffNo.getText().toString();
                String adharCard=adharcardNo.getText().toString();
                final String bloodGroup2=bloodGroup.getText().toString();



                if(!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email2)
                        || !TextUtils.isEmpty(password2)|| !TextUtils.isEmpty(phoneNumber)
                        || !TextUtils.isEmpty(city2)) {

                    if (accType.equals("Hospital")){
                        if (!TextUtils.isEmpty(hospitalref)){
                            mRegProgress.setTitle("Registering User");
                            mRegProgress.setMessage("Please wait while we create your account !");
                            mRegProgress.setCanceledOnTouchOutside(false);
                            mRegProgress.show();

                            register_hospital(display_name, email2, password2, city2
                                    ,phoneNumber,hospitalref,accType);
                        }
                    }else{
                        if (!TextUtils.isEmpty(adharCard) || !TextUtils.isEmpty(bloodGroup2)){
                            mRegProgress.setTitle("Registering User");
                            mRegProgress.setMessage("Please wait while we create your account !");
                            mRegProgress.setCanceledOnTouchOutside(false);
                            mRegProgress.show();

                            register_user(display_name, email2, password2, city2
                                    ,phoneNumber,bloodGroup2,adharCard,accType);
                        }
                    }




                  /* LayoutInflater inflater = getLayoutInflater();
                    View alertLayout= inflater.inflate(R.layout.processing_dialog,null);
                    AlertDialog.Builder show = new AlertDialog.Builder(RegisterActivity.this);
                    show.setView(alertLayout);
                    show.setCancelable(false);
                    dialog_verifying = show.create();
                    dialog_verifying.show();*/
            }
        }
    });
    }
    private void register_hospital(final String display_name, final String email, String password
              ,final String city,String phoneNumber,String hospitalref,String type) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    // dialog_verifying.dismiss();

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    assert current_user != null;
                    String uid = current_user.getUid();



                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("name", display_name);
                    userMap.put("email", email);
                    userMap.put("city", city);
                    userMap.put("phone", phoneNumber);
                    userMap.put("hospitalref", hospitalref);
                    userMap.put("type", type);
                    userMap.put("uid", uid);



                    mDatabase.child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                mRegProgress.hide();
                                Intent mainIntent = new Intent(SignupActivity.this, HospitalMainPage.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();

                            }

                        }
                    });



                } else {

                    // dialog_verifying.hide();
                    String task_result = task.getException().getMessage().toString();
                    mRegProgress.hide();

                }

            }
        });

    }

    private void register_user(final String display_name, final String email, String password
            ,final String city,String phoneNumber,String bloodgroup,String adharcard,String type) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    // dialog_verifying.dismiss();

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    assert current_user != null;
                    String uid = current_user.getUid();



                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("name", display_name);
                    userMap.put("email", email);
                    userMap.put("city", city);
                    userMap.put("phone", phoneNumber);
                    userMap.put("bloodgroup", bloodgroup);
                    userMap.put("donator", "no");
                    userMap.put("adharcard", adharcard);
                    userMap.put("type", type);
                    userMap.put("uid", uid);



                    mDatabase.child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                mRegProgress.hide();
                                Intent mainIntent = new Intent(SignupActivity.this, UserMainPage.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();

                            }

                        }
                    });



                } else {

                    // dialog_verifying.hide();
                    String task_result = task.getException().getMessage().toString();
                    mRegProgress.hide();

                }

            }
        });

    }
}