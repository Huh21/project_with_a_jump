package org.ict.project_with_a_jump;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.annotations.NotNull;

import java.util.Random;

public class MainActivity_C extends AppCompatActivity {
// 회원가입
    Button registerbut;
    private EditText user_name, user_ad,user_ce;
    private EditText user_email, user_pwd;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private String Validation = "^.(?=.*[0-9])(?=.*[0-9ㄱ-ㅎ가-힣]).*$";
    //sms
    EditText user_pnumber;
    Button sendSMSBt;
    EditText inputCheckNum;
    Button checkBt;

    static final int SMS_SEND_PERMISSOW = 1;
    //안증번호 비교를 위한 쉐어드 저장

    String checkNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_c);

        sendSMSBt = findViewById(R.id.send_sms_button);
        user_pnumber = findViewById(R.id.user_pnumber);
        inputCheckNum = findViewById(R.id.input_check_num);
        checkBt = findViewById(R.id.check_button4);

        user_name = (EditText) findViewById(R.id.user_name);
        user_ad = (EditText) findViewById(R.id.user_ad);
        user_ce = (EditText) findViewById(R.id.user_ce);
        registerbut = findViewById(R.id.registerbut);
        /***
         * 문자 보내기 권한 확인
         */
        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissonCheck != PackageManager.PERMISSION_GRANTED) {
            //보내기 권한 거부
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                Toast.makeText(getApplicationContext(), "SMS 권한이 필요합니다", Toast.LENGTH_SHORT).show();

            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_SEND_PERMISSOW);
        }

        sendSMSBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNum = numberGen(4, 1);

                sendSMS(user_pnumber.getText().toString(), "인증번호" + checkNum);
            }
        });
        registerbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작
                //String email = user_email.getText().toString();
                //String pwd = user_pwd.getText().toString();
                String name = user_name.getText().toString();
                String num = user_ce.getText().toString();
                String address = user_ad.getText().toString();

                //Firebase Auth 진행
                // mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener
                mFirebaseAuth.createUserWithEmailAndPassword(name,num ).addOnCompleteListener(MainActivity_C.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            User User = new User();
                            User.setIdToken(firebaseUser.getUid());
                            //User.setEmailId(firebaseUser.getEmail());
                            // User.setPassword(pwd);
                            User.setName(name);
                            User.setAddress(address);
                            User.setNum(num);


                            //setvalue(): database에 insert(삽입)
                            mDatabaseRef.child(firebaseUser.getUid()).setValue(User);

                            Toast.makeText(MainActivity_C.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity_C.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                });


            }

        });

        //인증번호 일치 확인 버튼
        checkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "인증번호를 확인하였습니다", Toast.LENGTH_SHORT).show();
            }
        });




    }


    /***
     *sms 기능
     * @param phoneNumber
     * @param message
     */

    private void sendSMS(String phoneNumber, String message) {
        PendingIntent pi = PendingIntent.getActivities(this, 0,
                new Intent[]{new Intent(this, subActivity.class)}, 0);
        //여기 intent가 있어서 이전 입력한 정보가 살아있지 못하더라구요. shardprefence 사용해봤는데 앱이 강제 종료됬었습니다.

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
        Toast.makeText(getBaseContext(), "문자 메세지를 확인해주세요", Toast.LENGTH_SHORT).show();
    }

    /**
     * @param len:생성한 난수의 길이
     * @poram dupCd:중복 허용 여부
     */
    public static String numberGen(int len, int dupCd) {

        Random rand = new Random();
        String numStr = "";

        for (int i = 0; i < len; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            if (dupCd == 1) {
                numStr += ran;
            } else if (dupCd == 2) {
                if (!numStr.contains(ran)) {
                    numStr += ran;
                } else {
                    i -= 1;
                }
            }
        }
        return numStr;
    }
}