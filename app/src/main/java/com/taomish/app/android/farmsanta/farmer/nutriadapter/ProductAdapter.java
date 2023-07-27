package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.Product_Data;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Product_Data> modelList;

    private OnItemClickListener mItemClickListener;
    private OnMoreClickListener mMoreClickListener;

    public ProductAdapter(Context context, ArrayList<Product_Data> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<Product_Data> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_services, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        //Here you can fill your row view

        final Product_Data model = getItem(position);


        ArrayList<Product_Data> singleSectionItems = model.getSingleItemArrayList();

        holder.itemTxtTitle.setText(model.getTitle());
        SingleItemProducts itemListDataAdapter = new SingleItemProducts(mContext, singleSectionItems);

        holder.recyclerViewHorizontalList.setHasFixedSize(true);
        holder.recyclerViewHorizontalList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewHorizontalList.setAdapter(itemListDataAdapter);


        holder.recyclerViewHorizontalList.setNestedScrollingEnabled(false);


        itemListDataAdapter.SetOnItemClickListener(new SingleItemProducts.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemPosition, Product_Data model) {

                mItemClickListener.onItemClick(view, position, itemPosition, model);

            }
        });

        holder.itemTxtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMoreClickListener.onMoreClick(view, position, model);


            }
        });



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private Product_Data getItem(int position) {
        return modelList.get(position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void SetOnMoreClickListener(final OnMoreClickListener onMoreClickListener) {
        this.mMoreClickListener = onMoreClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int absolutePosition, int relativePosition, Product_Data model);
    }


    public interface OnMoreClickListener {
        void onMoreClick(View view, int position, Product_Data model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected RecyclerView recyclerViewHorizontalList;
        protected TextView itemTxtMore;
        private TextView itemTxtTitle;



        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.itemTxtTitle = (TextView) itemView.findViewById(R.id.item_txt_title);
            this.recyclerViewHorizontalList = (RecyclerView) itemView.findViewById(R.id.services_horizontal_list);
            this.itemTxtMore = (TextView) itemView.findViewById(R.id.item_txt_more);

        }
    }


}



