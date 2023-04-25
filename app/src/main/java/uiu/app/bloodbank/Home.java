package uiu.app.bloodbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Random;

public class Home extends AppCompatActivity implements  View.OnClickListener{
    String[] questions = {"* After how many days can blood be given?", "* What is the minimum age of donating blood?", "* What is the minimum weight of donating blood?",
            "* How much blood is taken in blood donation?","* How long does it take to donate blood?","* Can blood be donated after taking antibiotics?",
            "* Can a patient who has blood pressure donate blood?", "* How long after the birth of baby can the mother give blood?", "* Can blood be given is case of having cold/fever?",
            "* Can a patient who has diabetics donate blood?", "* What should I do before donating blood"};

    String[] answers = {">> 120 days", ">> 18 years", ">> 47 kg", ">> 380-400 ml", ">> 5-7 minutes, highest 15 minutes",
            ">> No. You have to wait at least 7 days", ">> Yes, if his/her blood pressure is under control", ">> 15 months", ">> No", ">> No. But in some emergency case they can",
            ">> Avoid coffee or tea, avoid high fat foods, drink enough water"};

    private FirebaseAnalytics mFirebaseAnalytics;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String userKey = "phoneKey";
    public static final String passKey = "passKey";
    public static final String imgKey = "imageBitmap";
    public static final String nameKey = "nameOfuser";

    int imageArr[] = {R.drawable.bgo, R.drawable.bgt, R.drawable.bgth};
    ImageView iv;
    ImageView[][] pImageViews = new ImageView[2][3];
    TextView[][] pTextViews = new TextView[2][3];

    LinearLayout ll;

    TextView ques, ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iv = findViewById(R.id.sv_iv);
        ll = findViewById(R.id.clickableLinear);
        ques = findViewById(R.id.tvQuestions);
        ans = findViewById(R.id.tvAnswers);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, 0); // 0 - for private mode

        //Toast.makeText(this,sharedpreferences.getString(imgKey,"")+"",Toast.LENGTH_SHORT).show();

        ll.setOnClickListener(this);

        Runnable s = new  Runnable(){
            int i=0;
            public void run()
            {
                ques.setText(questions[i]);
                ans.setText(answers[i]);
                i++;
                if(i > questions.length-1)
                {
                    i = 0;
                }

                ques.postDelayed(this, 5000);
                //ans.postDelayed(this, 5000);

            }
        };
        ques.postDelayed(s, 5000);
        //ans.postDelayed(s, 5000);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Runnable r = new  Runnable(){
            int i=0;
            public void run()
            {
                iv.setImageResource(imageArr[i]);
                i++;
                if(i > imageArr.length-1)
                {
                    i = 0;
                }
                iv.postDelayed(this,5000);  //rest of the delay

            }
        };
        iv.postDelayed(r,5000);                     //for the 1st delay

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                String imgID = "iv"+i+j;
                String txtID = "tv"+i+j;
                pImageViews[i][j] = findViewById(getResources().getIdentifier(imgID, "id", getPackageName()));
                pTextViews[i][j] = findViewById(getResources().getIdentifier(txtID, "id", getPackageName()));

                pImageViews[i][j].setOnClickListener(this);
                pTextViews[i][j].setOnClickListener(this);
            }
        }
    }

    public void test(View view) {
        Toast.makeText(this,"LayoutClickableDone",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if(v == ll)
        {
            Intent i = new Intent(this, Facts.class);
            startActivity(i);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                    if(v == pImageViews[i][j] || v == pTextViews[i][j])
                    {
                        if(i == 0 && j == 0)
                        {
                            Intent it = new Intent(this, RequestForBlood.class);
                            startActivity(it);
                        }
                        else if(i == 0 && j == 1)
                        {
                            //Toast.makeText(this, "Feed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, Feed.class);
                            startActivity(intent);
                        }
                        else if(i == 0 && j == 2)
                        {
                            Toast.makeText(this, "Organization", Toast.LENGTH_SHORT).show();

                        }
                        else if(i == 1 && j == 0)
                        {
                            //Toast.makeText(this, "Ambulance", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(this, Ambulance.class);
                            startActivity(it);
                        }
                        else if(i == 1 && j == 1)
                        {
                            //Intent intent = new Intent(Home.this,MainActivity.class);
							Intent intent = new Intent(Home.this, Profile.class);
                            startActivity(intent);

                        }
                        else if(i == 1 && j == 2)
                        {
                            //Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show();
                            Intent x = new Intent(this, Info.class);
                            startActivity(x);
                        }
                    }
                }
            }
    }
}
