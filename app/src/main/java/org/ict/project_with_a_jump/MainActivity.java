package org.ict.project_with_a_jump;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //영업시간 설정
    private static final String OPEN = "8:30:00";
    private static final String CLOSE = "19:30:00";

    Date nowTime = null;
    Date openTime = null;
    Date closingTime = null;
    int openCompare;
    int closingCompare;

    TextView clock1, clock2;
    private static Handler handler;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 툴바
        toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getActionBar().setDisplayShowTitleEnabled(false);


        clock2=(TextView)findViewById(R.id.nowTime2);

        //오늘 날짜
        clock1=(TextView)findViewById(R.id.nowTime1);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1= new SimpleDateFormat("yyyy년 MM월 dd일");
        String dateString = sdf1.format(cal.getTime());
        clock1.setText(dateString);

        //현재 시간
        SimpleDateFormat sdf2= new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf3= new SimpleDateFormat("a hh시 mm분 ss초");
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                    try {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String rightNow= getNowTime(sdf2);
                                clock2.setText(getNowTime(sdf3));
                                showMessage(sdf2,rightNow);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        });
        th.start();

    }

    //현재시간 가져오기
    public String getNowTime(SimpleDateFormat sdf) {
        long time= System.currentTimeMillis();
        Date now = new Date();
        String nowString = sdf.format(new Date(time));

        return nowString;
    }

    //현재시간,영업시간 비교
    public void showMessage(SimpleDateFormat sdf, String nowString){
        try {
            nowTime = sdf.parse(nowString);
            openTime = sdf.parse(OPEN);
            closingTime = sdf.parse(CLOSE);
            openCompare = nowTime.compareTo(openTime);
            closingCompare = nowTime.compareTo(closingTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView state = findViewById(R.id.state);
        //현재시간,영업시간 비교

        if(openCompare>0){
            if(closingCompare<0){
                state.setText("전자출입명부 작성 중입니다.");
            }else if(closingCompare>0){
                state.setText("전자출입명부 작성 시간이 아닙니다.");
            }else{
                state.setText("영업이 종료되었습니다.");
            }
        }else if(openCompare<0){
            state.setText("전자출입명부 작성 시간이 아닙니다.");
        }else{
            state.setText("영업이 시작되었습니다.");
        }
    }

    // 옵션 메뉴 관련 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_button,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setup:
                Intent setupIntent = new Intent(getApplicationContext(), SetUp.class);
                startActivity(setupIntent);
                return true;
            case R.id.history:
                Intent historyIntent = new Intent(getApplicationContext(), History.class);
                startActivity(historyIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}