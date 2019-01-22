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

/**
 * Created by Kay Thi Swe Tun
 **/
public class LoginActivity extends AppCompatActivity {

APIDataAgent agent=new APIDataAgentImpl();
    EditText username,passwrod;
    private SessionManager session;

    Button login;
    boolean tes=false;
    String u,p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        username=findViewById(R.id.username);
        passwrod=findViewById(R.id.password);
        session = SessionManager.getInstance();
        u=username.getText().toString();
        p=passwrod.getText().toString();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                       tes =agent.login(u,p);
                        return tes;
                    }

                    @Override
                    protected void onPostExecute(Boolean res) {
                        //show(emp);
                        if (res){

                            session.createLoginSession(u,p);

                            Intent i=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }.execute();



            }
        });

    }
}
