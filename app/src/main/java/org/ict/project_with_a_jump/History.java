package org.ict.project_with_a_jump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

public class History extends AppCompatActivity {
    Toolbar toolbar;
    FragmentGraph1 fragment1;
    FragmentGraph2 fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // 툴바
        toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 기간별 기록 탭:
        // 한달(month; 해당 달의 일별 기록을 확인할 수 있는 (해당 달의) 캘린더가 보여짐)
        // 월별(year; 여러 달의 방문자수 변화를 한눈에 파악할 수 있는 그래프가 표시됨)
        fragment1=new FragmentGraph1();
        fragment2=new FragmentGraph2();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        TabLayout tabs= findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("이번 달 기록"));
        tabs.addTab(tabs.newTab().setText("월별 기록"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position= tab.getPosition();
                if(position==0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                }else if(position==1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // 툴바에 뒤로가기 버튼 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}