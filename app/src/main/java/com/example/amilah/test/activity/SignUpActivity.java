package com.example.amilah.test.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amilah.test.R;
import com.example.amilah.test.database.DBHelper;
import com.example.amilah.test.models.User;

public class SignUpActivity extends AppCompatActivity {

    EditText uName,pWord;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        uName = (EditText)findViewById(R.id.inputUName);
        pWord = (EditText)findViewById(R.id.inputUPassword);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                String name = uName.getText().toString();
                String pw = pWord.getText().toString();
                dbHelper.addUser(new User(name,pw));
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
