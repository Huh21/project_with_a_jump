package org.ict.project_with_a_jump;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeScreen extends Fragment {
    //Toolbar toolbar;
    TextView clock1, clock2;
    TextView placeName;
    TextView officeHour;
    TextView state;

    Date nowTime = null;
    Date openTime = null;
    Date closingTime = null;
    String open, closed;
    int openCompare = 100;
    int closingCompare = 100;


    String[] title = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

    private Activity myActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_screen, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = activity;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //startActivity(new Intent(getContext(), Loading.class));
        super.onViewCreated(view, savedInstanceState);

        clock1 = view.findViewById(R.id.nowTime1);
        clock2 = view.findViewById(R.id.nowTime2);

        placeName = view.findViewById(R.id.name);
        officeHour = view.findViewById(R.id.officeHour);
        state = view.findViewById(R.id.state);

        //오늘 날짜, 시간
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM월 dd일 (EE)");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm"); //영업시간과 비교할 형식
        SimpleDateFormat sdf3 = new SimpleDateFormat("a hh시 mm분 ss초"); //보여주는 방식
        String dateString = sdf1.format(cal.getTime()); //오늘 날짜
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        //파이어베이스에서 시설이름 가져오기
        DatabaseReference uidRef1 = rootRef.child("project_with_a_jump").child("ManageAccount").child(uid);
        uidRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null) {
                    Manage user = snapshot.getValue(Manage.class);
                    String companyName = user.getCompanyName();
                    placeName.setText(companyName);
                } else {
                    placeName.setText("snapshot(placeName) is null");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                placeName.setText("시설 이름 오류");
            }
        });

        //파이어베이스에서 오늘 영업시간 가져오기
        DatabaseReference uidRef2 = rootRef.child("project_with_a_jump").child("ManageAccount").child(uid).child("officeHour").child(title[dayOfWeek - 1]);
        uidRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null) {
                    Time schedule = snapshot.getValue(Time.class);
                    open = schedule.getOpen();
                    closed = schedule.getClosed();

                    if ((open.equals("")) && (open.equals(""))) {
                        officeHour.setText("휴무일");
                    } else {
                        officeHour.setText("영업 시작 시간: " + open + "" + "\n영업 종료 시간: " + closed);
                    }
                } else {
                    officeHour.setText("snapshot(officeHour) is null");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        //현재시간(갱신됨)
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                    try {
                        Thread.sleep(1);
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clock2.setText(getNowTime(sdf3)); //변하는 현재 시각 세팅
                                String rightNow = getNowTime(sdf2); //갱신되는 현재 시간
                                showMessage(sdf2, rightNow);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        });
        th.start();

        clock1.setText(dateString);
    }


    //sdf 형식으로 현재시간 가져오기
    public String getNowTime(SimpleDateFormat sdf) {
        long time = System.currentTimeMillis();
        String nowString = sdf.format(new Date(time));
        return nowString;
    }

    // 현재시간,영업시간 비교하기
    public void showMessage(SimpleDateFormat sdf, String nowString) {
        //시간 비교를 위해 String 타입의 시간->Date 타입으로 바꾸기

        try {
            if((open.equals("")) && (open.equals(""))){
                openTime= null;
                closingTime= null;
            }else{
                nowTime = sdf.parse(nowString);
                openTime = sdf.parse(open);
                closingTime = sdf.parse(closed);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //현재시간,영업시간 비교
        if ((openTime != null) && (closingTime != null)) {
            openCompare = nowTime.compareTo(openTime);
            closingCompare = nowTime.compareTo(closingTime);

            if (openCompare > 0) {
                if (closingCompare < 0) {
                    state.setText("전자출입명부 작성 중입니다.");
                } else if (closingCompare > 0) {
                    state.setText("전자출입명부 작성 시간이 아닙니다.");
                } else {
                    state.setText("영업이 종료되었습니다.");
                }
            } else if (openCompare < 0) {
                state.setText("전자출입명부 작성 시간이 아닙니다.");
            } else {
                state.setText("영업이 시작되었습니다.");
            }
        }else{
            state.setText("전자출입명부 작성 시간이 아닙니다!!");
        }
    }
}
