package org.ict.project_with_a_jump;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity5 extends AppCompatActivity {
    Button endButton;

    String[] days = new String[14];
    String[] dayName = {"mon1", "mon2", "tue1", "tue2", "wed1", "wed2", "thu1", "thu2", "fri1", "fri2", "sat1", "sat2", "sun1", "sun2"};
    String[] title = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

    EditText mon1, mon2;
    EditText tue1, tue2;
    EditText wed1, wed2;
    EditText thu1, thu2;
    EditText fri1, fri2;
    EditText sat1, sat2;
    EditText sun1, sun2;

    DatabaseReference rootRef;
    DatabaseReference uidRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register5);

        endButton = findViewById(R.id.endButton);
        mon1 = findViewById(R.id.mon1);
        mon2 = findViewById(R.id.mon2);
        tue1 = findViewById(R.id.tue1);
        tue2 = findViewById(R.id.tue2);
        wed1 = findViewById(R.id.wed1);
        wed2 = findViewById(R.id.wed2);
        thu1 = findViewById(R.id.thu1);
        thu2 = findViewById(R.id.thu2);
        fri1 = findViewById(R.id.fri1);
        fri2 = findViewById(R.id.fri2);
        sat1 = findViewById(R.id.sat1);
        sat2 = findViewById(R.id.sat2);
        sun1 = findViewById(R.id.sun1);
        sun2 = findViewById(R.id.sun2);

        //영업시간 저장
        endButton = findViewById(R.id.endButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edittext값 가져오기
                days[0] = mon1.getText().toString();
                days[1] = mon2.getText().toString();

                days[2] = tue1.getText().toString();
                days[3] = tue2.getText().toString();

                days[4] = wed1.getText().toString();
                days[5] = wed2.getText().toString();

                days[6] = thu1.getText().toString();
                days[7] = thu2.getText().toString();

                days[8] = fri1.getText().toString();
                days[9] = fri2.getText().toString();

                days[10] = sat1.getText().toString();
                days[11] = sat2.getText().toString();

                days[12] = sun1.getText().toString();
                days[13] = sun2.getText().toString();

                //영업 시간이 제대로 입력되었는지 확인 후 저장
                for (int i = 0; i < days.length; i += 2) {
                    saveInfo(days[i], days[i + 1], dayName[i], dayName[i + 1], title[i / 2]);
                }
                Toast.makeText(getApplicationContext(), "회원가입을 완료했습니다.", Toast.LENGTH_LONG).show();

                //로그인 화면으로 돌아가기
                Intent intent = new Intent(RegisterActivity5.this, MainActivity2.class);
                startActivity(intent);
            }
        });

    }

    //값 저장(한 요일의 오픈/종료 시간을 같이 저장)
    public void saveInfo(String input1, String input2, String key1, String key2, String day) {
        //SharedPreferences에 저장
        SharedPreferences pref = getSharedPreferences("officeTime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key1, input1);
        editor.putString(key2, input2);
        editor.commit();

        //파이어베이스에 저장
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();
        uidRef = rootRef.child("project_with_a_jump").child("ManageAccount").child(uid).child("officeHour").child(day);

        Time schedule = new Time();
        schedule.setOpen(input1);
        schedule.setClosed(input2);
        uidRef.setValue(schedule);
    }

}