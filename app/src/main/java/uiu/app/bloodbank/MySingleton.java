package uiu.app.bloodbank;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Array;
import java.util.ArrayList;

public class MySingleton {
    public static MySingleton mInstance;
    private RequestQueue requestQueue;
    public static Context mCtx;

    public MySingleton(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context){
        if(mInstance==null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }





    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }

}
