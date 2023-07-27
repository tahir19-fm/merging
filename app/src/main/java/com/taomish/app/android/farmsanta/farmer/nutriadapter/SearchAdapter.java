package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.Seller_Product_Data;

import java.util.List;

import coil.ImageLoader;

public class SearchAdapter extends BaseAdapter {

    private Context mContext;
    private List<Seller_Product_Data> list;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public SearchAdapter(Context mContext, List<Seller_Product_Data> list) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.list = list;
        mRequestQueue = Volley.newRequestQueue(mContext);
       // mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache());
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_search   , parent, false);
        }
        ImageView image = (ImageView)convertView.findViewById(R.id.productimage);
        TextView tvName = (TextView)convertView.findViewById(R.id.productname);

        Seller_Product_Data model = list.get(position);
        image.setImageResource(model.getProductimg());
        tvName.setText(model.getProductname());

        return convertView;
    }
}
