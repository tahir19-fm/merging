package com.taomish.app.android.farmsanta.farmer.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.constants.URLConstants;
import com.taomish.app.android.farmsanta.farmer.interfaces.OnRecyclerItemClickListener;
import com.taomish.app.android.farmsanta.farmer.models.api.message.Message;
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class SocialWallAdapter extends RecyclerView.Adapter<SocialWallAdapter.SocialWallViewHolder> implements Filterable {
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private final List<Message> itemList;
    private final List<Message> itemListFull;

    public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
    public final static long MILLIS_PER_HOUR = 60 * 60 * 1000L;
    public final static long MILLIS_PER_MINUTE = 60 * 1000L;

    private Context context;

    private OnRecyclerItemClickListener onRecyclerItemClickListener;


    public SocialWallAdapter(List<Message> itemList) {
        this.itemList = itemList;
        itemListFull = new ArrayList<>(itemList);
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @NonNull
    @Override
    public SocialWallViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.card_social_wall_post,
                        viewGroup, false);
        return new SocialWallViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SocialWallViewHolder holder, int position) {
        Message message = itemList.get(position);

        String username = (message.getFirstName() != null ?
                message.getFirstName() : "") + " "
                + (message.getLastName() != null ?
                message.getLastName() : "");

        holder.textViewPostTitle.setText(message.getTitle());
        holder.textViewPostContent.setText(message.getDescription());
        holder.textViewUserName.setText(username);

        holder.textViewPostLikes.setText(String.valueOf(message.getLikes() != null ? message.getLikes() : 0));
        if (message.getSelfLike() != null && message.getSelfLike() > 0)
            holder.textViewPostLikes.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_filled_heart, 0, 0, 0);

        if (message.getComments() != null && message.getComments().size() > 0) {
            holder.viewCommentsDivider2.setVisibility(View.VISIBLE);
            holder.viewCommentsDivider1.setVisibility(View.VISIBLE);
        } else {
            holder.viewCommentsDivider2.setVisibility(View.GONE);
            holder.viewCommentsDivider1.setVisibility(View.GONE);
        }
        holder.textViewPostComments.setText(
                String.valueOf(
                        message.getComments() != null ?
                                message.getComments().size() :
                                0));
        if (message.getImages() != null && message.getImages().size() > 0) {
            holder.imageViewPost.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(URLConstants.S3_IMAGE_BASE_URL + message.getImages().get(0).getFileName())
                    .into(holder.imageViewPost);
        } else {
            holder.imageViewPost.setVisibility(View.GONE);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerViewComments.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        layoutManager.setInitialPrefetchItemCount(2);//message.getComments().size()
        layoutManager.setStackFromEnd(true);

        SocialWallCommentAdapter childItemAdapter = new SocialWallCommentAdapter(message.getComments());

        holder.recyclerViewComments.setLayoutManager(layoutManager);
        holder.recyclerViewComments.setAdapter(childItemAdapter);
        holder.recyclerViewComments.addItemDecoration(new DividerItemDecoration(
                holder.recyclerViewComments.getContext(),
                layoutManager.getOrientation()));
        holder.recyclerViewComments.setRecycledViewPool(viewPool);
        holder.itemView.setOnClickListener(v -> onRecyclerItemClickListener.onItemClick(v, "", position));

        // set posted time in hours format
        if (isPostedWithin24hours(message.getCreatedTimestamp())) {
            holder.postedTime.setText(new DateUtil().getMobileFormat(message.getCreatedTimestamp()));
        } else {
            try {
                holder.postedTime.setText(getDifferenceHours(message.getCreatedTimestamp()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Message> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(itemListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Message item : itemListFull) {
                        if (item.getTitle().toLowerCase().contains(filterPattern)
                                || item.getDescription().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemList.clear();
                //noinspection unchecked,rawtypes
                itemList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    private boolean isPostedWithin24hours(String createdTimeStamp) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new DateUtil().getTimeStampFormat(createdTimeStamp);

        try {
            return Math.abs(timestamp.getTime() - date.getTime()) > MILLIS_PER_DAY;
        } catch (Exception e) {
            return false;
        }
    }

    private String getDifferenceHours(String createdTimeStamp) throws ParseException {

        /* Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date utcDate  = new DateUtil().getDateInUTCFormat(createdTimeStamp); */
        Date date = new DateUtil().getTimeStampFormat(createdTimeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.ENGLISH);
        Date dd = localDateFormat.parse(simpleDateFormat.format(new Date()));
        try {
            long timeStamp = (Math.abs((dd != null ? dd.getTime() : 0) - date.getTime())) / MILLIS_PER_HOUR;
            if (timeStamp <= 0) {
                long minutes = (Math.abs((dd != null ? dd.getTime() : 0) - date.getTime())) / MILLIS_PER_MINUTE;
                if (minutes <= 0) {
                    return "just now";
                }
                return minutes + "m";
            }
            return timeStamp + "h";
        } catch (Exception e) {
            e.printStackTrace();
            return "0h";
        }
    }

    static class SocialWallViewHolder
            extends RecyclerView.ViewHolder {

        private final TextView textViewPostTitle;
        private final TextView textViewPostContent;
        private final TextView textViewPostLikes;
        private final TextView textViewPostComments;
        private final TextView textViewUserName;
        private final ImageView imageViewPost;
        private final View viewCommentsDivider1;
        private final View viewCommentsDivider2;
        private final RecyclerView recyclerViewComments;
        private final TextView postedTime;

        SocialWallViewHolder(final View itemView) {
            super(itemView);

            textViewPostTitle = itemView.findViewById(R.id.cardSocialWall_text_title);
            textViewPostContent = itemView.findViewById(R.id.cardSocialWall_text_content);
            textViewPostLikes = itemView.findViewById(R.id.cardSocialWall_text_likes);
            textViewPostComments = itemView.findViewById(R.id.cardSocialWall_text_comments);
            textViewUserName = itemView.findViewById(R.id.cardSocialWall_text_userName);
            imageViewPost = itemView.findViewById(R.id.cardSocialWall_image_post);

            recyclerViewComments = itemView.findViewById(R.id.cardSocialWall_recycler_comments);
            viewCommentsDivider1 = itemView.findViewById(R.id.view_socialWall_comments_divider1);
            viewCommentsDivider2 = itemView.findViewById(R.id.view_socialWall_comments_divider2);

            postedTime = itemView.findViewById(R.id.social_wall_post_date);

        }
    }
}
