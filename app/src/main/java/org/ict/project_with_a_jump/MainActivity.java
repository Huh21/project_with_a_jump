package org.ict.project_with_a_jump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    Button registerButton;
    Button loginButton;
    private Button login;
    private EditText email_login;
    private EditText pwd_login;
    FirebaseAuth firebaseAuth;

    //유종: set 시설 이름
    String placeName;
    DatabaseReference rootRef;
    DatabaseReference uidRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //유종: 시설 이름 표시
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootRef= FirebaseDatabase.getInstance().getReference();
        uidRef= rootRef.child("project_with_a_jump").child("UserAccount:").child(uid).child("companyName");
        uidRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    String companyName= snapshot.getValue(String.class);
                    placeName=companyName;
                }else{
                    placeName="snapshot is null";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        login=(Button)findViewById(R.id.login);
        email_login=(EditText) findViewById(R.id.email_login);
        pwd_login=(EditText)findViewById(R.id.pwd_login);
        firebaseAuth=firebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=email_login.getText().toString().trim();
                String pwd=pwd_login.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email,pwd)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                                    intent.putExtra("place",placeName);
                                    startActivity(intent);
                                    //Toast.makeText(MainActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        registerButton=findViewById(R.id.사업자register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

}