package org.ict.project_with_a_jump;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity2 extends AppCompatActivity {
    private EditText name2, ad2, num, phoneNumber;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name2 = findViewById(R.id.name2);
        ad2 = findViewById(R.id.ad2);
        num = findViewById(R.id.num);
        phoneNumber = findViewById(R.id.phoneNumber);
        DatabaseReference mDatabase;

    }

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 로그인한 유저의 정보 가져오기
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();




}