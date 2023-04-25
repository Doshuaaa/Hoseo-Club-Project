package com.example.hoseoclub;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                else {


                    builder.setMessage("사용할 수 있는 학번 입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.show();
                    usableEmail = true;
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
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pw = pwText.getText().toString();
                name = nameText.getText().toString();

                mFirebaseAuth.createUserWithEmailAndPassword(studentEmail, pw).addOnCompleteListener(RegisterActivity.this
                        , new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                    User user = new User(firebaseUser.getUid(),firebaseUser.getEmail(), pw, name);
                                    mDatabaseRef.child("User").child(firebaseUser.getUid()).setValue(user);
                                    Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT);
                                }
                            }
                        });
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
