package org.ict.project_with_a_jump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class RegisterActivity3 extends AppCompatActivity {
    Button nextbutton3;
    ImageView imageView;
    Button picturebutton;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        imageView=findViewById(R.id.imageView);

        nextbutton3=findViewById(R.id.nextbutton3);
        nextbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity3.this, RegisterActivity4.class);
                startActivity(intent);
            }
        });

        Button picturebutton=findViewById(R.id.picturebutton);
        picturebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data ){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==101 && resultCode==RESULT_OK) {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=8;
            Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath(),options);

            imageView.setImageBitmap(bitmap);
        }
    }

    public void takePicture() {
        try {
            file=createFile();
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri uri;
        if(Build.VERSION.SDK_INT>=24) {
            uri= FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file);
        } else {
            uri= Uri.fromFile(file);
        }

        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,101);
    }

    private File createFile() {
        String filename="capture.jpg";
        File outFile=new File(getFilesDir(),filename);
        Log.d("Main","File path:"+outFile.getAbsolutePath());

        return outFile;
    }

}