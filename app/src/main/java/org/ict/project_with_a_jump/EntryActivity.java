package org.ict.project_with_a_jump;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EntryActivity extends AppCompatActivity {
    Dialog dialog;
    TextView Day;
    Button yesBtn;

    String user_name;
    String user_num;
    String user_address;
    String facilityName;

    //사업자 전자출입명부 firebase 연결
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = database.getReference("project_with_a_jump").child("ManageList");

    //ManageAccount에서 시설 영업시간 읽어오기
    DatabaseReference databaseReference2 = database.getReference("project_with_a_jump").child("ManageAccount");
    String[] title = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
    Date nowTime = null;
    Date openTime = null;
    Date closingTime = null;
    String open, closed;
    int openCompare = 100;
    int closingCompare = 100;

    //현재 시간
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
    String formatDate = dateFormat.format(date);
    String nowString= dateFormat2.format(date);

    EditText NowPlace, UserNum, Name, LivePlace, Temperature;

    //사용자 Database
    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference3 = database2.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //inflate
        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.dialog, null);

        //findViewById
        UserNum = findViewById(R.id.UserNum);
        Day = findViewById(R.id.Day);
        NowPlace = findViewById(R.id.NowPlace);
        Name = findViewById(R.id.Name);
        LivePlace = findViewById(R.id.LivePlace);
        Temperature = findViewById(R.id.Temperature);

        //intent
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        Name.setText(user_name);
        user_num = intent.getStringExtra("user_num");
        UserNum.setText(user_num);
        user_address = intent.getStringExtra("user_address");
        LivePlace.setText(user_address);
        facilityName = intent.getStringExtra("facilityName");
        NowPlace.setText(facilityName);
        Boolean checkData = intent.getExtras().getBoolean("checkData");

        //팝업창
        dialog = new Dialog(EntryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);

        findViewById(R.id.ButtonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        //시간 출력
        Day = findViewById(R.id.Day);
        Day.setText(formatDate);


    }

    public void showDialog() {
        dialog.show();

        //동의
        yesBtn = dialog.findViewById(R.id.yesBtn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toast 메시지
                //Toast.makeText(getApplicationContext(), "명부가 작성되었습니다",Toast.LENGTH_SHORT).show();

                /* 사업자 전자출입명부 */
                //현재 날짜 가져오기
                Calendar cal= Calendar.getInstance();
                Date currentTime = cal.getTime();
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

                String year = yearFormat.format(currentTime);
                String month = monthFormat.format(currentTime);
                String day = dayFormat.format(currentTime);

                Log.d("DAY", year + month + day);

                //Toast.makeText(getApplicationContext(), "명부가 작성되었습니다", Toast.LENGTH_SHORT).show();

                /* 명부 작성 시간과 시설 영업시간 비교 후 출입명부 작성 */
                //파이어베이스 ManageAccount로부터 시설 영업시간 읽어오기
                databaseReference2.child(facilityName).child("officeHour").child(title[dayOfWeek - 1]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot != null) {
                            Time schedule = snapshot.getValue(Time.class);
                            open = schedule.getOpen();
                            closed = schedule.getClosed();

                            if ((open.equals("")) && (open.equals(""))) {
                                Toast.makeText(getApplicationContext(), "명부를 작성할 수 없습니다.\n시설 휴무일 입니다.", Toast.LENGTH_SHORT).show();
                            }

                            //시설 영업시간(open,closed)과 현재시간 비교
                            int possible= checkTime(open, closed);
                            if(possible==1){
                                FirebasePost firebasePost = new FirebasePost();
                                firebasePost.setDate(Day.getText().toString());
                                firebasePost.setName1(user_name);
                                firebasePost.setphonenumber(user_num);
                                firebasePost.sethome(user_address);
                                firebasePost.settemperature(Temperature.getText().toString());

                                databaseReference
                                        .child(facilityName)
                                        .child(year + "년" + month + "월")
                                        .child(day + "일")
                                        .child(user_name).setValue(firebasePost);

                                Toast.makeText(getApplicationContext(), "명부가 작성되었습니다", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "명부를 작성할 수 없습니다.\n시설 영업시간이 아닙니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });


                //동의 버튼 누르면 database에 데이터 저장
                EntryList entryList = new EntryList(NowPlace.getText().toString(), Day.getText().toString());

                databaseReference3
                        .child("EntryList")
                        .child(UserNum.getText().toString())
                        .push()
                        .setValue(entryList);
            }
        });

        //비동의
        dialog.findViewById(R.id.noBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    //명부 작성 시간이 해당 시설 영업시간 내인지 확인
    public int checkTime(String start, String end){
        try {
            nowTime = dateFormat2.parse(nowString);
            openTime = dateFormat2.parse(start);
            closingTime = dateFormat2.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }

        openCompare = nowTime.compareTo(openTime);
        closingCompare = nowTime.compareTo(closingTime);

        if (openCompare > 0) {
            if (closingCompare < 0) {
                return 1;
            }else{
                return 0;
            }
        } else if (openCompare < 0) {
            return 0;
        } else {
            return 1;
        }
    }
}