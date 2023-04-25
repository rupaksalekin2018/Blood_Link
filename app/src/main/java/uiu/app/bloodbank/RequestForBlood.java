package uiu.app.bloodbank;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestForBlood extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String userKey = "phoneKey";
    public static final String passKey = "passKey";
    TextView showBlood;
    String blood="", bFactor="";
    RadioButton radioButton, mmale, ffemale, one, two, three, four;
    int checkRg1=0, checkRg2=0, checkRg3=0, checkRg4=0, bag;
    int currYear, currMonth, currDay, currHour, currMin;
    Button request, date, time;
    String gender="";
    com.google.android.material.textfield.TextInputEditText message, addresss;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_blood);

        showBlood = findViewById(R.id.tvRShowBlood);
        request = findViewById(R.id.requestBt);
        message = (com.google.android.material.textfield.TextInputEditText)findViewById(R.id.etReason);
        addresss = (com.google.android.material.textfield.TextInputEditText)findViewById(R.id.etAddress);
        date = findViewById(R.id.btD);
        time = findViewById(R.id.btT);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        //request.setOnClickListener(this);

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!message.getText().toString().isEmpty() && !addresss.getText().toString().isEmpty() && !date.getText().toString().equals("Expected date") && !time.getText().toString().equals("Expected time"))
                {
                    if(checkRg1 == 1 && checkRg2 == 1 && checkRg3 == 1 && checkRg4 == 1)
                    {
                        request.setEnabled(true);
                    }
                }
                else{
                    request.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addresss.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!message.getText().toString().isEmpty() && !addresss.getText().toString().isEmpty() && !date.getText().toString().equals("Expected date") && !time.getText().toString().equals("Expected time"))
                {
                    if(checkRg1 == 1 && checkRg2 == 1 && checkRg3 == 1 && checkRg4 == 1)
                    {
                        request.setEnabled(true);
                    }
                }
                else{
                    request.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        request.setOnClickListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
    }

    public void onRequestBloodSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.rrgBlood);
        switch (radioGroup.getCheckedRadioButtonId())
        {
            case R.id.rrbA:
                //Toast.makeText(this, "A blood group", Toast.LENGTH_SHORT).show();
                radioButton = findViewById(R.id.rrbA);
                blood = "A";
                if(bFactor.isEmpty())
                {
                    showBlood.setText("Blood group is: "+blood+bFactor);

                }
                else
                {
                    showBlood.setText("Blood group is: "+blood+bFactor+"(ve)");
                }
                checkRg1=1;
                break;
            case R.id.rrbB:
                //Toast.makeText(this, "B blood group", Toast.LENGTH_SHORT).show();
                radioButton = findViewById(R.id.rrbB);
                blood = "B";
                if(bFactor.isEmpty())
                {
                    showBlood.setText("Blood group is: "+blood+bFactor);

                }
                else
                {
                    showBlood.setText("Blood group is: "+blood+bFactor+"(ve)");
                }                checkRg1=1;
                break;
            case R.id.rrbO:
                //Toast.makeText(this, "O blood group", Toast.LENGTH_SHORT).show();
                radioButton = findViewById(R.id.rrbO);
                blood = "O";
                if(bFactor.isEmpty())
                {
                    showBlood.setText("Blood group is: "+blood+bFactor);

                }
                else
                {
                    showBlood.setText("Blood group is: "+blood+bFactor+"(ve)");
                }
                checkRg1=1;
                break;
            case R.id.rrbAB:
                //Toast.makeText(this, "AB blood group", Toast.LENGTH_SHORT).show();
                radioButton = findViewById(R.id.rrbAB);
                blood = "AB";
                if(bFactor.isEmpty())
                {
                    showBlood.setText("Blood group is: "+blood+bFactor);

                }
                else
                {
                    showBlood.setText("Blood group is: "+blood+bFactor+"(ve)");
                }                checkRg1=1;
                break;
        }

        if(checkRg1 == 1 && checkRg2 == 1 && checkRg3 == 1 && checkRg4 == 1)
        {
            if(!message.getText().toString().isEmpty() && !addresss.getText().toString().isEmpty() && !date.getText().toString().equals("Expected date") && !time.getText().toString().equals("Expected time"))
            {
                request.setEnabled(true);
            }
        }
        else
        {
            request.setEnabled(false);
        }


    }

    public void onRequestFactorSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.rrgFactor);
        switch (radioGroup.getCheckedRadioButtonId())
        {
            case R.id.rrbPlus:
                //Toast.makeText(this, "+ factor", Toast.LENGTH_SHORT).show();
                radioButton = findViewById(R.id.rrbPlus);
                bFactor = "+";
                showBlood.setText("Blood group is: "+blood+bFactor+"(ve)");
                checkRg2=1;
                break;
            case R.id.rrbMinus:
                //Toast.makeText(this, "- factor", Toast.LENGTH_SHORT).show();
                radioButton = findViewById(R.id.rrbMinus);
                bFactor = "-";
                showBlood.setText("Blood group is: "+blood+bFactor+"(ve)");
                checkRg2=1;
                break;
        }

        if(checkRg1 == 1 && checkRg2 == 1 && checkRg3 == 1 && checkRg4 == 1)
        {
            if(!message.getText().toString().isEmpty() && !addresss.getText().toString().isEmpty() && !date.getText().toString().equals("Expected date") && !time.getText().toString().equals("Expected time"))
            {
                request.setEnabled(true);
            }
        }
        else
        {
            request.setEnabled(false);
        }


    }

    @Override
    public void onClick(View v) {
        if(v == request)
        {
            String url = "https://bloodlink.mrrobi.tech/api/bloodReq";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(RequestForBlood.this,response,Toast.LENGTH_SHORT).show();
                    //SharedPreferences.Editor editor = sharedpreferences.edit();
                    //editor.putString(userKey, phone);
                    //editor.putString(passKey, pass);
                    //editor.commit();
                    if(response.equals("Request Successful...")){
                        Intent intent = new Intent(RequestForBlood.this,Home.class);
                        //Intent intent = new Intent(Login.this,home.class);
                        startActivity(intent);
                        finish();
                    }else{

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error");
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("user",sharedpreferences.getString(userKey, ""));
                    params.put("msg",message.getText().toString());
                    params.put("time",time.getText().toString());
                    params.put("date",date.getText().toString());
                    params.put("amount",bag+"");
                    params.put("b_group",blood+bFactor);
                    params.put("gender",gender);
                    params.put("hospital",addresss.getText().toString());
                    return params;
                }
            };
            MySingleton.getInstance(RequestForBlood.this).addToRequestQueue(stringRequest);
            Toast.makeText(this, "Request has sent", Toast.LENGTH_SHORT).show();
        }
        else if(v == date)
        {
            DatePicker datePicker = new DatePicker(this);
            currDay = datePicker.getDayOfMonth();
            currMonth = datePicker.getMonth()+1;
            currYear = datePicker.getYear();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            date.setText(dayOfMonth + "-" + (month+1) + "-" + year);
                            if(checkRg1 == 1 && checkRg2 == 1 && checkRg3 == 1 && checkRg4 == 1)
                            {
                                if(!message.getText().toString().isEmpty() && !addresss.getText().toString().isEmpty() && !date.getText().toString().equals("Expected date") && !time.getText().toString().equals("Expected time"))
                                {
                                    request.setEnabled(true);
                                }
                            }
                            else
                            {
                                request.setEnabled(false);
                            }

                        }
                    },
                    currYear, currMonth, currDay);
            datePickerDialog.show();
        }
        else if(v == time)
        {
            final TimePicker timePicker = new TimePicker(this);
            currHour = timePicker.getCurrentHour();
            currMin = timePicker.getCurrentMinute();

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            time.setText(hourOfDay+":"+minute);
                            if(checkRg1 == 1 && checkRg2 == 1 && checkRg3 == 1 && checkRg4 == 1)
                            {
                                if(!message.getText().toString().isEmpty() && !addresss.getText().toString().isEmpty() && !date.getText().toString().equals("Expected date") && !time.getText().toString().equals("Expected time"))
                                {
                                    request.setEnabled(true);
                                }
                            }
                            else
                            {
                                request.setEnabled(false);
                            }

                        }
                    }, currHour, currMin, true);
            timePickerDialog.show();
        }
    }

    public void onRequestGenderSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.rrgGender);
        mmale = findViewById(R.id.rrbMale);
        ffemale = findViewById(R.id.rrbFemale);
        switch (radioGroup.getCheckedRadioButtonId())
        {
            case R.id.rrbMale:
                //Toast.makeText(this, "+ factor", Toast.LENGTH_SHORT).show();
                gender = "male";
				mmale.setTextColor(getResources().getColor(R.color.colorWhite));
				ffemale.setTextColor(getResources().getColor(R.color.new_color));
                checkRg3=1;
                break;
            case R.id.rrbFemale:
                //Toast.makeText(this, "- factor", Toast.LENGTH_SHORT).show();
                gender = "female";
				mmale.setTextColor(getResources().getColor(R.color.new_color));
				ffemale.setTextColor(getResources().getColor(R.color.colorWhite));
                checkRg3=1;
                break;
        }

        if(checkRg1 == 1 && checkRg2 == 1 && checkRg3 == 1 && checkRg4 == 1)
        {
            if(!message.getText().toString().isEmpty() && !addresss.getText().toString().isEmpty() && !date.getText().toString().equals("Expected date") && !time.getText().toString().equals("Expected time"))
            {
                request.setEnabled(true);
            }
        }
        else
        {
            request.setEnabled(false);
        }


    }

    public void onRequestBagSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.rrgBag);
        one = findViewById(R.id.rrbOne);
        two = findViewById(R.id.rrbTwo);
        three = findViewById(R.id.rrbThree);
        four = findViewById(R.id.rrbFour);
        switch (radioGroup.getCheckedRadioButtonId())
        {
            case R.id.rrbOne:
                //Toast.makeText(this, "+ factor", Toast.LENGTH_SHORT).show();
                bag = 1;
				one.setTextColor(getResources().getColor(R.color.colorWhite));
				two.setTextColor(getResources().getColor(R.color.new_color));
				three.setTextColor(getResources().getColor(R.color.new_color));
				four.setTextColor(getResources().getColor(R.color.new_color));
                checkRg4=1;
                break;
            case R.id.rrbTwo:
                //Toast.makeText(this, "- factor", Toast.LENGTH_SHORT).show();
                bag = 2;
				one.setTextColor(getResources().getColor(R.color.new_color));
				two.setTextColor(getResources().getColor(R.color.colorWhite));
				three.setTextColor(getResources().getColor(R.color.new_color));
				four.setTextColor(getResources().getColor(R.color.new_color));
                checkRg4=1;
                break;
            case R.id.rrbThree:
                //Toast.makeText(this, "- factor", Toast.LENGTH_SHORT).show();
                bag = 3;
				one.setTextColor(getResources().getColor(R.color.new_color));
				two.setTextColor(getResources().getColor(R.color.new_color));
				three.setTextColor(getResources().getColor(R.color.colorWhite));
				four.setTextColor(getResources().getColor(R.color.new_color));
                checkRg4=1;
                break;
            case R.id.rrbFour:
                //Toast.makeText(this, "- factor", Toast.LENGTH_SHORT).show();
                bag = 4;
				one.setTextColor(getResources().getColor(R.color.new_color));
				two.setTextColor(getResources().getColor(R.color.new_color));
				three.setTextColor(getResources().getColor(R.color.new_color));
				four.setTextColor(getResources().getColor(R.color.colorWhite));
                checkRg4=1;
                break;
        }

        if(checkRg1 == 1 && checkRg2 == 1 && checkRg3 == 1 && checkRg4 == 1)
        {
            if(!message.getText().toString().isEmpty() && !addresss.getText().toString().isEmpty() && !date.getText().toString().equals("Expected date") && !time.getText().toString().equals("Expected time"))
            {
                request.setEnabled(true);
            }
        }
        else
        {
            request.setEnabled(false);
        }

    }
}
