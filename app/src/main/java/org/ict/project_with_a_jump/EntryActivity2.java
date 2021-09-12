package org.ict.project_with_a_jump;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryActivity2 extends Activity {

    CalendarView calendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry2);

        calendarView = findViewById(R.id.calendarView);

        //날짜 변환
        DateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
        Date date = new Date(calendarView.getDate());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String day;
                day = year + "년" + (month + 1) + "월" + dayOfMonth + "일";

            }
        });
    }
}