package org.ict.project_with_a_jump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class subActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
    }

    public void onClick_cf(View v) {
        EditText user_pnumber  = (EditText) findViewById(R.id.user_pnumber);
        Intent intent_02= new Intent(getApplicationContext(), MainActivity2.class);
        String ph= user_pnumber.getText().toString();
        intent_02.putExtra("입력한 번호",ph);
        startActivity(intent_02);
    }
}