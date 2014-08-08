package com.west.todolist;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

/**
 * Created by usr1 on 7/25/14.
 */
public class MyTextV extends TextView {

    public MyTextV(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public MyTextV(Context context){
        super(context);
    }

    public MyTextV(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void onDraw(Canvas canvas){

    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){


        return super.onKeyDown(keyCode, keyEvent);
    }
}
