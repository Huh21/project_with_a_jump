package org.ict.project_with_a_jump;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity<editText, editText2> extends AppCompatActivity {

    // 파이어베이스 데이터베이스 연동
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    Button check;
    EditText user_id,user_ad,user_sepw;


    public static WebView web;
    public static WebSettings webSettings;
    private String Validation = "^.(?=.*[0-9])(?=.*[0-9ㄱ-ㅎ가-힣]).*$";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_id = findViewById(R.id.user_id);
        user_ad = findViewById(R.id.user_ad);
        user_sepw = findViewById(R.id.user_sepw);


        user_sepw.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Pattern p = Pattern.compile("^[0-9ㄱ-ㅎ가-힣]+$");
                    Matcher m = p.matcher((user_sepw).getText().toString());
                    if ( !m.matches()){
                        Toast.makeText(MainActivity.this, "안심번호 형식에 맞춰서 입력해주세요", Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //에딧 텍스트 값을 문자열로 바꾸어 함수에 넣어줍니다.
                addUser(user_id.getText().toString(), user_sepw.getText().toString(), user_ad.getText().toString());
            }
        });

    }

    private void addUser(String name, String num, String address) {
        User User = new User(name, num, address);
    }



    public void onClick_login(View v) {
        String id= user_id.getText().toString();
        String sepw= user_sepw.getText().toString();
        String ad= user_ad.getText().toString();
        Intent intent_01= new Intent(getApplicationContext(), subActivity.class);
        intent_01.putExtra("입력한 이름",id);
        intent_01.putExtra("입력한 안심번호",sepw);
        intent_01.putExtra("입력한 주소",ad);
        startActivity(intent_01);
    }

}