package com.application.aayush.geeta;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Aayush on 4/29/2017.
 */

public class SecondScreen extends AppCompatActivity {
    TextView heading,content,skip;
    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondscreen);
        heading = (TextView)findViewById(R.id.textView);
        content = (TextView)findViewById(R.id.textView16);
        skip = (TextView)findViewById(R.id.textView24);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
        heading.setTypeface(customFont);
        content.setTypeface(customFont);
        skip.setTypeface(customFont);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    finish();
                    startActivity(new Intent(SecondScreen.this,ThirdScreen.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        gestureDetectorCompat = new GestureDetectorCompat(this,new MyGestureListener());
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.gestureDetectorCompat.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);

    }
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e2.getX()<e1.getX()){
                finish();
                Intent intent = new Intent(SecondScreen.this,ThirdScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
            else if(e1.getX()<e2.getX()){
                finish();
                Intent intent = new Intent(SecondScreen.this,FirstScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
            return true;
        }
    }
}
