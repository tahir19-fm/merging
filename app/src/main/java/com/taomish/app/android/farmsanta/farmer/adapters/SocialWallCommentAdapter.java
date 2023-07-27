package com.taomish.app.android.farmsanta.farmer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taomish.app.android.farmsanta.farmer.R;
import com.taomish.app.android.farmsanta.farmer.models.api.message.Comment;
import com.taomish.app.android.farmsanta.farmer.utils.DateUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class SocialWallCommentAdapter
        extends RecyclerView
        .Adapter<SocialWallCommentAdapter.ChildViewHolder> {

    private final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
    private final static long MILLIS_PER_HOUR = 60 * 60 * 1000L;
    private final static long MILLIS_PER_MINUTE = 60 * 1000L;

    private List<Comment> commentList;

    SocialWallCommentAdapter(List<Comment> childItemList) {
        this.commentList = childItemList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_social_post_comment,
                        viewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {
        Comment childItem = commentList.get(position);

        childViewHolder.textViewComment.setText(childItem.getComment());
        childViewHolder.textViewUserName.setText(childItem.getFirstName() != null ? childItem.getFirstName() : "");


        if (isPostedWithin24hours(childItem.getCreatedTimestamp())) {
            childViewHolder.textViewCommentTime.setText(new DateUtil().getDateMonthYearFormat(childItem.getCreatedTimestamp()));
        } else {
            try {
                childViewHolder.textViewCommentTime.setText(getDifferenceHours(childItem.getCreatedTimestamp()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return commentList != null ? commentList.size() : 0;
    }

    private boolean isPostedWithin24hours(String createdTimeStamp) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new DateUtil().getTimeStampFormat(createdTimeStamp);
        try {
            return Math.abs(timestamp.getTime() - date.getTime()) > SocialWallAdapter.MILLIS_PER_DAY;
        } catch (Exception e) {
            return false;
        }
    }

    private String getDifferenceHours(String createdTimeStamp) throws ParseException {

        /*Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date utcDate  = new DateUtil().getDateInUTCFormat(createdTimeStamp);*/
        Date date = new DateUtil().getTimeStampFormat(createdTimeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.getDefault());
        Date dd = localDateFormat.parse(simpleDateFormat.format(new Date()));
        try {
            long timeStamp = 0;
            if (dd != null) {
                timeStamp = (Math.abs(dd.getTime() - date.getTime())) / MILLIS_PER_HOUR;
            }
            if (timeStamp == 0) {
                long minutes = 0;
                if (dd != null) {
                    minutes = (Math.abs(dd.getTime() - date.getTime())) / MILLIS_PER_MINUTE;
                }
                if (minutes == 0) {
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

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView textViewComment;
        TextView textViewUserName;
        TextView textViewCommentTime;

        ChildViewHolder(View itemView) {
            super(itemView);
            textViewComment = itemView.findViewById(R.id.cardSocialWallComment_text_comment);
            textViewUserName = itemView.findViewById(R.id.cardSocialWallComment_text_user);
            textViewCommentTime = itemView.findViewById(R.id.cardSocialWallComment_text_time);
        }
    }
}
