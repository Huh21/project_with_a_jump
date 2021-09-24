package org.ict.project_with_a_jump;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends Activity {

    private EditText editPlace, UserNum;
    private TextView editTime;
    public String stPlace, stTime;
    private ListView listView;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private ChildEventListener childEventListener;

    private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<Object>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        UserNum = (EditText) findViewById(R.id.UserNum);
        editPlace = (EditText) findViewById(R.id.NowPlace);
        editTime = (TextView) findViewById(R.id.Day);
        listView = (ListView) findViewById(R.id.listViewEntry);

        initDatabase();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                new ArrayList<String>());
        listView.setAdapter(adapter);

        databaseReference = firebaseDatabase.getReference("EntryList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();;

                for(DataSnapshot entryData : snapshot.getChildren()) {

                    String editPlace2 = entryData.getValue().toString();
                    Array.add(editPlace2);
                    adapter.add(editPlace2);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initDatabase() {

        firebaseDatabase = FirebaseDatabase.getInstance();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(childEventListener);
    }
}
