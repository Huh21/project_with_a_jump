package org.ict.project_with_a_jump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView_id = (TextView) findViewById(R.id.textView_id);
        TextView textView_pw = (TextView) findViewById(R.id.textView_pw);
        TextView textView_ad = (TextView) findViewById(R.id.textView_ad);
        TextView textView_ph = (TextView) findViewById(R.id.textView_ph);
        Intent intent_01 = getIntent();
        Intent intent_02 = getIntent();
        String id= intent_01.getStringExtra("입력한 이름 ");
        String pw = intent_01.getStringExtra("입력한 비밀번호 출력");
        String ad= intent_01.getStringExtra("입력한 주소 ");
        String ph = intent_02.getStringExtra("입력한 번호");
        textView_id.setText(String.valueOf(id));
        textView_pw.setText(String.valueOf(pw));
        textView_ph.setText(String.valueOf(ad));
        textView_ad.setText(String.valueOf(ad));
    }
    public void onClick_back(View v){
        finish();
    }

}