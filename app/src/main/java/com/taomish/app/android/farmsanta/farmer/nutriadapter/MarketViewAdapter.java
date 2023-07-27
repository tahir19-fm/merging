package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stx.xmarqueeview.XMarqueeView;
import com.stx.xmarqueeview.XMarqueeViewAdapter;
import com.taomish.app.android.farmsanta.farmer.R;

import java.util.List;

public class MarketViewAdapter extends XMarqueeViewAdapter<String> {

    private Context mContext;
    public MarketViewAdapter(List<String> datas, Context context) {
        super(datas);
        mContext = context;
    }

    @Override
    public View onCreateView(XMarqueeView parent) {

        return LayoutInflater.from(parent.getContext()).inflate(R.layout.marqueeview_item, null);
    }

    @Override
    public void onBindView(View parent, View view, final int position) {
        //布局内容填充
        TextView tvOne = (TextView) view.findViewById(R.id.marquee_tv_one);
        tvOne.setText(mDatas.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "position" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
