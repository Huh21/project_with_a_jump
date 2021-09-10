package org.ict.project_with_a_jump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SetUp extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        // 툴바
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로 가기 버튼 설정

        //'개인정보 변경' 버튼을 클릭했을 때
        Button button1= findViewById(R.id.buttonFirst);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(getApplicationContext(),PersonalInfo.class);
                startActivity(intent1);
            }
        });

        //'영업시간 변경' 버튼을 클릭했을 때
        Button button2= findViewById(R.id.buttonSecond);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2= new Intent(getApplicationContext(),ChangeOfficeHour.class);
                startActivity(intent2);
            }
        });

        //'명부 작성 가능 시간 변경' 버튼을 클릭했을 때
        Button button3= findViewById(R.id.buttonThird);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3= new Intent(getApplicationContext(),ChangeListableTime.class);
                startActivity(intent3);
            }
        });
    }

    // 툴바에 뒤로가기 버튼 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}