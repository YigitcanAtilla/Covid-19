package com.yigitcan.covid_19.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.yigitcan.covid_19.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView NewConfirmed,TotalConfirmed,NewDeaths,TotalDeaths,NewRecovered,TotalRecovered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewConfirmed=findViewById(R.id.NewConfirmed);
        TotalConfirmed=findViewById(R.id.TotalConfirmed);
        NewDeaths=findViewById(R.id.NewDeaths);
        TotalDeaths=findViewById(R.id.TotalDeaths);
        NewRecovered=findViewById(R.id.NewRecovered);
        TotalRecovered=findViewById(R.id.TotalRecovered);

        getCurrentState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.ulke){
            Intent intent =new Intent(MainActivity.this,Countries.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getCurrentState(){
        JsonParse jsonParse=new JsonParse();
        jsonParse.execute();
    }

    protected class JsonParse extends AsyncTask<Void,Void,Void>{

        int yeniHasta;
        int toplamHasta;
        int yeniOlum;
        int toplamOlum;
        int yeniTedavi;
        int toplamTedavi;

        @Override
        protected Void doInBackground(Void... voids) {

            String result="";


            try {
                URL covid_url=new URL("https://api.covid19api.com/summary");
                BufferedReader bufferedReader=null;
                bufferedReader=new BufferedReader(new InputStreamReader(covid_url.openStream()));
                String line=null;
                while((line=bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();

                JSONObject object=new JSONObject(result);
                JSONObject global=object.getJSONObject("Global");
                yeniHasta=global.getInt("NewConfirmed");
                toplamHasta=global.getInt("TotalConfirmed");
                yeniOlum=global.getInt("NewDeaths");
                toplamOlum=global.getInt("TotalDeaths");
                yeniTedavi=global.getInt("NewRecovered");
                toplamTedavi=global.getInt("TotalRecovered");


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            NewConfirmed.setText(String.valueOf(yeniHasta));
            TotalConfirmed.setText(String.valueOf(toplamHasta));
            NewDeaths.setText(String.valueOf(yeniOlum));
            TotalDeaths.setText(String.valueOf(toplamOlum));
            NewRecovered.setText(String.valueOf(yeniTedavi));
            TotalRecovered.setText(String.valueOf(toplamTedavi));

            super.onPostExecute(aVoid);
        }
    }




}
