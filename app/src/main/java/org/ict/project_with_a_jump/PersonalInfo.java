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
    EditText showName, showEmail, showBirth, showPlaceName, showAddress1, showAddress2;

    DatabaseReference rootRef;
    DatabaseReference uidRef;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_personal_info,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        showName=view.findViewById(R.id.name);
        showEmail=view.findViewById(R.id.email);
        showBirth=view.findViewById(R.id.birth);
        showPlaceName=view.findViewById(R.id.placeName);
        showAddress1=view.findViewById(R.id.address1);
        showAddress2=view.findViewById(R.id.address2);

        mAuth= FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        rootRef= FirebaseDatabase.getInstance().getReference();
        uidRef= rootRef.child("project_with_a_jump").child("ManageAccount").child(uid);
        uidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    Manage userAccount= snapshot.getValue(Manage.class);
                    String name= userAccount.getName();
                    String birth= userAccount.getBirth();
                    String emailId= userAccount.getEmailId();
                    String placeName= userAccount.getCompanyName();
                    String daum1= userAccount.getDaum1();
                    String daum2= userAccount.getDaum2();

                    //개인정보 출력
                    showName.setText(name+" 님");
                    showBirth.setText(birth);
                    showEmail.setText(emailId);
                    showPlaceName.setText(placeName);
                    showAddress1.setText(daum1);
                    showAddress2.setText(daum2);

                    /*
                    SimpleDateFormat sdf= new SimpleDateFormat("yyyy년 MM월 dd일");
                    try {
                        Date birthDate= sdf.parse(birth);
                        showBirth.setText(birthDate+"");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    */
                }else{
                    showName.setText("오류");
                    showBirth.setText("오류");
                    showEmail.setText("오류");
                    showPlaceName.setText("오류");
                    showAddress1.setText("오류");
                    showAddress2.setText("오류");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}