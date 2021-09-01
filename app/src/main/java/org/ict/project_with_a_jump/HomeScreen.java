package org.ict.project_with_a_jump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeScreen extends AppCompatActivity {
    //영업시간 설정
    //private static final String OPEN = "8:00:00";
    //private static final String CLOSE = "22:00:00";

    Date nowTime=null;
    Date openTime=null;
    Date closingTime=null;

    /*
    Date nowTime = null;
    Date openTime = null;
    Date closingTime = null;
    */

    int openCompare=100;
    int closingCompare=100;

    //private static Handler handler;

    //TextView
    Toolbar toolbar;
    TextView clock1, clock2;
    TextView placeName;
    TextView officeHour;
    TextView state;

    String[] title={"sun","mon","tue","wed","thu","fri","sat"};

    String companyName;

    String open, closed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startActivity(new Intent(this,Loading.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        /* findViewById */
        clock1=(TextView)findViewById(R.id.nowTime1);
        clock2=(TextView)findViewById(R.id.nowTime2);

        placeName= (TextView)findViewById(R.id.name);
        officeHour= (TextView)findViewById(R.id.officeHour);
        state= (TextView)findViewById(R.id.state);

        /* 오늘 날짜, 시간 */
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf1= new SimpleDateFormat("MM월 dd일 (EE)");
        SimpleDateFormat sdf3= new SimpleDateFormat("a hh시 mm분 ss초"); //보여주는 방식
        SimpleDateFormat sdf2= new SimpleDateFormat("HH:mm"); //영업시간과 비교할 형식
        String dateString = sdf1.format(cal.getTime()); //오늘 날짜
        int dayOfWeek= cal.get(Calendar.DAY_OF_WEEK);

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();

        DatabaseReference uidRef1= rootRef.child("project_with_a_jump").child("UserAccount:").child(uid);
        uidRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot != null){
                    UserAccount user= snapshot.getValue(UserAccount.class);
                    companyName= user.getCompanyName();
                    placeName.setText(companyName);
                }else{
                    placeName.setText("snapshot(placeName) is null");
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                placeName.setText("시설 이름 오류");
            }
        });

        /*
        SharedPreferences pref= getSharedPreferences("test",MODE_PRIVATE);
        String open_ = pref.getString("tue1","디폴트");
        if(open_ != "디폴트") {
            open=open_;
        }
        String closed_ =pref.getString("tue2", "디폴트");
        if(closed !="디폴트") {
            closed=closed_;
        }

         */

        DatabaseReference uidRef2= rootRef.child("project_with_a_jump").child("UserAccount:").child(uid).child("officeHour").child(title[dayOfWeek-1]);
        uidRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot != null){
                    Time schedule= snapshot.getValue(Time.class);
                    open=schedule.getOpen();
                    closed= schedule.getClosed();

                    //테스트 후 지워야 할 부분
                    if((open!=null) && (closed!=null)){
                        officeHour.setText("시작: "+open+" / 종료: "+closed);
                    }else{
                        officeHour.setText("오늘 영업시간이 설정되지 않았음");
                    }
                }else{
                    officeHour.setText("snapshot(officeHour) is null");
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        
        /* Firebase에서 정보 가져오기 */
        //시설 이름
        //Intent receive_intent= getIntent();
        //String companyName= receive_intent.getStringExtra("place");
        //placeName.setText(companyName);

        //영업시간
        //open= receive_intent.getStringExtra("openTime");
        //closed= receive_intent.getStringExtra("closedTime");

        //갱신되는 현재 시간
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                    try {
                        Thread.sleep(1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clock2.setText(getNowTime(sdf3)); //변하는 현재 시각 세팅
                                String rightNow= getNowTime(sdf2); //갱신되는 현재 시간
                                showMessage(sdf2, rightNow);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        });
        th.start();

        /* 툴바 */
        toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getActionBar().setDisplayShowTitleEnabled(false);


        clock1.setText(dateString);


    }

    //sdf 형식으로 현재시간 가져오기
    public String getNowTime(SimpleDateFormat sdf) {
        long time= System.currentTimeMillis();
        String nowString = sdf.format(new Date(time));
        return nowString;
    }

    //현재시간,영업시간 비교하기
    public void showMessage(SimpleDateFormat sdf, String nowString){

        //시간 비교를 위해 String 타입의 시간->Date 타입으로 바꾸기
        try{
            nowTime= sdf.parse(nowString);
            openTime= sdf.parse(open);
            closingTime= sdf.parse(closed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //시간 비교
        if((openTime != null) && (closingTime != null)){ //openTime,closingTime 둘 다 null 아닐 때
            openCompare = nowTime.compareTo(openTime);
            closingCompare = nowTime.compareTo(closingTime);

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
        }else if((openTime == null) && (closingTime == null)){ //openTime,closingTime 둘 중 하나라도 null일 때(비교할 영업시간이 없는 경우)
            state.setText("휴무일 입니다.");
        }else{
            state.setText("영업 시작 시간과 종료 시간을 모두 입력해주세요.");
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


    //오늘의 영업시간 불러오기
    public void takeInfo(EditText editText,String key){
        SharedPreferences pref= getSharedPreferences("test",MODE_PRIVATE);
        String result = pref.getString(key,"디폴트");
        if(result!="디폴트"){
            editText.setText(result);
        }else{ //해당 키 값이 없을 경우(=저장된 데이터가 없을 경우)

        }
    }

}