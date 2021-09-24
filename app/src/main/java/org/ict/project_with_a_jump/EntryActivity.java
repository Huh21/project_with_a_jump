package org.ict.project_with_a_jump;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryActivity extends AppCompatActivity {
    Dialog dialog;
    TextView Day;
    EditText NowPlace, UserNum, Name, LivePlace;

    //현재 시간
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
    String formatDate = dateFormat.format(date);

    //Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewById
        UserNum = findViewById(R.id.UserNum);
        Day = findViewById(R.id.Day);
        NowPlace = findViewById(R.id.NowPlace);
        Name = findViewById(R.id.Name);
        LivePlace = findViewById(R.id.LivePlace);

        //intent
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user_name");
        Name.setText(user_name);
        String user_num = intent.getStringExtra("user_num");
        UserNum.setText(user_num);
        String user_address = intent.getStringExtra("user_address");
        LivePlace.setText(user_address);
        String facilityName = intent.getStringExtra("facilityName");
        NowPlace.setText(facilityName);
        //inflate
        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.dialog, null);

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
        Button yesBtn = dialog.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toast 메시지
                Toast.makeText(getApplicationContext(), "명부가 작성되었습니다",
                        Toast.LENGTH_SHORT).show();

                //동의 버튼 누르면 database에 데이터 저장
                EntryList entryList = new EntryList(NowPlace.getText().toString(), Day.getText().toString());

                databaseReference
                        .child("EntryList")
                        .child(UserNum.getText().toString())
                        .push()
                        .setValue(entryList);

                //다음 액티비티와 연결
                Intent intent = new Intent(EntryActivity.this, MainActivity2.class);
                startActivity(intent);
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
}