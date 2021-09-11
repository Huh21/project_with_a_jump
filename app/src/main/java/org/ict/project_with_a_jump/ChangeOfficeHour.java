package org.ict.project_with_a_jump;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeOfficeHour extends Fragment {
    Toolbar toolbar;
    Button save;
    EditText mon1, mon2;
    EditText tue1, tue2;
    EditText wed1, wed2;
    EditText thu1, thu2;
    EditText fri1, fri2;
    EditText sat1, sat2;
    EditText sun1, sun2;

    String[] days = new String[14];
    String[] dayName = {"mon1", "mon2", "tue1", "tue2", "wed1", "wed2", "thu1", "thu2", "fri1", "fri2", "sat1", "sat2", "sun1", "sun2"};
    String[] title = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

    DatabaseReference rootRef;
    DatabaseReference uidRef;

    /*
    // 툴바에 뒤로가기 버튼 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_change_office_hour, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        save = view.findViewById(R.id.save);
        mon1 = view.findViewById(R.id.mon1);
        mon2 = view.findViewById(R.id.mon2);
        tue1 = view.findViewById(R.id.tue1);
        tue2 = view.findViewById(R.id.tue2);
        wed1 = view.findViewById(R.id.wed1);
        wed2 = view.findViewById(R.id.wed2);
        thu1 = view.findViewById(R.id.thu1);
        thu2 = view.findViewById(R.id.thu2);
        fri1 = view.findViewById(R.id.fri1);
        fri2 = view.findViewById(R.id.fri2);
        sat1 = view.findViewById(R.id.sat1);
        sat2 = view.findViewById(R.id.sat2);
        sun1 = view.findViewById(R.id.sun1);
        sun2 = view.findViewById(R.id.sun2);

        //있는 값 불러오기
        takeInfo(mon1, dayName[0]);
        takeInfo(mon2, dayName[1]);
        takeInfo(tue1, dayName[2]);
        takeInfo(tue2, dayName[3]);
        takeInfo(wed1, dayName[4]);
        takeInfo(wed2, dayName[5]);
        takeInfo(thu1, dayName[6]);
        takeInfo(thu2, dayName[7]);
        takeInfo(fri1, dayName[8]);
        takeInfo(fri2, dayName[9]);
        takeInfo(sat1, dayName[10]);
        takeInfo(sat2, dayName[11]);
        takeInfo(sun1, dayName[12]);
        takeInfo(sun2, dayName[13]);

        /*
        // 툴바
        toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */

        //저장 버튼을 누르면 SharedPreferences에 값 저장
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edittext값 가져오기
                days[0] = mon1.getText().toString(); //days[0]=19:30
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

                for (int i = 0; i < days.length; i += 2) {
                    if ((days[i] != null) && (days[i + 1] != null)) {
                        saveInfo(days[i], days[i + 1], dayName[i], dayName[i + 1], title[i / 2]);
                    }
                }
                Toast.makeText(getContext(), "영업시간이 변경되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //값 불러오기
    public void takeInfo(EditText editText, String key) {
        SharedPreferences pref = getActivity().getSharedPreferences("officeTime", Context.MODE_PRIVATE);
        String result = pref.getString(key, "디폴트");
        if (result != "디폴트") {
            editText.setText(result);
        } else { //해당 키 값이 없을 경우(=저장된 데이터가 없을 경우)
            editText.setText("");
        }
    }

    //영업시간 수정(한 요일의 오픈/종료 시간 한번에 저장)
    public void saveInfo(String input1, String input2, String key1, String key2, String day) {
        SharedPreferences pref = getActivity().getSharedPreferences("officeTime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key1, input1);
        editor.putString(key2, input2);
        editor.commit();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();
        uidRef = rootRef.child("project_with_a_jump").child("UserAccount:").child(uid).child("officeHour").child(day);

        Time schedule = new Time();
        schedule.setOpen(input1);
        schedule.setClosed(input2);
        uidRef.setValue(schedule);
        /*
        if(input.isEmpty()){
            Toast.makeText(this,"휴무",Toast.LENGTH_SHORT).show();
            editor.putString("input","*");
        }else{ //edittext에 입력된 값이 있다면 저장
            Toast.makeText(this,input+" 저장",Toast.LENGTH_SHORT).show();
        }
        */
    }

}
