package com.example.httpreturnstringusage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new readjson().execute("https://atm201605.appspot.com/h");
            }
        });
    }

    private class readjson extends AsyncTask<String, Integer, String>{

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("loading...");
            pd.setCancelable(false);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            for (String urlstring: params){
                try {
                    URL url = new URL(urlstring);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    BufferedReader data = new BufferedReader(new InputStreamReader(is));
                    String line = data.readLine();
                    StringBuffer json = new StringBuffer();
                    while (line!=null){
                        json.append(line);
                        line = data.readLine();
                    }
                    Thread.sleep(500);
                    return json.toString();
                }catch (Exception e){
                    Log.e("TAG", "wtf");
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
            pd.dismiss();
        }
    }




}