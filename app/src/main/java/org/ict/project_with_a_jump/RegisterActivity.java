package org.ict.project_with_a_jump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.Random;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {
    Button nextbutton, registerbutton, authentication, authenticationCheck;
    private EditText email_join, pwd_join;
    private EditText phoneNumberAccess, Access;
    private EditText name_join, birth_join, daum_resultDetail_join, companyName_join;
    private TextView daum_result_join,daum_result2_join;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    //다음 api
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    //인증번호
    static final int SMS_SEND_PERMISSOW = 1;
    String checkNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email_join = (EditText) findViewById(R.id.email_join);
        pwd_join = (EditText) findViewById(R.id.pwd_join);
        name_join = (EditText) findViewById(R.id.name_join);
        birth_join = (EditText) findViewById(R.id.birth_join2);
        daum_result_join=(TextView) findViewById(R.id.daum_result_join);
        daum_result2_join=(TextView) findViewById(R.id.daum_result2_join);
        daum_resultDetail_join=(EditText) findViewById(R.id.daum_resultDetail_join);
        companyName_join=(EditText) findViewById(R.id.companyName_join);
        registerbutton = findViewById(R.id.registerbutton);
        nextbutton = findViewById(R.id.nextbutton);
        authentication = findViewById(R.id.authentication);
        authenticationCheck = findViewById(R.id.authenticationCheck);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ManageAccount");


        //다음 api
        if (daum_result_join != null) {
            daum_result_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(RegisterActivity.this, WebViewActivity.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }


        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작
                String email = email_join.getText().toString();
                String pwd = pwd_join.getText().toString();
                String name = name_join.getText().toString();
                String birth = birth_join.getText().toString();
                String daum1=daum_result_join.getText().toString();
                String daum2=daum_resultDetail_join.getText().toString();
                String companyName=companyName_join.getText().toString();

                //Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(pwd);
                            account.setName(name);
                            account.setBirth(birth);
                            account.setDaum1(daum1);
                            account.setDaum2(daum2);
                            account.setCompanyName(companyName);

                            //setvalue(): database에 insert(삽입)
                            mDatabaseRef.child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }


        });



//        문자 보내기 권한 확인 (코드 구현중!!!)
//        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
//        if (permissonCheck != PackageManager.PERMISSION_GRANTED) {
//            //보내기 권한 거부
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
//                Toast.makeText(getApplicationContext(), "SMS 권한이 필요합니다", Toast.LENGTH_SHORT).show();
//
//            }
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_SEND_PERMISSOW);
//        }
//
//        authentication.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkNum = numberGen(4,1);
//
//                sendSMS(phoneNumberAccess.getText().toString(),"인증번호"+checkNum);
//            }
//        });
//
//        //인증번호 일치 확인 버튼
//        authenticationCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"인증번호를 확인하였습니다",Toast.LENGTH_SHORT).show();
//            }
//        });



        //!!!!파베 이미지 업로드할 때 데이터 전달
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, RegisterActivity3.class);
                intent.putExtra("name", name_join.getText().toString());
                intent.putExtra("companyName", companyName_join.getText().toString());
                startActivity(intent);

            }
        });


    }


    //코드 구현중!!!!!
    /*//sms 기능
    private void sendSMS(String phoneNumber, String message)
    {
        PendingIntent pi = PendingIntent.getActivities(this, 0,
                new Intent[]{new Intent(this, SubActivity.class)}, 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
        Toast.makeText(getBaseContext(), "문자 메세지를 확인해주세요", Toast.LENGTH_SHORT).show();
    }

    public static String numberGen(int len, int dupCd){

        Random rand = new Random();
        String numStr= "";

        for(int i=0; i<len; i++){
            String ran = Integer.toString(rand.nextInt(10));
            if(dupCd==1){
                numStr+= ran;
            }else if(dupCd==2){
                if(!numStr.contains(ran)){
                    numStr+= ran;
                }else{
                    i-=1;
                }
            }
        }
        return numStr;
    }

    *//*public void onClick_cf(View v) {

        Intent intent_02= new Intent(getApplicationContext(), MainActivity2.class);
        String ph= phoneNumberAccess.getText().toString();
        intent_02.putExtra("입력한 번호",ph);
        startActivity(intent_02);
    }*/


    //다음 api
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    Log.d("data",data);
                    int idx = data.indexOf(",");
                    //data2 = 우편번호
                    String data2 = data.substring(0,idx);
                    Log.d("data2",data2);
                    String cutData = data.substring(idx+1).trim();
                    Log.d("cutData",cutData);
                    if (data != null) {
                        daum_result_join.setText(cutData);
                        daum_result2_join.setText(data2);
                    }
                }
                break;
        }
    }


}
