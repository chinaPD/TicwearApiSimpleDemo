package com.mobvoi.ticwear.apisimpledemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by pd on 16-4-22.
 */
public class NaonaoActivity extends Activity{

    private ProgressBar progressBar = null;
    private TextView textView = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naonao);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setScaleX(70);
        progressBar.setProgress(50);

        textView    = (TextView) findViewById(R.id.operate_style);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

    }

    public boolean onLongPressSidePanel(MotionEvent e){
        textView.append("long press!!!\n");
        return true;
    }

    // 滑动事件
    public boolean onScrollSidePanel(MotionEvent e1, MotionEvent e2, float distanceX,
                                     float distanceY) {  // distanceY: Y轴方向的移动距离，正负数表示方向
        textView.append("one" + distanceX + "\n");
        return true;
    }

    // 快速滑动后抬起
    public boolean onFlingSidePanel(MotionEvent e1, MotionEvent e2, float velocityX,
                                    float velocityY) {  // velocityY: Y轴方向的加速度，正负数表示方向，方向与distanceY相反
        textView.append("one" + velocityX + "\n");
        return true;
    }

    // 双击事件
    public boolean onDoubleTapSidePanel(MotionEvent e) {  // 参数暂时不需要关注
        textView.append("double tap!!!\n");
        return true;
    }

    // 单击事件
    public boolean onSingleTapSidePanel(MotionEvent e) {  // 参数暂时不需要关注
        textView.append("single tap!!!\n");
        return true;
    }
}
