package org.ict.project_with_a_jump;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    DatabaseReference rootRef;
    DatabaseReference databaseReference;

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

        //?????? ??????, ??????
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("M??? dd??? (EE)");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm"); //??????????????? ????????? ??????
        SimpleDateFormat sdf3 = new SimpleDateFormat("a hh??? mm??? ss???"); //???????????? ??????
        String dateString = sdf1.format(cal.getTime()); //?????? ??????
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);


        //?????? ?????? ????????????
        SharedPreferences pref = getActivity().getSharedPreferences("companyInfo", Context.MODE_PRIVATE);
        String companyName = pref.getString("placeName", "default");
        if(companyName != "default"){
            placeName.setText(companyName);
        }else{
            placeName.setText("");
        }

        //???????????????????????? ?????? ???????????? ????????????
        rootRef = FirebaseDatabase.getInstance().getReference("project_with_a_jump").child("ManageAccount");
        databaseReference= rootRef.child(companyName).child("officeHour").child(title[dayOfWeek - 1]);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null) {
                    Time schedule = snapshot.getValue(Time.class);
                    open = schedule.getOpen();
                    closed = schedule.getClosed();

                    if ((open.equals("")) && (open.equals(""))) {
                        officeHour.setText("?????????");
                    } else {
                        officeHour.setText("?????? ?????? ??????: " + open + "" + "\n?????? ?????? ??????: " + closed);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        //????????????(?????????)
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                    try {
                        Thread.sleep(1);
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clock2.setText(getNowTime(sdf3)); //????????? ?????? ?????? ??????
                                String rightNow = getNowTime(sdf2); //???????????? ?????? ??????
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


    //sdf ???????????? ???????????? ????????????
    public String getNowTime(SimpleDateFormat sdf) {
        long time = System.currentTimeMillis();
        String nowString = sdf.format(new Date(time));
        return nowString;
    }

    //????????????,???????????? ????????????
    public void showMessage(SimpleDateFormat sdf, String nowString) {
        try {
            if ((open.equals("")) && (open.equals(""))) {
                openTime = null;
                closingTime = null;
            } else {
                nowTime = sdf.parse(nowString);
                openTime = sdf.parse(open);
                closingTime = sdf.parse(closed);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if ((openTime != null) && (closingTime != null)) {
            openCompare = nowTime.compareTo(openTime);
            closingCompare = nowTime.compareTo(closingTime);

            if (openCompare > 0) {
                if (closingCompare < 0) {
                    state.setText("?????????????????? ?????? ????????????.");
                } else if (closingCompare > 0) {
                    state.setText("?????????????????? ?????? ????????? ????????????.");
                } else {
                    state.setText("????????? ?????????????????????.");
                }
            } else if (openCompare < 0) {
                state.setText("?????????????????? ?????? ????????? ????????????.");
            } else {
                state.setText("????????? ?????????????????????.");
            }
        } else {
            state.setText("?????????????????? ?????? ????????? ????????????.");
        }
    }
}