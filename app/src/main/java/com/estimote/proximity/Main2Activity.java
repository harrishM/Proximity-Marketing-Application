package com.estimote.proximity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ListView lv = findViewById(R.id.lv1);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

postit();
      //  navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



    public void postit() {


        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(8000);
        RequestParams params = new RequestParams();


        client.get(Main2Activity.this, "http://ec2-54-225-39-169.compute-1.amazonaws.com/recommendations?cid=BBID_211417787",

                params, new AsyncHttpResponseHandler() {

                    public void onStart() {

                        super.onStart();

                    }

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                        try {
                            String s = new String(responseBody);

                            JSONParser jsonParser = new JSONParser();
                            Object object;


                            object = jsonParser.parse(s);
                            JSONObject jsonObject = (JSONObject) object;
                            ArrayList<String> per = new ArrayList<String>();
                            ArrayList<String> tot = new ArrayList<String>();
                            JSONObject res = (JSONObject) jsonObject.get("res");
                            JSONObject personal = (JSONObject) res.get("personal");
                            JSONObject total = (JSONObject) res.get("total");

                            for (int i = 0; i < personal.size(); i++) {
                                per.add(String.valueOf(personal.get(String.valueOf(i))));
                            }

                            for (int i = 0; i < total.size(); i++) {
                                tot.add(String.valueOf(total.get(String.valueOf(i))));
                            }
 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1, per);
                            lv.setAdapter(arrayAdapter);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }



                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                    }


                });
    }



    }
