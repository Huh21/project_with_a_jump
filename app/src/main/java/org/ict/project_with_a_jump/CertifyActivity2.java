package org.ict.project_with_a_jump;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CertifyActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certify2);

        TextView textView_id = (TextView) findViewById(R.id.textView_id);
        TextView textView_pw = (TextView) findViewById(R.id.textView_pw);
        TextView textView_ad = (TextView) findViewById(R.id.textView_ad);
        TextView textView_ph = (TextView) findViewById(R.id.textView_ph);
        Button button2 = (Button) findViewById(R.id.button2);
        Intent intent_01 = getIntent();
        Intent intent_02 = getIntent();
        String id = intent_01.getStringExtra("입력한 이름 ");
        String pw = intent_01.getStringExtra("입력한 비밀번호 출력");
        String ad = intent_01.getStringExtra("입력한 주소 ");
        String ph = intent_02.getStringExtra("입력한 번호");
        textView_id.setText(String.valueOf(id));
        textView_pw.setText(String.valueOf(pw));
        textView_ph.setText(String.valueOf(ad));
        textView_ad.setText(String.valueOf(ad));

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // 본인인증 할 때 사용자로부터 받은 사용자 이름을 사용자 홈 화면으로 넘기는 코드
                // user_name이라는 이름으로 넘겨주시면 감사하겠습니다
                intent.putExtra("user_name", "허희원");
                startActivity(intent);
            }
        });
    }

    public void onClick_back(View v) {
        finish();
    }
}