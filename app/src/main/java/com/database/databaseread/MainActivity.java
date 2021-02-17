package com.database.databaseread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText txtname,txtage,txtemail;
    Button btnsave;
    long maxId= 0;
    DatabaseReference reff;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtname=(EditText)findViewById(R.id.txtname);
        txtage=(EditText)findViewById(R.id.txtage);
        txtemail=(EditText)findViewById(R.id.txtemail);
        btnsave=(Button)findViewById(R.id.btnsave);
        member=new Member();
        reff= FirebaseDatabase.getInstance().getReference().child("Member");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxId=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int age=Integer.parseInt(txtage.getText().toString().trim());

                member.setName(txtname.getText().toString().trim());
                member.setAge(age);
                member.setEmail(txtemail.getText().toString().trim());

                reff.child(String.valueOf(maxId+1)).setValue(member);
                Toast.makeText(MainActivity.this,"Information sent to database!",Toast.LENGTH_LONG).show();
            }
        });

    }
}