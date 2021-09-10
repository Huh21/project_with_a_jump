package org.ict.project_with_a_jump;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Loading extends AppCompatActivity{
    private TextView percentage;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        percentage= (TextView)findViewById(R.id.percentage);
        progressBar= findViewById(R.id.progressBar);

        new ShowProgress().execute();

        Handler handler= new Handler(){
            @Override
            public void handleMessage(Message msg){
                finish();

                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        };
        handler.sendEmptyMessageDelayed(0,1500);
        //public void onBackPressed(){} //loading 띄우는 과정에 백 버튼 누를수도
    }

    public class ShowProgress extends AsyncTask<Void, Integer, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0; i<=100; i++){
                try{
                    Thread.sleep(10);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                final int percent = i;
                publishProgress(percent);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            percentage.setText(values[0]+"%");
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void avoid){
            super.onPostExecute(avoid);
        }

    }
}

