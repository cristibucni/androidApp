package com.exam.AndroidApp.animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.exam.AndroidApp.loginsignup.R;


public class AnimatorActivity extends AppCompatActivity {
    Button AnimateBtn;
    View RedView;
    ObjectAnimator objectAnimator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animator_activity);
        AnimateBtn = (Button)findViewById(R.id.animateBtn);
        RedView = (View)findViewById(R.id.redView);
        objectAnimator = ObjectAnimator.ofFloat(RedView, View.TRANSLATION_Y, RedView.getTranslationY(), RedView.getTranslationY() + 100f);
        AnimateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectAnimator.setDuration(1000);
                objectAnimator.start();
            }
        });
    }
}