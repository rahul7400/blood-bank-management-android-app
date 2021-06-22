package in.macrocodes.bloodbankmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserMainPage extends AppCompatActivity {
    ImageButton request_btn,donate_btn;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);

        donate_btn = (ImageButton) findViewById(R.id.btn_donate);
        request_btn = (ImageButton) findViewById(R.id.btn_req);
        logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent startIntent = new Intent(UserMainPage.this,MainActivity.class);
                startActivity(startIntent);
                finish();
            }
        });



        donate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserMainPage.this,DonateBlood.class);
                startActivity(intent);
            }
        });

        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainPage.this,RequestBlood.class);
                startActivity(intent);
            }
        });
    }
}