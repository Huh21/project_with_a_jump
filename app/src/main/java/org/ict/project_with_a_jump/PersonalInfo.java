package org.ict.project_with_a_jump;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonalInfo extends Fragment {
    DatabaseReference rootRef;
    DatabaseReference uidRef;

    TextView showName;
    TextView showBirth;
    TextView showEmailId;
    TextView showPassword;

    Button change;

    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_personal_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        showName= view.findViewById(R.id.name);
        showBirth= view.findViewById(R.id.birth);
        showEmailId= view.findViewById(R.id.email);
        showPassword= view.findViewById(R.id.password);
        change=view.findViewById(R.id.changePassword);
        mAuth= FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        rootRef= FirebaseDatabase.getInstance().getReference();
        uidRef= rootRef.child("project_with_a_jump").child("UserAccount:").child(uid);
        uidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    //String companyName= snapshot.getValue(String.class);
                    UserAccount userAccount= snapshot.getValue(UserAccount.class);
                    String name= userAccount.getName();
                    String birth= userAccount.getBirth();
                    String emailId= userAccount.getEmailId();
                    String password= userAccount.getPassword();
                    showName.setText(name+" 님");
                    showBirth.setText("생년월일       "+birth);
                    showEmailId.setText("아이디(이메일) "+emailId);
                    showPassword.setText("비밀번호       "+password);
                }else{
                    showName.setText("오류");
                    showEmailId.setText("오류");
                    showPassword.setText("오류");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // 툴바
        toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //비밀번호 재설정
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText= new EditText(getContext());
                //AlertDialog.Builder dialog= new AlertDialog.Builder(PersonalInfo.this);
                AlertDialog.Builder dialog= new AlertDialog.Builder(getContext());
                dialog.setTitle("비밀번호 재설정");
                dialog.setMessage("이메일을 입력해주세요.");
                dialog.setView(editText);
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.length() > 0){
                            String email= editText.getText().toString();
                            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getContext(), "이메일을 보냈습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        */

    }

    /*
    // 툴바에 뒤로가기 버튼 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

}