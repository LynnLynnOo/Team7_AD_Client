package com.example.adteam7.team7_ad_client.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.Employee;
import com.example.adteam7.team7_ad_client.data.ManageDepRep;
import com.example.adteam7.team7_ad_client.data.SessionManager;
import com.example.adteam7.team7_ad_client.data.StationeryRequestApiModel;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;
import com.example.adteam7.team7_ad_client.network.SendMailTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kay Thi Swe Tun
 **/
public class ManageDepRepActivity extends AppCompatActivity {

    Spinner spinEmp;
    Button assign;
    TextView depname, rep;
    String newrep;
    final ManageDepRep dep = new ManageDepRep();

    APIDataAgent agent = new APIDataAgentImpl();
SessionManager sessionManager=SessionManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_dep_rep);
        spinEmp = findViewById(R.id.sprEmp);
        assign = findViewById(R.id.assign);
        depname = findViewById(R.id.depname);
        rep = findViewById(R.id.currep);

        new AsyncTask<Void, Void, ManageDepRep>() {
            @Override
            protected ManageDepRep doInBackground(Void... params) {
                // dep= ;                      //  tes =agent.login(username.getText().toString(),passwrod.getText().toString());
                return agent.delegateDepHeadGet();
            }

            @Override
            protected void onPostExecute(ManageDepRep manageDepRep) {
                // dep=manageDepRep;
                if (manageDepRep != null) {
                    dep.setEmployees(manageDepRep.getEmployees());
                    dep.setDepartmentRepId(manageDepRep.getDepartmentRepId());
                    dep.setDepartmentRepName(manageDepRep.getDepartmentRepName());
                    dep.setDepartmentId(manageDepRep.getDepartmentId());

                    dep.setDepartmentname(manageDepRep.getDepartmentname());
                    List<String> empnamelist = new ArrayList<>();
                    for (Employee e : dep.getEmployees()) {
                        empnamelist.add(e.getName());
                    }

                    ArrayAdapter<String> spinneradapter;
                    spinneradapter = new ArrayAdapter<String>(
                            ManageDepRepActivity.this,
                            android.R.layout.simple_list_item_1,
                            empnamelist);
                    spinEmp.setAdapter(spinneradapter);
                    depname.setText(dep.getDepartmentname());
                    rep.setText(dep.getDepartmentRepName());
                }else{
                    Toast.makeText(ManageDepRepActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if (dep.getEmployees() != null) {
                    String repid = checkSelectedEmp();
                    rep.setText(spinEmp.getSelectedItem().toString());
                    dep.setDepartmentRepName(spinEmp.getSelectedItem().toString());
                    dep.setDepartmentRepId(repid);
                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... voids) {
                            return agent.assignDepRep(dep);

                        }

                        @Override
                        protected void onPostExecute(String aVoid) {
                            if(aVoid.equals("1\n")){
                                String subject = "Deligation of authority";
                                String content ="Kindly remind: \n You have been assigned as an department representative.\n Assigned by " + sessionManager.getUsername() + ".";
                                new SendMailTask(ManageDepRepActivity.this).execute(new String[]{"kaythiswetun@u.nus.edu",subject,content});

                                Toast.makeText(ManageDepRepActivity.this, "Assigned " + spinEmp.getSelectedItem() + " as Department Representative", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    }.execute();

                }
                else{
                    Toast.makeText(ManageDepRepActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private String checkSelectedEmp() {
        String newRepId = "";
        String n = spinEmp.getSelectedItem().toString();
        for (Employee e : dep.getEmployees()
        ) {
            if (e.getName() == n) {
                newRepId = e.getEmpid();
                return newRepId;
            }
        }
        return newRepId;
    }
}
