package com.taomish.app.android.farmsanta.farmer.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.models.api.soil.Parameter;
import com.taomish.app.android.farmsanta.farmer.models.api.soil.SoilHealth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SoilHealthCardExpandableAdapter extends BaseExpandableListAdapter {

    private final Context _context;
    private final Map<String, ArrayList<Parameter>> listData; // header titles
    private final String[] groupNames; // header titles

    public SoilHealthCardExpandableAdapter(Context context,
                                           Map<String, ArrayList<Parameter>> soilHealthParameters) {
        this._context = context;
        this.listData = soilHealthParameters;
        this.groupNames = soilHealthParameters.keySet().toArray(new String[0]);
    }

    @Override
    public Parameter getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(this.listData.get(groupNames[groupPosition])).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_soil_health_card_table, null);
        }


        return getTableView(groupPosition, convertView);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public String getGroup(int groupPosition) {
        return this.groupNames[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return this.groupNames.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_soil_health_card, null);
        }

        TextView lblListHeader = convertView
                .findViewById(R.id.soilHealthCard_text_title);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View getTableView(int groupPos, View view) {
        List<Parameter> parameters              = listData.get(groupNames[groupPos]);

        TableLayout tableLayout                 = (TableLayout) view;
        tableLayout.removeAllViewsInLayout();

        tableLayout.addView(tableTitle());

        if (parameters != null) {
            for (Parameter parameter: parameters) {
                tableLayout.addView(tableRow(parameter));
            }
        }

        return tableLayout;
    }

    private TableRow tableTitle() {
        LayoutInflater inflater                 = (LayoutInflater) this._context
                                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                               = inflater.inflate(R.layout.item_soil_health_card_table_title, null);

        TableRow tableRow                       = new TableRow(_context);
        tableRow.addView(view);

        return tableRow;
    }

    private TableRow tableRow(Parameter parameter) {
        LayoutInflater inflater                 = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view                               = inflater.inflate(R.layout.item_soil_health_card_table_item, null);

        TextView paramTextView                  = view.findViewById(R.id.soilHealthCardItem_text_param);
        TextView testValTextView                = view.findViewById(R.id.soilHealthCardItem_text_testVal);
        TextView unitTextView                   = view.findViewById(R.id.soilHealthCardItem_text_unit);
        TextView ratingTextView                 = view.findViewById(R.id.soilHealthCardItem_text_rating);
        TextView normalTextView                 = view.findViewById(R.id.soilHealthCardItem_text_normal);

        paramTextView.setText(parameter.getParameter());
        testValTextView.setText(String.valueOf(parameter.getValue()));
        unitTextView.setText(parameter.getUnit());
        ratingTextView.setText(parameter.getRating());
        normalTextView.setText((parameter.getNormalValue().getMin() +" - "+ parameter.getNormalValue().getMax()));

        TableRow tableRow                       = new TableRow(_context);
        tableRow.addView(view);

        return tableRow;
    }
}