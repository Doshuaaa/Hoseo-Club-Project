package com.example.hoseoclub;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;
    private EditText pwText, pwConfirmText, nameText, studentEmailText;
    private Button emailDupBtn, registerBtn;
    private TextView correctEmailText, correctPwText;
    String studentEmail = null, pw = null, name = null;
    int position = -1;
    AlertDialog.Builder builder;
    Boolean usableEmail = false;
    private FirebaseUser firebaseUser;
    private String[] hoseoEmail;
    String temp = null;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pwText = findViewById(R.id.pwText);
        pwConfirmText = findViewById(R.id.pwConfirmText);
        nameText = findViewById(R.id.nameText);
        studentEmailText = findViewById(R.id.studentEmailText);
        emailDupBtn = findViewById(R.id.numDupCheckBtn);
        registerBtn = findViewById(R.id.registerBtn);
        builder = new AlertDialog.Builder(this);
        correctEmailText = findViewById(R.id.correctEmailText);
        correctPwText = findViewById(R.id.correctPwText);

        mFirebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("User");

        studentEmailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                usableEmail = false;
            }
        });

        emailDupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentEmail = studentEmailText.getText().toString();
                position = studentEmail.indexOf("@");
                pw = pwText.getText().toString();
                name = nameText.getText().toString();
                hoseoEmail = studentEmail.split("@");

                if(studentEmail.equals("")) {
                    builder.setMessage("이메일을 입력해 주세요");
                    builder.setPositiveButton("확인", null);
                    builder.show();
                    return;
                }

                else if(position == -1) {
                    correctEmailText.setTextColor(Color.RED);
                    correctEmailText.setText("올바른 이메일을 입력해 주세요.");
                    return;
                }

                else if(!hoseoEmail[1].equals("vision.hoseo.edu")) {
                    correctEmailText.setText("올바른 학교 이메일형식이 아닙니다.");
                    return;
                }

                if(pw.length() < 6) {
                    correctPwText.setText("6자리 이상의 비밀번호를 설정해주세요.");
                    return;
                }
                else if(!pw.equals(pwConfirmText.getText().toString())) {
                    correctEmailText.setText("비밀번호가 일치하지 않습니다.");
                    return;
                }

                mDatabaseRef.child(hoseoEmail[0]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        temp = snapshot.child("userEmail").getValue(String.class);

                        if(temp != null) {
                            builder.setMessage("이미 존재하는 이메일 입니다.")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            builder.create().dismiss();
                                        }
                                    })
                                    .show();
                            return;
                        }

                        mFirebaseAuth.createUserWithEmailAndPassword(studentEmail, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                firebaseUser = mFirebaseAuth.getCurrentUser();

                                if(firebaseUser == null) {
                                    return;
                                }

                                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "이메일 인증을 보냈습니다.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "이메일 인증 발송에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                flag = true;

            }


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


        });




        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flag) {
                    builder.setMessage("이메일 인증을 해주세요").setPositiveButton("확인", null).show();
                    return;
                }

                firebaseUser.reload();
                boolean a = firebaseUser.isEmailVerified();
                if(firebaseUser == null) {
                    builder.setMessage("회원가입에 실패했습니다");
                    builder.setPositiveButton("확인", null);
                    builder.show();
                    return;
                }
                else if(!firebaseUser.isEmailVerified()) {
                    builder.setMessage("이메일 인증을 해주세요.");
                    builder.setPositiveButton("확인", null);
                    builder.show();
                    return;
                } else {
                    mDatabaseRef.child(hoseoEmail[0]).setValue(new User(hoseoEmail[0], firebaseUser.getUid(), name, pw, studentEmail));
//                    builder.setMessage();
//                    builder.setPositiveButton("확인", null);
//                    builder.show();
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this)
                            .setMessage("회원가입에 완료했습니다.")
                            .setPositiveButton("확인", null)
                            .show();
                    alertDialog.dismiss();
                    finish();
                }

            }
        });
    }

}
