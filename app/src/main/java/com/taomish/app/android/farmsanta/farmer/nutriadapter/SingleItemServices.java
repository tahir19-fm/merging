package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.Service_Data;

import java.util.ArrayList;

public class SingleItemServices extends RecyclerView.Adapter<SingleItemServices.SingleItemRowHolder> {

    private final ArrayList<Service_Data> itemsList;
    private final Context mContext;
    private OnItemClickListener mItemClickListener;

    public SingleItemServices(Context context, ArrayList<Service_Data> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_services, viewGroup, false);

        return new SingleItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        Service_Data singleItem = itemsList.get(i);

        holder.itemCardTxtTitle.setText(singleItem.getTitle());
        holder.itemCardImg.setImageResource(singleItem.getImage());

       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int itemPosition, Service_Data model);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemCardTxtTitle;

        protected ImageView itemCardImg;


        public SingleItemRowHolder(View view) {
            super(view);

            this.itemCardTxtTitle = view.findViewById(R.id.item_card_txt_title);
            this.itemCardImg = view.findViewById(R.id.item_card_img);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(), itemsList.get(getAdapterPosition()));
                }
            });


        }

    }

}
