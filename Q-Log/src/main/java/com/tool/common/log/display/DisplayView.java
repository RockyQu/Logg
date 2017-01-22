package com.tool.common.log.display;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tool.common.log.R;

/**
 * 自定义滚动显示TextView
 */
public class DisplayView extends LinearLayout {

    /**
     * Context
     */
    private Context context = null;

    /**
     * View
     */
    private View view = null;

    //开关
    private CheckBox logSwitch = null;
    //ScrollView
    private ScrollView sclLog = null;
    //Log容器
    private TextView txvLog = null;

    //View的坐标，原点为左上角
    private float touchStartX, touchStartY;
    //屏幕的坐标，原点为屏幕左上角
    private float x, y;

    public DisplayView(Context context) {
        super(context);
        this.context = context;

        view = LayoutInflater.from(context).inflate(R.layout.view_display, this, true);

        init();
    }

    public DisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        view = LayoutInflater.from(context).inflate(R.layout.view_display, this, true);

        init();
    }

    public DisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        view = LayoutInflater.from(context).inflate(R.layout.view_display, this, true);

        init();
    }

    /**
     * 设置Message
     */
    public void setText(CharSequence text) {
        txvLog.setText(text);
    }

    /**
     * 设置开关文字
     */
    public void setLogSwitchText(CharSequence text) {
        logSwitch.setText(text);
    }

    /**
     * 设置ScrollView滚动到最底部
     */
    public void fullScroll(int direction) {
        sclLog.fullScroll(direction);
    }

    /**
     * 初始化
     */
    private void init() {
        logSwitch = (CheckBox) view.findViewById(R.id.log_switch);

        logSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (getSwitchChangeListener() != null) {
                    getSwitchChangeListener().changed(buttonView, isChecked, txvLog);
                }
            }
        });

        sclLog = (ScrollView) view.findViewById(R.id.scl_log);
        txvLog = (TextView) view.findViewById(R.id.txv_log);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getRawX();
        y = event.getRawY() - 25;  // 25是系统状态栏的高度
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStartX = event.getX();
                touchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                this.updateViewPosition(x, y, touchStartX, touchStartY);
                break;
            case MotionEvent.ACTION_UP:
                this.updateViewPosition(x, y, touchStartX, touchStartY);
                touchStartX = touchStartY = 0;
                break;
        }
        return true;
    }

    /**
     * 更新View屏幕位置
     */
    private void updateViewPosition(float x, float y, float touchStartX, float touchStartY) {
        if (getTouchEvent() != null) {
            getTouchEvent().onTouch(this, (int) (x - touchStartX), (int) (y - touchStartY));
        }
    }

    /**
     * 触摸移动事件监听
     */
    public interface TouchEvent {

        void onTouch(View view, int x, int y);
    }

    private TouchEvent touchEvent = null;

    public TouchEvent getTouchEvent() {
        return touchEvent;
    }

    public void setTouchEvent(TouchEvent touchEvent) {
        this.touchEvent = touchEvent;
    }

    /**
     * LogCat开关事件监听
     */
    public interface SwitchChangeListener {

        void changed(CompoundButton buttonView, boolean isChecked, View view);
    }

    private SwitchChangeListener switchChangeListener = null;

    public SwitchChangeListener getSwitchChangeListener() {
        return switchChangeListener;
    }

    public void setSwitchChangeListener(SwitchChangeListener switchChangeListener) {
        this.switchChangeListener = switchChangeListener;
    }
}
