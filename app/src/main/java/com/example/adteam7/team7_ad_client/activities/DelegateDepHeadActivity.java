package com.example.adteam7.team7_ad_client.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.DelegateDepHeadApiModel;
import com.example.adteam7.team7_ad_client.data.EmployeeDto;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DelegateDepHeadActivity extends AppCompatActivity {
    APIDataAgent agent = new APIDataAgentImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_dep_head);
        new AsyncCallerGet().execute();

        Button cancel = this.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
//        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
//                super.onBackPressed();
//        Intent i = new Intent(DelegateDepHeadActivity.this, MainActivity.class);
                //        startActivity(i);
            }
        });

        Button submit = this.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
                Spinner employeeListddl = findViewById(R.id.employeeList);
                EditText endDate = findViewById(R.id.endDate);
                EditText startDate = findViewById(R.id.startDate);

                DelegateDepHeadApiModel newActDepHead = new DelegateDepHeadApiModel(
                        startDate.getText().toString().trim(),
                        endDate.getText().toString().trim(),
                        null,
                        employeeListddl.getSelectedItem().toString(),
                        null,
                        null
                );
                String status = agent.delegateActingDepHeadSet(newActDepHead);
                Toast.makeText(DelegateDepHeadActivity.this, status, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    //For displaying items when entered
    private class AsyncCallerGet extends AsyncTask<Void, Void, DelegateDepHeadApiModel> {
        ProgressDialog pdLoading = new ProgressDialog(DelegateDepHeadActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            //Converting StartDate text view to date spinner

            final Calendar myCalendar = Calendar.getInstance();
            final EditText startDate = findViewById(R.id.startDate);
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

                private void updateLabel() {
                    String myFormat = "dd/MMMM/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    startDate.setText(sdf.format(myCalendar.getTime()));
                }
            };

            startDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(DelegateDepHeadActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            //Converting EndDate text view to date spinner
            final EditText endDate = findViewById(R.id.endDate);
            final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

                private void updateLabel() {
                    String myFormat = "dd/MMMM/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    endDate.setText(sdf.format(myCalendar.getTime()));
                }
            };

            endDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(DelegateDepHeadActivity.this, date2, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }

        @Override
        protected DelegateDepHeadApiModel doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            return agent.delegateActingDepHeadGet();
        }

        @Override
        protected void onPostExecute(DelegateDepHeadApiModel depHeadApiModel) {
            super.onPostExecute(depHeadApiModel);

            //this method will be running on UI thread
            final TextView depHeadNamelbl = findViewById(R.id.depHeadName);
            final Spinner employeeListddl = findViewById(R.id.employeeList);
            List<String> selection = new ArrayList<>();
            for (EmployeeDto current : depHeadApiModel.getEmployees()) {
                selection.add(current.getName());
            }
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(DelegateDepHeadActivity.this, android.R.layout.simple_list_item_1, selection);
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            employeeListddl.setAdapter(myAdapter);
            depHeadNamelbl.setText(depHeadApiModel.getDelegatedDepartmentHeadName());
        }
    }

    //For posting details to server
}
