package uiu.app.bloodbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;

public class Ambulance extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    AmbulanceAdapter ambulanceAdapter;
    SearchView searchViewAmbulance;
    ArrayList<Ambulance_data> ambulances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

        recyclerView = findViewById(R.id.rvAmbulance);
        searchViewAmbulance = findViewById(R.id.searchviewAmbulance);
        searchViewAmbulance.setOnQueryTextListener(this);

        Bg_Task bg_task = new Bg_Task(Ambulance.this);

        ambulances = bg_task.getAmbulancesList();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println(ambulances.size());

                ambulanceAdapter = new AmbulanceAdapter(ambulances,Ambulance.this);
                recyclerView.setAdapter(ambulanceAdapter);
            }
        },1000);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        ambulanceAdapter.setOnItemClickListener(new AmbulanceAdapter.ClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//
//            }
//
//            @Override
//            public void onItemLongClick(int position, View v) {
//                Intent i = new Intent(Intent.ACTION_DIAL);
//                i.setData(Uri.parse("tel:"+ambulances.get(position).amb_phone));
//                startActivity(i);
//            }
//        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText;
        ArrayList<Ambulance_data> newList = new ArrayList<Ambulance_data>();

        for(Ambulance_data amb : ambulances)
        {
            if(amb.amb_dis.toLowerCase().contains(userInput))
            {
                newList.add(amb);
            }
        }
        ambulanceAdapter.updateList(newList);

        return true;
    }
}
