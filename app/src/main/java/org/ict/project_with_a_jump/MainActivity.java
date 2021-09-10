package org.ict.project_with_a_jump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button registerButton;
    Button loginButton;
    private Button login;
    private EditText email_login;
    private EditText pwd_login;
    FirebaseAuth firebaseAuth; //firebase 인스턴스 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=(Button)findViewById(R.id.login);
        email_login=(EditText) findViewById(R.id.email_login);
        pwd_login=(EditText)findViewById(R.id.pwd_login);

        //firebaseauth 인스턴스 초기화

        firebaseAuth=firebaseAuth.getInstance();

        //버튼 누름
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //text를 문자열로 가져옴

                String email=email_login.getText().toString().trim();
                String pwd=pwd_login.getText().toString().trim();

                //signinwithemailandpassword 메서드 사용
                //addOnCompleteListener, onComplete 메서드 사용


                firebaseAuth.signInWithEmailAndPassword(email,pwd)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, HomeDefault.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });


        registerButton=findViewById(R.id.사업자register);

        //setonclicklistener :버튼 클릭 이벤트 처리
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
