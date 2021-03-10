package com.example.mireloj;

import androidx.appcompat.app.AppCompatActivity;


import android.util.Log;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    TextView txtview;
    int count;

    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    private static final String TAG = "MyActivity"; // Variable Global


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request = Volley.newRequestQueue(this);

        txtview = (TextView) findViewById(R.id.txtview_reloj);

        Thread t = new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){
                    try {

                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count++;
                                txtview.setText(String.valueOf(count));
                                if(count%4==0){
                                    Cargarwebservice();

                                }

                            }
                        });

                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();

    }

    private void Cargarwebservice(){
        String url="http://10.171.65.64:8080/";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i(TAG, "Hubo error de conexion" );
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.i(TAG, "Que bien nos respondio" );
    }
}