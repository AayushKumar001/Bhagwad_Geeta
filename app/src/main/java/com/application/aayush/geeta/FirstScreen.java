package com.application.aayush.geeta;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Aayush on 4/29/2017.
 */

public class FirstScreen extends AppCompatActivity {
    TextView heading,content,skip;
    private GestureDetectorCompat gestureDetectorCompat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//remove notification bar
        setContentView(R.layout.activity_firstscreen);
        heading = (TextView)findViewById(R.id.textView);
        content = (TextView)findViewById(R.id.textView16);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
        heading.setTypeface(customFont);
        content.setTypeface(customFont);
        skip = (TextView) findViewById(R.id.textView24);
        skip.setTypeface(customFont);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    finish();
                    startActivity(new Intent(FirstScreen.this,SecondScreen.class));
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
                Intent intent = new Intent(FirstScreen.this,SecondScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
            return true;
        }
    }

}