package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.Quick_Links;

import java.util.ArrayList;

public class QuickLinkAdapter extends RecyclerView.Adapter<QuickLinkAdapter.ViewHolder> {

    ArrayList<Quick_Links> mValues;
    Context mContext;
    public ItemListener mListener;

    public QuickLinkAdapter(Context context, ArrayList<Quick_Links> values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    public QuickLinkAdapter(FragmentActivity activity, ArrayList arrayList) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ImageView imageView;
        //  public RelativeLayout relativeLayout;
        Quick_Links item;

        public ViewHolder(View v){

            super(v);

            v.setOnClickListener(this);
            textView = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);
            // relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);

        }

        public void setData(Quick_Links item) {
            this.item = item;
            // relativeLayout.setBackgroundColor(Color.parseColor(item.color));
            textView.setText(item.title);
            imageView.setImageDrawable(mContext.getResources().getDrawable(item.image));
           // Picasso.get().load(item.drawable1).into(imageView);
        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.quicklinkitem_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(Quick_Links item);
    }
}
