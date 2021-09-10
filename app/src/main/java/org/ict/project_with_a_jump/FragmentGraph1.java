package org.ict.project_with_a_jump;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentGraph1 extends Fragment {
    TextView nowDate, nowYear,nowMonth,number,total;

    private BarChart barChart;
    BarDataSet barDataSet;
    BarData barData;

    ArrayList values= new ArrayList(); //그래프 데이터 값
    ArrayList days= new ArrayList(); //그래프 x축 라벨

    int totalNumber=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_fragment_graph1,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        /* 해당 년도/월 나옴 */
        //nowYear= view.findViewById(R.id.nowYear);
        //nowMonth= view.findViewById(R.id.nowMonth);
        nowDate= view.findViewById(R.id.nowDate);
        Calendar cal=Calendar.getInstance(new Locale("en","US"));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy년\n  M월");
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy년");
        SimpleDateFormat sdf2=new SimpleDateFormat("M월");
        nowDate.setText(sdf.format(cal.getTime()));
        //nowYear.setText(sdf1.format(cal.getTime()));
        //nowMonth.setText(sdf2.format(cal.getTime()));

        /* 그래프 */
        barChart = view.findViewById(R.id.barChart);
        //오늘 날짜에 해당하는 데이터 가져오기
        long today = System.currentTimeMillis();
        Date date = new Date(today);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy년MM월");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd일");
        String month = dateFormat1.format(date); //이번 달
        //String today2 = dateFormat2.format(date);

        values.clear();
        days.clear();
        barChart.invalidate();
        barChart.clear();
        totalNumber=0;

        /* 일별 방문자 수 파악, 그래프에 반영하기 */
        total=view.findViewById(R.id.total); //총 방문자 수
        //String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef1= rootRef.child("ManageList").child("국민떡볶이 덕성여대점");
        uidRef1.child(month).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int index=0;
                if(snapshot.hasChildren()){
                    for(DataSnapshot myDataSnapshot : snapshot.getChildren()){
                        long count= myDataSnapshot.getChildrenCount();
                        values.add(new BarEntry((int)count,index));
                        days.add(myDataSnapshot.getKey());
                        index++;
                        totalNumber+=(int)count;
                        total.setText(totalNumber+"명");
                    }
                    showChart(values,days);
                }else{
                    //number.setText("snapshot is null");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    public void showChart(ArrayList values,ArrayList days) {
        barDataSet = new BarDataSet(values, "일별 방문자 수");

        //BarDataSet barDataSet= new BarDataSet(values,"일별 방문자 수");
        barData = new BarData(days, barDataSet);
        barDataSet.setColor(Color.BLUE);
        barDataSet.setValueTextSize(14f);
        barChart.setData(barData);

        //데이터값 float->int
        ValueFormatter vf = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "" + (int) value;
            }
        };
        barData.setValueFormatter(vf);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(true);
        //xAxis.setLabelsToSkip(0);
        xAxis.setEnabled(true);
        xAxis.setTextSize(12f);

        //y축
        YAxis yAxisLeft= barChart.getAxisLeft();
        //yAxisLeft.setDrawLabels(false);
        //yAxisLeft.setDrawAxisLine(false);
        //yAxisLeft.setDrawGridLines(false);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);

        //barChart.clear();
        barChart.setDrawGridBackground(false);
        barChart.setTouchEnabled(true); //차트 터치x
        barChart.setPinchZoom(false);
        barChart.setDescription(null);
        //barChart.setDescription("(일)");
        //barChart.setDescriptionTextSize(12f);
        barChart.setVisibleXRangeMinimum(2);
        barChart.setVisibleXRangeMaximum(7);
        barChart.moveViewToX(7);
        barChart.invalidate();
    }
}