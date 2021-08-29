package org.ict.project_with_a_jump;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InitActivity extends AppCompatActivity {

    private Button userBtn;
    private Button manageBtn;
    private SharedPreferences choiceData;
    private boolean saveData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        userBtn = findViewById(R.id.userBtn);
        manageBtn = findViewById(R.id.manageBtn);

        // 앱 첫 실행이 아닌 경우, 바로 사용자 유형별(사용자, 사업자) 화면 연결
        choiceData = getSharedPreferences("choiceData", MODE_PRIVATE);
        load();

        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                save();
                Intent intent = new Intent(getApplicationContext(), CertifyActivity.class);
                startActivity(intent);
            }
        });

        manageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
                //startActivity(intent);
            }
        });
    }

    private void load() { // 설정값 호출
        saveData = choiceData.getBoolean("SAVE_CHOICE_DATA", false);

        if (saveData) {
            userBtn.callOnClick();
        } else {
            manageBtn.callOnClick();
        }
    }

    private void save() { // 설정값 저장
        SharedPreferences.Editor editor = choiceData.edit();

        editor.putBoolean("SAVE_CHOICE_DATA", userBtn.isSelected());
        editor.apply();
    }
}
