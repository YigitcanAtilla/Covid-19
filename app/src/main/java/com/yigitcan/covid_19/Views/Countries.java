package com.yigitcan.covid_19.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.yigitcan.covid_19.Adapter.RecyclerViewAdapter;
import com.yigitcan.covid_19.Model.Country;
import com.yigitcan.covid_19.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Countries extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView;
    ArrayList<Country> liste;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        recyclerView = findViewById(R.id.recyclerView);
        searchView=findViewById(R.id.search_country);


        liste = new ArrayList<>();

        getCountriesData();
        getSearchView();
    }


    public SearchView getSearchView() {
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recyclerViewAdapter.getFilter().filter(s);
                return false;
            }
        });
        return searchView;
    }

    public void getCountriesData() {
        JsonParser jsonParser=new JsonParser();
        jsonParser.execute();
    }

    public class JsonParser extends AsyncTask<Void, Void, Void> {

        String Country;
        int yeniHasta;
        int toplamHasta;
        int yeniOlum;
        int toplamOlum;
        int yeniTedavi;
        int toplamTedavi;
        String date;

        @Override
        protected Void doInBackground(Void... voids) {
            String result = "";
            try {
                URL covid_url = new URL("https://api.covid19api.com/summary");
                BufferedReader bufferedReader = null;
                bufferedReader = new BufferedReader(new InputStreamReader(covid_url.openStream()));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();

                JSONObject object = new JSONObject(result);
                JSONArray array_countries=object.getJSONArray("Countries");
                for (int i =0; i<array_countries.length();i++) {
                    JSONObject country = array_countries.getJSONObject(i);
                    Country = country.getString("Country");
                    yeniHasta = country.getInt("NewConfirmed");
                    toplamHasta = country.getInt("TotalConfirmed");
                    yeniOlum = country.getInt("NewDeaths");
                    toplamOlum = country.getInt("TotalDeaths");
                    yeniTedavi = country.getInt("NewRecovered");
                    toplamTedavi = country.getInt("TotalRecovered");
                    date = country.getString("Date");

                    com.yigitcan.covid_19.Model.Country cx = new Country();
                    cx.setCountryName(Country);
                    cx.setDate(date);
                    cx.setNewConfirmed(yeniHasta);
                    cx.setTotalConfirmed(toplamHasta);
                    cx.setNewDeaths(yeniOlum);
                    cx.setTotalDeaths(toplamOlum);
                    cx.setNewRecovered(yeniTedavi);
                    cx.setTotalRecovered(toplamTedavi);
                    liste.add(cx);

                }

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            recyclerView.setLayoutManager(new LinearLayoutManager(Countries.this));
            recyclerViewAdapter= new RecyclerViewAdapter(liste);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.notifyDataSetChanged();
            super.onPostExecute(aVoid);

        }
    }
}
