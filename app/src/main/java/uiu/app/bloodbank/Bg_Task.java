package uiu.app.bloodbank;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Bg_Task {
    Context context ;
    static ArrayList<Ambulance_data> ambulances = new ArrayList<>();
    static ArrayList<Feed_Data> feeds = new ArrayList<>();
    String amburl = "https://bloodlink.mrrobi.tech/api/getAmbulance";
    String feedurl = "https://bloodlink.mrrobi.tech/api/getFeed";

    public Bg_Task(Context context) {
        this.context = context;
    }

    public ArrayList<Ambulance_data> getAmbulancesList(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, amburl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count=0;
                ambulances.clear();
                while(count<response.length()) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(count);
                        ambulances.add(new Ambulance_data(jsonObject.getString("amb_name"), jsonObject.getString("amb_phone"), jsonObject.getString("amb_addr"),jsonObject.getString("amb_pos"),jsonObject.getString("amb_dis")));
                        count++;
                        //System.out.println(people.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return ambulances;
    }
    public ArrayList<Feed_Data> getfeedsList(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, feedurl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count=0;
                feeds.clear();
                while(count<response.length()) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(count);
                        feeds.add(new Feed_Data(
                                jsonObject.getString("id"),
                                jsonObject.getString("b_group"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("amount_blood"),
                                jsonObject.getString("date"),
                                jsonObject.getString("time"),
                                jsonObject.getString("add_hospital"),
                                jsonObject.getString("msg"),
                                jsonObject.getString("user")));
                        count++;
                        //System.out.println(people.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return feeds;
    }
    public String test(){
        return "Test String";
    }
}
