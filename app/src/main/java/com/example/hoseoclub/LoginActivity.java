package com.example.hoseoclub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText idText;
    private EditText pwText;
    private Button loginButton;
    private TextView registerText;
    private Spinner universitySpinner;
    private ImageView universityImageView;
    private LinearLayout universityLinearLayout;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<String> universityNameList;
    private ArrayAdapter<String> arrayAdapter;
    private ProgressDialog customProgressDialog;
    public static Context contextLogin;
    public String universityName = null;

    private String loginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        contextLogin = this;

        idText = findViewById(R.id.idText);
        pwText = findViewById(R.id.pwText);
        universitySpinner = findViewById(R.id.schoolSpinner);
        universityImageView = findViewById(R.id.loginUniversityImageView);
        universityLinearLayout = findViewById(R.id.loginLinearLayout);
        loginButton = findViewById(R.id.loginButton);

        //로딩 다이얼로그 설정
        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customProgressDialog.setCancelable(false);

        //
        ArrayList<UniversityList> universityList = new ArrayList<>();

        universityNameList = new ArrayList<>();

        universityNameList.add("학교를 선택해주세요.");

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("University");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datasnapshot : snapshot.getChildren()) {
                    University university = datasnapshot.getValue(University.class);
                    universityNameList.add(university.getName());
//                    universityImageList.add(university.getImage());
//                    universityBackgroundList.add(university.getColor());
                    universityList.add(new UniversityList(university.getName(), university.getImage(), university.getColor()));
                }
                arrayAdapter = new ArrayAdapter<>(LoginActivity.this, android.R.layout.simple_spinner_item, universityNameList);

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                universitySpinner.setAdapter(arrayAdapter);


                universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        customProgressDialog.show();
                        int realPosition = position - 1;
                        if(realPosition == -1) {
                            universityImageView.setImageResource(R.drawable.sample);
                            universityLinearLayout.setBackgroundColor(Color.parseColor("#BFBABA"));
                            customProgressDialog.dismiss();
                            return;
                        }
//                        Glide.with(LoginActivity.this).load(universityImageList.get(realPosition))
//                                        .into(universityImageView);
//                        universityLinearLayout.setBackgroundColor(Color.parseColor(universityBackgroundList.get(realPosition)));

                        Glide.with(LoginActivity.this).load(universityList.get(realPosition).getImage())
                                .into(universityImageView);
                        universityLinearLayout.setBackgroundColor(Color.parseColor(universityList.get(realPosition).getColor()));
                        customProgressDialog.dismiss();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(LoginActivity.this, "nothing", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        registerText = findViewById(R.id.registerText);

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                universityName = universitySpinner.getSelectedItem().toString();


                if(universityName.equals("학교를 선택해주세요.")) {
                    Toast.makeText(LoginActivity.this, "학교를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                String id = idText.getText().toString();
                String pw = pwText.getText().toString();
                databaseReference = database.getReference("User");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    boolean isMember = false;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if(dataSnapshot.getKey().toString().equals(id)) {
                                String pwData = dataSnapshot.child("userPassword").getValue(String.class);
                                if(!pwData.equals(pw)) {
                                    Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                                    databaseReference.removeEventListener(this);
                                } else {
                                    loginId = id;
                                    isMember = true;
                                    databaseReference.removeEventListener(this);
                                    break;
                                }
                            }
                        }
                        if(isMember) {
                            databaseReference.removeEventListener(this);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("loginId", loginId);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                databaseReference.child(id).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            String temp = dataSnapshot.getKey();
//                            String pwData = dataSnapshot.child("userPassword").getValue(String.class);
//                            if(pw.equals(pwData)) {
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                startActivity(intent);
//
//                            }
//                            Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


            }
        });
    }
}
