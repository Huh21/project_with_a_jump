package org.ict.project_with_a_jump;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
   Button nextbut, registerbut;
   private EditText user_name, user_ad,user_ce;
   private EditText user_email, user_pwd;
   private FirebaseAuth mFirebaseAuth;
   private DatabaseReference mDatabaseRef;
   private String Validation = "^.(?=.*[0-9])(?=.*[0-9ㄱ-ㅎ가-힣]).*$";
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      user_name = (EditText) findViewById(R.id.user_name);
      user_ad = (EditText) findViewById(R.id.user_ad);
      user_ce = (EditText) findViewById(R.id.user_ce);
      user_email = (EditText) findViewById(R.id.user_email);
      user_pwd = (EditText) findViewById(R.id.user_pwd);
      registerbut = findViewById(R.id.registerbut);
      nextbut = findViewById(R.id.nextbut);

      mFirebaseAuth = FirebaseAuth.getInstance();
      mDatabaseRef = FirebaseDatabase.getInstance().getReference("project_with_a_jump").child("User"); //리얼타임 파이어베이스 변경 가능?

      user_ce.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
               Pattern p = Pattern.compile("^[0-9ㄱ-ㅎ가-힣]+$");
               Matcher m = p.matcher((user_ce).getText().toString());
               if (!m.matches()) {
                  Toast.makeText(MainActivity.this, "안심번호 형식에 맞춰서 입력해주세요", Toast.LENGTH_SHORT).show();

               }

            }
         }
      });


      registerbut.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            //회원가입 처리 시작
            String email = user_email.getText().toString();
            String pwd = user_pwd.getText().toString();
            String name = user_name.getText().toString();
            String num = user_ce.getText().toString();
            String address = user_ad.getText().toString();

            //Firebase Auth 진행
            mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {
                     FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                     User User = new User();
                     User.setIdToken(firebaseUser.getUid());
                     User.setEmailId(firebaseUser.getEmail());
                     User.setPassword(pwd);
                     User.setName(name);
                     User.setAddress(address);
                     User.setNum(num);


                     //setvalue(): database에 insert(삽입)
                     mDatabaseRef.child(firebaseUser.getUid()).setValue(User);

                     Toast.makeText(MainActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                  } else {
                     Toast.makeText(MainActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                  }
               }

            });


         }

      });
      nextbut.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
            Intent intent_01 = new Intent(getApplicationContext(), subActivity.class);
            intent_01.putExtra("name", user_name.getText().toString());
            intent_01.putExtra("address",user_ad.getText().toString());
            intent_01.putExtra("num", user_ce.getText().toString());
            startActivity(intent_01);
         }
      });
   }
}
