package org.ict.project_with_a_jump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity2 extends AppCompatActivity {
    private EditText name2, ad2, num, phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name2=findViewById(R.id.name2);
        ad2=findViewById(R.id.ad2);
        num=findViewById(R.id.num);
        phoneNumber=findViewById(R.id.phoneNumber);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in

            if (user != null) {
                // Name, email address, and profile photo Url
                String name2 = user.getDisplayName();
                ; // 사용자의 이름
                String num = user.getUid(); // 사용자의 개인안심번호
                String ad2 = user.getDisplayName();


                // Check if user's email is verified
                boolean emailVerified = user.isEmailVerified();

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getIdToken() instead.
                String uid = user.getUid();
            }

        }
    }


}