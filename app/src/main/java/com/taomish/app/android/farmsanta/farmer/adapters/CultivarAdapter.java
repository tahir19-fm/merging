package com.taomish.app.android.farmsanta.farmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.models.api.cultivar.Cultivar;

import java.util.ArrayList;

public class CultivarAdapter extends ArrayAdapter<Cultivar> {
    private final String MY_DEBUG_TAG = "CustomerAdapter";
    private final ArrayList<Cultivar> items;
    private final ArrayList<Cultivar> itemsAll;
    private final ArrayList<Cultivar> suggestions;
    private final int viewResourceId;


    public CultivarAdapter(Context context, int viewResourceId, ArrayList<Cultivar> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<Cultivar>) items.clone();
        this.suggestions = new ArrayList<Cultivar>();
        this.viewResourceId = viewResourceId;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        Cultivar cultivar = items.get(position);
        if (cultivar != null) {
            TextView cultivarNameLabel = v.findViewById(R.id.tvCustom);
            if (cultivarNameLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                cultivarNameLabel.setText(cultivar.getCultivarName());
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Cultivar)(resultValue)).getCultivarName();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Cultivar cultivar : itemsAll) {
                    if(cultivar.getCultivarName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        suggestions.add(cultivar);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Cultivar> filteredList = (ArrayList<Cultivar>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Cultivar c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}
