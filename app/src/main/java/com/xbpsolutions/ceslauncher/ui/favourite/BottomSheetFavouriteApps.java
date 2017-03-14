package com.xbpsolutions.ceslauncher.ui.favourite;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xbpsolutions.ceslauncher.R;

/**
 * Created by xitij on 11-03-2017.
 */

public class BottomSheetFavouriteApps extends LinearLayout {
    Context context;
    LayoutInflater inflater;
    private FrameLayout favStrip;

    public void setOnFavouriteButtonClickListner(OnFavouriteButtonClickListner onFavouriteButtonClickListner) {
        this.onFavouriteButtonClickListner = onFavouriteButtonClickListner;
    }

    private OnFavouriteButtonClickListner onFavouriteButtonClickListner;

    public BottomSheetFavouriteApps(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BottomSheetFavouriteApps(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.bottom_homefav, this);

        favStrip = (FrameLayout) this.findViewById(R.id.favStrip);
        favStrip.setOnDragListener(new OnFavouriteHoverListner());
        favStrip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onFavouriteButtonClickListner.onClick();
            }
        });

    }

    public interface OnFavouriteButtonClickListner {
        void onClick();
    }

    class OnFavouriteHoverListner implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    doAnimation();

                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:


                    favStrip.setBackgroundResource(R.drawable.bgcardgreen);

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    favStrip.setBackgroundResource(R.drawable.bgcard);

                    break;
                case DragEvent.ACTION_DROP:

                    break;
                case DragEvent.ACTION_DRAG_ENDED:


                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private void doAnimation() {
        final Animation animShake = AnimationUtils.loadAnimation(context, R.anim.shake);

        findViewById(R.id.imgFav).startAnimation(animShake);
    }


}
