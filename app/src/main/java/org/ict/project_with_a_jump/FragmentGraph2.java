package org.ict.project_with_a_jump;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentGraph2 extends Fragment {
    private Context context;
    Button chooseTerm;
    PeriodDialog periodDialog;
    TextView term;

    private LineChart lineChart;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_fragment_graph2,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        chooseTerm= view.findViewById(R.id.chooseTerm);

        //chooseTerm.setOnClickListener(this);
        if(chooseTerm!=null){
            chooseTerm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    periodDialog=new PeriodDialog(getContext());
                    //periodDialog.setCancelable(true);
                    periodDialog.setUpTerm(term);
                }
            });
        }

        //그래프
        lineChart = (LineChart) view.findViewById(R.id.lineChart);

        ArrayList entries= new ArrayList();
        entries.add(new Entry(45,0));
        entries.add(new Entry(78,1));
        entries.add(new Entry(36,2));
        entries.add(new Entry(90,3));
        entries.add(new Entry(69,4));
        entries.add(new Entry(55,5));
        entries.add(new Entry(45,6));
        entries.add(new Entry(78,7));
        entries.add(new Entry(27,8));
        entries.add(new Entry(73,9));
        entries.add(new Entry(49,10));
        entries.add(new Entry(85,11));

        LineDataSet lineDataSet= new LineDataSet(entries,"방문자 수");
        lineDataSet.setCircleColor(Color.BLUE); //그래프 포인트(?값) 색
        lineDataSet.setColor(Color.BLUE); //그래프 색
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(16f);

        //dialog 기간 기본 설정
        Calendar cal= Calendar.getInstance();
        int nowYear= cal.get(Calendar.YEAR);
        int nowMonth= cal.get(Calendar.MONTH);
        int startYear= cal.get(Calendar.YEAR)-1;
        int startMonth= cal.get(Calendar.MONTH)+1;

        term=view.findViewById(R.id.term);
        String str1= startYear+"년 "+startMonth+"월~";
        String str2= nowYear+"년 "+nowMonth+"월";
        if(term!=null){
            term.setText("선택된 기간: "+str1+str2);
        }

        //그래프 x축 라벨
        ArrayList labels= new ArrayList();
        //작년
        startYear= startYear % 100;
        for(int i=startMonth; i<=12; i++){
            if(i<10){
                labels.add(startYear+"/0"+i);
            }else{
                labels.add(startYear+"/"+i);
            }
        }
        //올해
        nowYear= nowYear % 100;
        for(int j=1; j<= nowMonth; j++){
            //labels.add(nowYear+"/"+j);
            if(j<10){
                labels.add(nowYear+"/0"+j);
            }else{
                labels.add(nowYear+"/"+j);
            }
        }

        LineData lineData= new LineData(labels,lineDataSet);
        if(lineChart!=null){
            lineChart.setData(lineData);
        }

        //lineChart.animateXY(5000,5000);

        //데이터값 float->int
        ValueFormatter vf= new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ""+(int)value;
            }
        };
        lineData.setValueFormatter(vf);

        XAxis xAxis= lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(true);
        xAxis.setLabelsToSkip(0);

        //오른쪽 y축 비활성화
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);
        //yAxisRight.setAxisMaxValue(20f);
        //yAxisRight.setAxisMinValue(20f);

        lineChart.setDrawGridBackground(false);
        lineChart.setTouchEnabled(false); //차트 터치x
        lineChart.setPinchZoom(false);
        lineChart.setDescription(null);
        //lineChart.setExtraLeftOffset(15f);
        //lineChart.setExtraRightOffset(15f);
        lineChart.invalidate();

    }

    /*
    //버튼 누르면 다이얼로그 뜸
    @Override
    public void onClick(View view) {
        periodDialog=new PeriodDialog(getContext());
        //periodDialog.setCancelable(true);
        periodDialog.setUpTerm(term);
    }
    */
}