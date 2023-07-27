package com.taomish.app.android.farmsanta.farmer.nutriadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.datamodel.Rating_Data;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.SingleItemRowHolder> {

    private final ArrayList<Rating_Data> itemsList;
    private final Context mContext;
    private OnItemClickListener mItemClickListener;

    public ReviewAdapter(Context context, ArrayList<Rating_Data> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item, viewGroup, false);
        return new SingleItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        Rating_Data singleItem = itemsList.get(i);
        holder.Name.setText(singleItem.getName());
        holder.ProfileImage.setImageResource(singleItem.getImage());
        holder.message.setText(singleItem.getMessage());
        holder.rating.setText(String.valueOf(singleItem.getRatingnumber()));
        holder.liketext.setText(String.valueOf(singleItem.getLikenumber()));
        holder.disliketext.setText(String.valueOf(singleItem.getDislikenumber()));

        if(singleItem.getuserlike()) {
            holder.like.setImageResource(R.drawable.likedone);
        }
        if(singleItem.getuserdislike()) {
            holder.dislike.setImageResource(R.drawable.dislikedone);
        }
        //holder.liketext.setVisibility(View.GONE);
       // holder.disliketext.setVisibility(View.GONE);
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(singleItem.getuserlike()) {
                    Toast.makeText(mContext, "Remove Like", Toast.LENGTH_SHORT).show();
                    holder.like.setImageResource(R.drawable.like);
                    holder.liketext.setText(String.valueOf(singleItem.getLikenumber()-1));
                }
                else {
                    Toast.makeText(mContext, "Add Like", Toast.LENGTH_SHORT).show();
                    holder.like.setImageResource(R.drawable.likedone);
                    holder.liketext.setText(String.valueOf(singleItem.getLikenumber()+1));
                    if(singleItem.getuserdislike()) {
                        holder.disliketext.setText(String.valueOf(singleItem.getDislikenumber()-1));
                        holder.dislike.setImageResource(R.drawable.dislike);
                    }
                }
            }
        });

        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(singleItem.getuserdislike()) {
                    Toast.makeText(mContext, "Remove DisLike", Toast.LENGTH_SHORT).show();
                    holder.dislike.setImageResource(R.drawable.dislike);
                    holder.disliketext.setText(String.valueOf(singleItem.getDislikenumber()-1));
                }
                else {
                    Toast.makeText(mContext, "Add DisLike", Toast.LENGTH_SHORT).show();
                    holder.dislike.setImageResource(R.drawable.dislikedone);
                    holder.disliketext.setText(String.valueOf(singleItem.getDislikenumber()+1));
                    if(singleItem.getuserlike()) {
                        holder.liketext.setText(String.valueOf(singleItem.getDislikenumber()-1));
                        holder.like.setImageResource(R.drawable.like);
                    }
                }
            }
        });
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
        void onItemClick(View view, int itemPosition, Rating_Data model);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView Name;
        protected ImageView ProfileImage,like,dislike;
        protected TextView rating,message,liketext,disliketext;
        public SingleItemRowHolder(View view) {
            super(view);
            this.Name = view.findViewById(R.id.Name);
            this.ProfileImage = view.findViewById(R.id.ProfileImage);
            this.rating = view.findViewById(R.id.rating);
            this.message = view.findViewById(R.id.message);
            this.liketext= view.findViewById(R.id.liketext);
            this.disliketext= view.findViewById(R.id.disliketext);
            this.like= view.findViewById(R.id.like);
            this.dislike=view.findViewById(R.id.dislike);
           /* view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(), itemsList.get(getAdapterPosition()));
                }
            });
*/

        }

    }

}
