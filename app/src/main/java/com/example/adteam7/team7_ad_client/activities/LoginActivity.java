package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.SessionManager;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;
import com.example.adteam7.team7_ad_client.network.JSONParser;

/**
 * Created by Kay Thi Swe Tun
 **/
public class LoginActivity extends AppCompatActivity {
    EditText email, password;
APIDataAgent agent=new APIDataAgentImpl();
    EditText username,passwrod;
    private SessionManager session;

    Button login;
    boolean tes=false;
    String mail, pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        session = SessionManager.getInstance();
//        email.setText("Google is your friend.");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        mail = email.getText().toString().trim();
                        pw = password.getText().toString().trim();
                        String result = agent.login(mail, pw);

                        return result;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        //show(emp);
                        if (result != "fail") {
                            String c= JSONParser.access_token;
                            session.createLoginSession(mail, pw, result,c);

                            Intent i=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Username and password", Toast.LENGTH_SHORT).show();

                        }
                    }
                }.execute();



            }
        });

    }
}
