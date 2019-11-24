package com.tonyelkharrat.ui.managers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.snackbar.Snackbar;
import com.tonyelkharrat.R;

public class AnimationManager {

    // Make an animation loading before downloading pictures
    public static void manageLoadingAnimation(SpinKitView loadingView, ProgressBar progressBar) {

        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);

       if(loadingView.getVisibility() == View.INVISIBLE) {
           loadingView.setVisibility(View.VISIBLE);
       }
       else{
           loadingView.setVisibility(View.INVISIBLE);
       }
    }

    public static void showSnackBarMessage(Context context,String message){
        Snackbar snackBar = Snackbar.make(((Activity)context).getWindow().getDecorView().getRootView(),message, Snackbar.LENGTH_LONG);
        snackBar.show();
    }
}
