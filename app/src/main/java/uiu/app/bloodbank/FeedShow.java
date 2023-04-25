package uiu.app.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FeedShow extends AppCompatActivity implements View.OnClickListener {

    TextView bg, amb, ed, et, gender, addr, msg;
    Button map, call;
    String checkBG="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_show);

        bg = findViewById(R.id.tvBgShow);
        amb = findViewById(R.id.tvAmbShow);
        ed = findViewById(R.id.tvEdShow);
        et = findViewById(R.id.tvEtShow);
        gender = findViewById(R.id.tvGenderShow);
        addr = findViewById(R.id.tvAddressShow);
        msg = findViewById(R.id.tvMessageShow);
        map = findViewById(R.id.btMap);
        call = findViewById(R.id.btCallMe);

        map.setOnClickListener(this);
        call.setOnClickListener(this);

        Intent i = getIntent();
        bg.setText(i.getStringExtra("Blood_Group"));
        amb.setText(i.getStringExtra("Amount_of_Blood"));
        ed.setText(i.getStringExtra("Expected_Date"));
        et.setText(i.getStringExtra("Expected_Time"));
        gender.setText(i.getStringExtra("Gender"));
        addr.setText(i.getStringExtra("Address"));
        msg.setText(i.getStringExtra("Message"));
        checkBG = i.getStringExtra("Pass_Blood_Group");

        if(bg.getText().toString().equals(checkBG))
        {
            call.setEnabled(true);
        }
        else
        {
            call.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        Intent i = getIntent();
        if(v == map)
        {
            Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(i.getStringExtra("Address")));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        if(v == call)
        {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+i.getStringExtra("Phone")));
                startActivity(intent);
        }
    }
}
