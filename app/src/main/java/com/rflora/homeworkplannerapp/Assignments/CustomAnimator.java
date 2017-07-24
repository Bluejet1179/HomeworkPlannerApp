package com.rflora.homeworkplannerapp.Assignments;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by flora on 7/20/2017.
 */

public class CustomAnimator extends DefaultItemAnimator {
    private Context mContext;
    public CustomAnimator(Context context) {
        super();
        mContext = context;
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_out_right);
        animation.setDuration(250);
        holder.itemView.setAnimation(animation);
        holder.itemView.animate();
        return super.animateRemove(holder);
    }
}
