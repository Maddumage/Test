package com.example.amilah.test.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amilah.test.database.DBHelper;
import com.example.amilah.test.R;
import com.example.amilah.test.models.Marks;
import com.example.amilah.test.models.Subject;
import com.example.amilah.test.models.User;
import com.example.amilah.test.utill.BitmapUtility;
import com.example.amilah.test.utill.Constant;

import java.io.ByteArrayOutputStream;

public class LoginActivity extends AppCompatActivity {

    EditText username,pword;
    TextView SignUp;
    Button login;
    String uName;
    String pWord;

    DBHelper dbHelper;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.input_username);
        pword = (EditText)findViewById(R.id.input_password);
        SignUp = (TextView) findViewById(R.id.tvSignUp);
        login = (Button)findViewById(R.id.btnLogin);

        Log.i("username2","usernme "+uName+ "----password "+pWord);

        dbHelper = new DBHelper(LoginActivity.this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uName = username.getText().toString();
                pWord = pword.getText().toString();

                id = dbHelper.checkUser(new User(uName,pWord));
                if(id==-1)
                {
                    Toast.makeText(getApplicationContext(),"User Does not Exist!",Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences preferences = getSharedPreferences("USER_INFO",MODE_PRIVATE);
                    if(!preferences.contains("username2")&& !preferences.contains("password")){
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username2",uName);
                        editor.putString("password",pWord);
                        editor.putInt("id",id);
                        editor.commit();
                        Log.i("Editor_when_login --",editor.toString());
                    }
                    Toast.makeText(getApplicationContext(),"Login Success!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),NavigationActivity.class);
                    Constant.KEY_LOGIN = true;
                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    String img ;
                    img = dbHelper.getImage(id);
                   //Log.i("image from db--",""+img.length);
                    if(img!=null)
                    {
                        intent.putExtra("image",img);

                    }
                    else {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user);
                        String base64Image = BitmapUtility.getString(bitmap);
                        intent.putExtra("image", base64Image);
                    }
                    startActivity(intent);
                }
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent signUp = new Intent(getApplicationContext(),SignUpActivity.class);
//                startActivity(signUp);
                dbHelper.addUser(new User("roshan","123"));
                dbHelper.addUser(new User("kasun","456"));
                Subject s1 = new Subject();
                Subject s2 = new Subject();
                Subject s3 = new Subject();
                Subject s4 = new Subject();
                s1.setSubName("Android");
                s1.setPosition(1);
                s1.setSid(1);
                s2.setSubName("Java");
                s2.setPosition(2);
                s2.setSid(1);
                s3.setSubName("C#");
                s3.setPosition(3);
                s3.setSid(1);
                s4.setSubName("Ruby");
                s4.setPosition(4);
                s4.setSid(1);
                dbHelper.addSubject(s1);
                dbHelper.addSubject(s2);
                dbHelper.addSubject(s3);
                dbHelper.addSubject(s4);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            super.onRestart();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
