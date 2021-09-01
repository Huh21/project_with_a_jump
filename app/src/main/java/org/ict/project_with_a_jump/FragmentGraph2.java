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
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SortedMap;

public class FragmentGraph2 extends Fragment implements View.OnClickListener{
    Button button;
    private PeriodDialog periodDialog;
    TextView term;

    int[] pickedValues= new int[4];

    private LineChart lineChart;

    ArrayList values= new ArrayList(); //그래프 데이터 값
    ArrayList days= new ArrayList(); //그래프 x축 라벨

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_fragment_graph2,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button= view.findViewById(R.id.chooseTerm);
        button.setOnClickListener(this);

        //기간 기본 설정
        term=view.findViewById(R.id.term);
        Calendar cal= Calendar.getInstance();
        int nowYear= cal.get(Calendar.YEAR);
        int nowMonth= cal.get(Calendar.MONTH);
        int startYear= cal.get(Calendar.YEAR)-1;
        int startMonth= cal.get(Calendar.MONTH)+1;

        String str1= startYear+"년 "+startMonth+"월~";
        String str2= nowYear+"년 "+nowMonth+"월";
        term.setText(str1+str2);

        /* 월별 그래프 */
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM");
        int y,m;
        y=pickedValues[0];
        m=pickedValues[1];
        while((y<pickedValues[2]) || ((y==pickedValues[2])&&(m<=pickedValues[3]))){
            Calendar cal1= Calendar.getInstance();
            cal1.set(Calendar.YEAR, y);
            cal1.set(Calendar.MONTH, m);
            String xLabel= sdf.format(cal1.getTime());
            days.add(xLabel);
            m++;

            if(m>=13){
                m=1;
                y+=1;
            }
        }




        //x축 라벨 년도/월로 바꾸기
        ValueFormatter selectedTerm= new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ""+(int)value;
            }
        };
        //xAxis.setValueFormatter(selectedTerm);


    }

    //버튼 누르면 다이얼로그 뜸
    @Override
    public void onClick(View view) {
        periodDialog=new PeriodDialog(getContext(), new PeriodDialog.PeriodDialogListener() {
            @Override
            public void close(int startYear, int startMonth, int endYear, int endMonth) {
                pickedValues[0]=startYear;
                pickedValues[1]=startMonth;
                pickedValues[2]=endYear;
                pickedValues[3]=endMonth;
            }
        });
        //periodDialog.setCancelable(true);
        periodDialog.setUpTerm(term);
    }
}