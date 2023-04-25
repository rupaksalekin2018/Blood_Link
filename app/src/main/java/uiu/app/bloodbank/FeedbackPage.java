package uiu.app.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FeedbackPage extends AppCompatActivity implements View.OnClickListener{

    TextView name, email;
    com.google.android.material.textfield.TextInputEditText eym;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);

        name = findViewById(R.id.tvName);
        email = findViewById(R.id.tvEmail);
        eym = findViewById(R.id.etEYM);
        send = findViewById(R.id.btSend);

        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
