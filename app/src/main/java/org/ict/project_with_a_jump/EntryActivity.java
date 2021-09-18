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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EntryActivity extends AppCompatActivity {
    Dialog dialog;
    TextView Day;

    EditText Name, PhoneNumber, LivePlace, NowPlace, Temperature;
    //사업자 전자출입명부 firebase 연결
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=database.getReference("project_with_a_jump").child("ManageList");

    //현재 시간
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
    String formatDate = dateFormat.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //inflate
        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.dialog, null);

        Day = (TextView) findViewById(R.id.Day);
        Name = (EditText) findViewById(R.id.Name);
        PhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        LivePlace = (EditText) findViewById(R.id.LivePlace);
        Temperature = (EditText) findViewById(R.id.Temperature);
        NowPlace = (EditText) findViewById(R.id.NowPlace);

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
        Day = (TextView) findViewById(R.id.Day);
        Day.setText(formatDate);
    }

    public void showDialog() {
        dialog.show();

        //동의
        Button yesBtn = dialog.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "명부가 작성되었습니다",
                        Toast.LENGTH_SHORT).show();

                /* 사업자 전자출입명부 */
                //현재 날짜 가져오기
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

                String year = yearFormat.format(currentTime);
                String month = monthFormat.format(currentTime);
                String day = dayFormat.format(currentTime);

                Log.d("DAY", year + month + day);

                //Toast.makeText(getApplicationContext(), "명부가 작성되었습니다", Toast.LENGTH_SHORT).show();

                FirebasePost firebasePost = new FirebasePost();
                firebasePost.setDate(Day.getText().toString());
                firebasePost.setName1(Name.getText().toString());
                firebasePost.setphonenumber(PhoneNumber.getText().toString());
                firebasePost.sethome(LivePlace.getText().toString());
                firebasePost.settemperature(Temperature.getText().toString());

                databaseReference
                        .child(NowPlace.getText().toString())
                        .child(year + "년" + month + "월")
                        .child(day + "일")
                        .child(Name.getText().toString()).setValue(firebasePost);


                Intent intent = new Intent(EntryActivity.this, EntryActivity2.class);
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