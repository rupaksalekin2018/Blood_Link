package uiu.app.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Info extends AppCompatActivity implements View.OnClickListener{

    LinearLayout contactUs,feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        contactUs = findViewById(R.id.contactus);
        feedback = findViewById(R.id.feedback);

        feedback.setOnClickListener(this);
        contactUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == contactUs)
        {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:01719445899"));
            startActivity(i);
        }
        if(v == feedback)
        {
            Intent i = new Intent(this, FeedbackPage.class);
            startActivity(i);
        }
    }
}
