package com.yigitcan.covid_19.Adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yigitcan.covid_19.Model.Country;
import com.yigitcan.covid_19.R;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    ArrayList<Country> list;
    ArrayList<Country> listcopy;

    public RecyclerViewAdapter(ArrayList<Country> list) {
        this.list=list;
        listcopy=new ArrayList<>(list);
    }

    @Override
    public Filter getFilter(){
        return countyFilter;
    }
    private Filter countyFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Country> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() ==0){
                filteredList.addAll(listcopy);

            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(Country item : listcopy){
                    if(item.getCountryName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.country_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country=list.get(position);
        holder.CountryName.setText(country.getCountryName());
        holder.Date.setText(country.getDate());
        holder.TotalRecovered.setText(String.valueOf(country.getTotalRecovered()));
        holder.NewRecovered.setText(String.valueOf(country.getNewRecovered()));
        holder.NewDeaths.setText(String.valueOf(country.getNewDeaths()));
        holder.TotalDeaths.setText(String.valueOf(country.getTotalDeaths()));
        holder.NewConfirmed.setText(String.valueOf(country.getNewConfirmed()));
        holder.TotalConfirmed.setText(String.valueOf(country.getTotalConfirmed()));

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView CountryName;
        TextView NewConfirmed;
        TextView TotalConfirmed;
        TextView NewDeaths;
        TextView TotalDeaths;
        TextView NewRecovered;
        TextView TotalRecovered;
        TextView Date;
        TextView CountryNametxt;
        TextView NewConfirmedtxt;
        TextView TotalConfirmedtxt;
        TextView NewDeathstxt;
        TextView TotalDeathstxt;
        TextView NewRecoveredtxt;
        TextView TotalRecoveredtxt;
        TextView Datetxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CountryName=itemView.findViewById(R.id.Country);
            NewConfirmed=itemView.findViewById(R.id.NewConfirmed);
            TotalConfirmed=itemView.findViewById(R.id.TotalConfirmed);
            NewDeaths=itemView.findViewById(R.id.NewDeaths);
            TotalDeaths=itemView.findViewById(R.id.TotalDeaths);
            NewRecovered=itemView.findViewById(R.id.NewRecovered);
            TotalRecovered=itemView.findViewById(R.id.TotalRecovered);
            Date=itemView.findViewById(R.id.Date);
            CountryNametxt=itemView.findViewById(R.id.Countrytxt);
            NewConfirmedtxt=itemView.findViewById(R.id.NewConfirmedtxt);
            TotalConfirmedtxt=itemView.findViewById(R.id.TotalConfirmedtxt);
            TotalDeathstxt=itemView.findViewById(R.id.TotalDeathstxt);
            NewDeathstxt=itemView.findViewById(R.id.NewDeathstxt);
            NewRecoveredtxt=itemView.findViewById(R.id.NewRecoveredtxt);
            TotalRecoveredtxt=itemView.findViewById(R.id.TotalRecoveredtxt);
            Datetxt=itemView.findViewById(R.id.Datetxt);
        }
    }
}
