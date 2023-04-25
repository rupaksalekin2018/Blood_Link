package uiu.app.bloodbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity implements View.OnClickListener {

    String user,pass;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String userKey = "phoneKey";
    public static final String passKey = "passKey";
    public static final String imgKey = "imageBitmap";
    public static final String nameKey = "nameOfuser";
    public static final String bloodKey = "b_groupofuser";
    public static final String emailKey = "emailofuser";
    public static final String genderKey = "genderofuser";
    com.google.android.material.textfield.TextInputEditText username , password;
    Button login,signup,forgot_pass;
    //private final String user="username";
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        //testPhoneAutoRetrieve();
        username = findViewById(R.id.username);
        password = findViewById(R.id.pass);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.btn_signup);
        forgot_pass = findViewById(R.id.btn_forgot_pass);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgot_pass.setOnClickListener(this);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, 0); // 0 - for private mode

        if(sharedpreferences.contains(userKey) && sharedpreferences.contains(passKey)){
            Intent intent = new Intent(Login.this,Home.class);
            //Intent intent = new Intent(Login.this,home.class);
            startActivity(intent);
            finish();
        }
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //here is your code
                if(!password.getText().toString().isEmpty()&&!username.getText().toString().isEmpty()){
                    login.setEnabled(true);
                }else{
                    login.setEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //here is your code
                if(!password.getText().toString().isEmpty()&&!username.getText().toString().isEmpty()){
                    login.setEnabled(true);
                }else{
                    login.setEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

    }

//    public void animate() {
//        ImageView v = (ImageView) findViewById(R.id.ivrate);
//        Drawable d = v.getDrawable();
//
//        d.registerAnimationCallback(new Animatable2.AnimationCallback() {
//            @Override
//            public void onAnimationEnd(Drawable drawable) {
//                avd.start();
//            }
//        });
//
//        drawable.start();
//
//        if (d instanceof AnimatedVectorDrawable) {
//            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
//            avd.start();
//        } else if (d instanceof AnimatedVectorDrawableCompat) {
//            AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) d;
//            avd.start();
//        }
//    }

    java.text.DateFormat df;
    java.util.Date date1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if(v == login){
            String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
            String num = username.getText().toString().trim();
            pass = password.getText().toString();
            user = "+"+code+num;
            LinearLayout linearLayout = findViewById(R.id.loginScreen);
            linearLayout.setVisibility(View.GONE);
            LinearLayout linearLayout1 = findViewById(R.id.rateLin);
            linearLayout1.setVisibility(View.VISIBLE);
            final ImageView animationView = (ImageView) findViewById(R.id.ivrate);
            final AnimatedVectorDrawable drawabl = (AnimatedVectorDrawable) getDrawable(R.drawable.heart_rate);
            animationView.setImageDrawable(drawabl);
            drawabl.registerAnimationCallback(new Animatable2.AnimationCallback() {
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    drawabl.start();
                }
            });

            drawabl.start();

            df = new java.text.SimpleDateFormat("hh:mm:ss");
            date1 = null;
            try {
                date1 = df.parse("18:40:10");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Toast.makeText(this, user+" "+pass,Toast.LENGTH_SHORT).show();
            String url = "https://bloodlink.mrrobi.tech/api/login/";
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        String user = response.getString("user");
//                        String pass = response.getString("pass");
//                        String name = response.getString("name");
//                        String img = response.getString("img");
//                        String msg = response.getString("msg");
//
//                        if(msg.equalsIgnoreCase("login")){
//                            SharedPreferences.Editor editor = sharedpreferences.edit();
//                            editor.putString(userKey, user);
//                            editor.putString(passKey,pass);
//                            editor.putString(nameKey,name);
//                            editor.putString(imgKey,img);
//                            Intent intent = new Intent(Login.this, Home.class);
//                            //Intent intent = new Intent(Login.this,home.class);
//                            startActivity(intent);
//                            finish();
//                        }else{
//                            System.out.println(msg+user+pass);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //
                    username.setText("");
                    password.setText("");

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String user = jsonObject.getString("user");
                        String pass = jsonObject.getString("pass");
                        String name = jsonObject.getString("name");
                        String img = jsonObject.getString("img");
                        String gender = jsonObject.getString("gender");
                        String email = jsonObject.getString("email");
                        Toast.makeText(Login.this,img+"",Toast.LENGTH_SHORT).show();
                        String msg = jsonObject.getString("msg");
                        String b_group = jsonObject.getString("b_group");
                        if(msg.equalsIgnoreCase("login")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(userKey, user);
                            editor.putString(passKey, pass);
                            editor.putString(nameKey, name);
                            editor.putString(imgKey, img);
                            editor.putString(bloodKey, b_group);
                            editor.putString(emailKey, email);
                            editor.putString(genderKey, gender);
                            editor.commit();
                            Intent intent = new Intent(Login.this, Home.class);
                            //Intent intent = new Intent(Login.this,home.class);
                            startActivity(intent);
                            finish();
                        }else{
                            System.out.println(msg+user+pass);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    if (response.equals("Login Successful...")) {
//                        Intent intent = new Intent(Login.this, Home.class);
//                        //Intent intent = new Intent(Login.this,home.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//
//                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", user);
                    params.put("pass", pass);
                    return params;
                }
            };
            MySingleton.getInstance(Login.this).addToRequestQueue(stringRequest);

        }else if(v == signup){
            //Toast.makeText(this,"signup Button pressed",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login.this, SignUpOne.class);
            startActivity(i);
        }else if(v == forgot_pass){
            Toast.makeText(this,"forgot Button pressed",Toast.LENGTH_SHORT).show();
        }
    }

}
