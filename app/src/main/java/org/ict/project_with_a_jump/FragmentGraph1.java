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
import java.util.Locale;

public class FragmentGraph1 extends Fragment {
    TextView nowDate;
    TextView number;
    TextView total;

    private BarChart barChart;

    int index= 3;
    int countDate=1;

    String[] title={"sun","mon","tue","wed","thu","fri","sat"};

    BarDataSet barDataSet;
    BarData barData;

    ArrayList values= new ArrayList(); //그래프 데이터 값
    ArrayList days= new ArrayList(); //그래프 x축 라벨

    int date=1;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_fragment_graph1,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        /* 해당 년도/월 나옴 */
        nowDate= view.findViewById(R.id.nowDate);
        Calendar cal=Calendar.getInstance(new Locale("en","US"));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy년\n  M월");
        nowDate.setText(sdf.format(cal.getTime()));

        /* 그래프 */
        barChart = view.findViewById(R.id.barChart);


        //데이터 값
        ArrayList values= new ArrayList();
        values.add(new BarEntry(30,0));
        values.add(new BarEntry(40, 1));
        values.add(new BarEntry(50, 2));

        //x축 라벨
        ArrayList days= new ArrayList();
        days.add(countDate+"일");
        days.add((countDate+1)+"일");
        days.add((countDate+2)+"일");


        /* getChildrenCount() */
        //number= view.findViewById(R.id.number);
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();

        DatabaseReference uidRef1= rootRef.child("project_with_a_jump").child("UserAccount:").child(uid);
        uidRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot != null){
                    long num= snapshot.getChildrenCount();
                    number.setText(Long.toString(num));
                    values.add(new BarEntry(Long.valueOf(num).intValue()+1,0)); //데이터 추가
                    values.add(new BarEntry(20,1));
                    values.add(new BarEntry(35,2));
                    values.add(new BarEntry(20,3));
                    values.add(new BarEntry(35,4));
                    values.add(new BarEntry(20,5));
                    values.add(new BarEntry(35,6));
                    values.add(new BarEntry(20,7));
                    values.add(new BarEntry(35,8));
                    values.add(new BarEntry(20,9));
                    values.add(new BarEntry(35,10));


                    days.add(date+"일");
                    days.add((date+1)+"일");
                    days.add((date+2)+"일");
                    days.add((date+3)+"일");
                    days.add((date+4)+"일");
                    days.add((date+5)+"일");
                    days.add((date+6)+"일");
                    days.add((date+7)+"일");
                    days.add((date+8)+"일");
                    days.add((date+9)+"일");
                    days.add((date+10)+"일");
                    //index++;
                    showChart(values,days);
                }else{
                    number.setText("snapshot is null.");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void showChart(ArrayList values,ArrayList days){
        barDataSet= new BarDataSet(values,"일별 방문자 수");

        //BarDataSet barDataSet= new BarDataSet(values,"일별 방문자 수");
        barData= new BarData(days,barDataSet);
        barDataSet.setColor(Color.BLUE);
        barDataSet.setValueTextSize(14f);
        barChart.setData(barData);

        //데이터값 float->int
        ValueFormatter vf= new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ""+(int)value;
            }
        };
        barData.setValueFormatter(vf);

        XAxis xAxis= barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(true);
        //xAxis.setLabelsToSkip(0);
        xAxis.setEnabled(true);
        xAxis.setTextSize(12f);

        //y축
        //YAxis yAxisLeft= barChart.getAxisLeft();
        //yAxisLeft.setDrawLabels(false);
        //yAxisLeft.setDrawAxisLine(false);
        //yAxisLeft.setDrawGridLines(false);

        YAxis yAxisRight= barChart.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);

        //barChart.clear();
        barChart.setDrawGridBackground(false);
        barChart.setTouchEnabled(false); //차트 터치x
        barChart.setPinchZoom(false);
        barChart.setDescription(null);
        barChart.setDescription("(일)");
        barChart.setDescriptionTextSize(12f);
        barChart.setVisibleXRangeMinimum(2);
        barChart.setVisibleXRangeMaximum(7);
        barChart.moveViewToX(7);
        barChart.invalidate();
    }



}