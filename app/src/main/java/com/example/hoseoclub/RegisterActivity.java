package com.example.hoseoclub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;
    private EditText pwText, pwConfirmText, nameText, studentNumText;
    private Button numDupCheckBtn, registerBtn;
    String studentNum = null, pw = null, name = null;
    AlertDialog.Builder builder;
    Boolean usableId = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pwText = findViewById(R.id.pwText);
        pwConfirmText = findViewById(R.id.pwConfirmText);
        nameText = findViewById(R.id.nameText);
        studentNumText = findViewById(R.id.studentNumText);
        numDupCheckBtn = findViewById(R.id.numDupCheckBtn);
        registerBtn = findViewById(R.id.registerBtn);
        builder = new AlertDialog.Builder(this);

        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("User");

        studentNumText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                usableId = false;
            }
        });

        numDupCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentNum = studentNumText.getText().toString();
                if(studentNum.equals("")) {
                    builder.setMessage("아이디를 입력해주세요");
                    builder.setPositiveButton("확인", null);
                    builder.show();
                    return;
                }

                Query num = mDatabaseRef.child("User").child("StudentNum").equalTo(studentNum);
                num.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(!snapshot.getValue().equals("")) {
                            builder.setMessage("중복된 아이디 입니다.");
                            builder.setPositiveButton("확인", null);
                            builder.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
/*                String tempNum = num.toString();
                if(tempNum.equals("")) {
                    builder.setMessage("사용할 수 있는 학번 입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.show();
                    usableId = true;
                } else {
                    builder.setMessage("다른 사람이 사용하고 있는 학번 입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.show();
                }*/
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw = pwText.getText().toString();
                studentNum = studentNumText.getText().toString();
                name = nameText.getText().toString();


            }
        });
    }

    private void writeNewUser(String userId, String pw, String name, String email) {
        User user = new User(name, email);

        mDatabaseRef.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterActivity.this, "회원가입 되었습니다.", Toast.LENGTH_SHORT);
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다\n입력 정보를 다시 확인해주세요", Toast.LENGTH_SHORT);
            }
        });
    }
}
