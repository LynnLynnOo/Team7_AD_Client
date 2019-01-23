package com.example.adteam7.team7_ad_client.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.Employee;
import com.example.adteam7.team7_ad_client.data.ManageDepRep;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kay Thi Swe Tun
 **/
public class ManageDepRepActivity extends AppCompatActivity {

Spinner spinEmp;
Button assign;
TextView depname,rep;
    final ManageDepRep dep=new ManageDepRep();

    APIDataAgent agent=new APIDataAgentImpl();
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
            }
        }.execute();
        String newrep;
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ManageDepRepActivity.this, "Assigned " + spinEmp.getSelectedItem() + " as Department Representative", Toast.LENGTH_SHORT).show();
                String repid = checkSelectedEmp();
                rep.setText(spinEmp.getSelectedItem().toString());
                dep.setDepartmentRepName(spinEmp.getSelectedItem().toString());
                dep.setDepartmentRepId(repid);
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        return agent.delegateDepHeadSet(dep);

                    }

                    @Override
                    protected void onPostExecute(String aVoid) {
                        super.onPostExecute(aVoid);
                    }
                }.execute();

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
