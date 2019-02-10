package com.example.adteam7.team7_ad_client.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Delegate Department Head");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
                new AsyncCallerSet().execute();
                finish();
            }
        });

        Button revoke = this.findViewById(R.id.revoke);
        revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncCallerRevokeDep().execute();
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
            final Calendar myCalendarEnd = Calendar.getInstance();
            final EditText startDate = findViewById(R.id.startDate);

            String myFormat = "dd/MMMM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            startDate.setText(sdf.format(myCalendar.getTime()));
//            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//                @Override
//                public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                      int dayOfMonth) {
//                    // TODO Auto-generated method stub
//                    myCalendar.set(Calendar.YEAR, year);
//                    myCalendar.set(Calendar.MONTH, monthOfYear);
//                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                    updateLabel();
//                }
//
//                private void updateLabel() {
//                    String myFormat = "dd/MMMM/yyyy"; //In which you need put here
//                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//                    startDate.setText(sdf.format(myCalendar.getTime()));
//                }
//            };
//
//            startDate.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    new DatePickerDialog(DelegateDepHeadActivity.this, date, myCalendar
//                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                }
//            });

            //Converting EndDate text view to date spinner
            final EditText endDate = findViewById(R.id.endDate);
            final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendarEnd.set(Calendar.YEAR, year);
                    myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                    myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

                private void updateLabel() {
                    String myFormat = "dd/MMMM/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    endDate.setText(sdf.format(myCalendarEnd.getTime()));
                }
            };

            endDate.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    new DatePickerDialog(DelegateDepHeadActivity.this, date2, myCalendarEnd
//                            .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
//                            myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
//                }
//            });

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    DatePickerDialog dpdialog = new DatePickerDialog(DelegateDepHeadActivity.this, date2, myCalendarEnd
                            .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                            myCalendarEnd.get(Calendar.DAY_OF_MONTH));
                    dpdialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                    dpdialog.show();
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
            final Button revoke = findViewById(R.id.revoke);
            if (depHeadApiModel.getDelegatedDepartmentHeadName() == null) {
                //this method will be running on UI thread
                revoke.setVisibility(View.INVISIBLE);
                final Spinner employeeListddl = findViewById(R.id.employeeList);
                List<String> selection = new ArrayList<>();
                for (EmployeeDto current : depHeadApiModel.getEmployees()) {
                    selection.add(current.getName());
                }
                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(DelegateDepHeadActivity.this, android.R.layout.simple_list_item_1, selection);
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                employeeListddl.setAdapter(myAdapter);

            } else {
                //hide all controls except depheadname labal and revoke button
                final TextView depHeadNamelbl = findViewById(R.id.depHeadName);
                depHeadNamelbl.setText(depHeadApiModel.getDelegatedDepartmentHeadName());
                final Button submit = findViewById(R.id.submit);
                final Button cancel = findViewById(R.id.cancel);
                final TextView textView4 = findViewById(R.id.textView4);
                final TextView textView5 = findViewById(R.id.textView5);
                final Spinner employeeList = findViewById(R.id.employeeList);
                final EditText startDate = findViewById(R.id.startDate);
                final EditText endDate = findViewById(R.id.endDate);
                final TextView textView6 = findViewById(R.id.textView6);
                employeeList.setVisibility(View.INVISIBLE);
                startDate.setVisibility(View.INVISIBLE);
                endDate.setVisibility(View.INVISIBLE);
                textView4.setVisibility(View.INVISIBLE);
                textView5.setVisibility(View.INVISIBLE);
                textView6.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.INVISIBLE);
                revoke.setVisibility(View.VISIBLE);

            }


        }
    }

    //For posting details to server
    //For displaying items when entered
    private class AsyncCallerSet extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(DelegateDepHeadActivity.this);

        Spinner employeeListddl = findViewById(R.id.employeeList);
        EditText endDate = findViewById(R.id.endDate);
        EditText startDate = findViewById(R.id.startDate);
        String status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
        }

        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            DelegateDepHeadApiModel newActDepHead = new DelegateDepHeadApiModel(
                    startDate.getText().toString().trim(),
                    endDate.getText().toString().trim(),
                    null,
                    employeeListddl.getSelectedItem().toString(),
                    null,
                    null
            );
            status = agent.delegateActingDepHeadSet(newActDepHead);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            Toast.makeText(DelegateDepHeadActivity.this, status, Toast.LENGTH_SHORT).show();
            finish();
            pdLoading.dismiss();
        }
    }

    //For posting details to server
    //For displaying items when entered
    private class AsyncCallerRevokeDep extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(DelegateDepHeadActivity.this);
        String status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
        }

        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            status = agent.delegateActingDepHeadRevoke();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            Toast.makeText(DelegateDepHeadActivity.this, status, Toast.LENGTH_SHORT).show();
            finish();
            pdLoading.dismiss();
        }
    }
}