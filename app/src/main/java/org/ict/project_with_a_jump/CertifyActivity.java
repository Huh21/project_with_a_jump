package org.ict.project_with_a_jump;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CertifyActivity<editText, editText2> extends AppCompatActivity {
    public static WebView web;
    public static WebSettings webSettings;
    EditText editText;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certify);
    }

    private void singUp() {
        String id = ((EditText) findViewById(R.id.user_id)).getText().toString();

    }

    public void onClick_login(View v) {
        EditText user_id = (EditText) findViewById(R.id.user_id);
        EditText user_ad = (EditText) findViewById(R.id.user_ad);
        EditText user_password = (EditText) findViewById(R.id.user_password);
        String id = user_id.getText().toString();
        String pw = user_password.getText().toString();
        String ad = user_ad.getText().toString();
        Intent intent_01 = new Intent(getApplicationContext(), SubActivity.class);
        intent_01.putExtra("입력한 이름", id);
        intent_01.putExtra("입력한 패스워드", pw);
        intent_01.putExtra("입력한 주소", ad);
        startActivity(intent_01);
    }
}