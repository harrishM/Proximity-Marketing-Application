package com.estimote.proximity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.entity.mime.Header;

public class list  extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        postit();


    }


    public void postit() {


        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(8000);
        RequestParams params = new RequestParams();
        //params.put("email", semail);

        client.get(list.this, "http://ec2-54-225-39-169.compute-1.amazonaws.com/recommendations?cid=BBID_211417787",

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

                            JSONObject res = (JSONObject) jsonObject.get("res");
                            JSONObject personal = (JSONObject) res.get("personal");
                            JSONObject total = (JSONObject) res.get("total");
                            ArrayList<String> per = new ArrayList<String>();
                            ArrayList<String> tot = new ArrayList<String>();

                            for (int i = 0; i < 9; i++) {

                                String q=String.valueOf(personal.get(String.valueOf(i)));
                                q=q.substring(q.lastIndexOf("-") + 1);
                                per.add(q);
                            }

                            for(int i=0; i<total.size(); i++) {
                                String q=String.valueOf(personal.get(String.valueOf(i)));
                                q=q.substring(q.lastIndexOf("-") + 1);
                                tot.add(q);                            }


                            ListView lv = findViewById(R.id.l1);
                            ListView lv1 = findViewById(R.id.l2);


                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(list.this, android.R.layout.simple_list_item_1, per);
                            lv.setAdapter(arrayAdapter);

                            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(list.this, android.R.layout.simple_list_item_1, tot);
                            lv1.setAdapter(arrayAdapter1);
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
