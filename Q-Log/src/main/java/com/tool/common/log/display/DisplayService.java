package com.tool.common.log.display;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ScrollView;

import com.tool.common.log.QLog;
import com.tool.common.log.common.Setting;
import com.tool.common.log.util.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * DisplayService
 */
public class DisplayService extends Service {

    // WindowManager
    private WindowManager windowManager = null;
    // LayoutParams
    private WindowManager.LayoutParams params = null;

    // Message Queue
    private Queue<String> messages = new ArrayDeque<>();

    // Log显示容器
    private DisplayView displayView = null;

    // 是否已显示悬浮窗
    private boolean isShow = false;

    /**
     * 参数配置
     */
    private Setting setting = null;

    @Override
    public IBinder onBind(Intent intent) {
        QLog.e("DisplayService onBind");
        return new DisplayBinder();
    }

    public class DisplayBinder extends Binder {

        public DisplayService getService() {
            return DisplayService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        QLog.e("DisplayService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        QLog.e("DisplayService onCreate");
        setting = Setting.getInstance();

        // WindowManager
        windowManager = (WindowManager) setting.getContext()
                .getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        // 设置LayoutParams相关参数
        params = new WindowManager.LayoutParams();

        params = new WindowManager.LayoutParams(Util.getScreenWidth(setting
                .getContext()),
                Util.getScreenHeight(setting.getContext()) / 2 + 100,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.RGBA_8888);

        params.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角

        displayView = new DisplayView(setting.getContext());

        displayView.setTouchEvent(new DisplayView.TouchEvent() {

            @Override
            public void onTouch(View view, int x, int y) {
                params.x = x;
                params.y = y;
                windowManager.updateViewLayout(view, params);
            }
        });

        displayView.setSwitchChangeListener(new DisplayView.SwitchChangeListener() {

            @Override
            public void changed(CompoundButton buttonView, boolean isChecked, View view) {

                if (isChecked) {
                    displayView.setLogSwitchText("Stop");

                    handler.postDelayed(runnable, 0);
                } else {
                    handler.removeCallbacks(runnable);

                    displayView.setLogSwitchText("Start");
                }
            }
        });

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        QLog.e("DisplayService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 消息处理器
     */
    private Handler handler = new Handler();

    /**
     * 线程定时器
     */
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {

            message(logcat());

            displayView.fullScroll(ScrollView.FOCUS_DOWN);

            handler.postDelayed(this, 1000);
        }
    };

    /**
     * 显示悬浮窗口
     */
    public void start() {
        if (displayView != null && !isShow) {
            windowManager.addView(displayView, params);
            isShow = true;
        }
    }

    /**
     * 获取logcat信息
     */
    private String logcat() {
        Process process = null;
        String logcat = "";
        try {
            ArrayList<String> commandLine = new ArrayList<String>();//logcat -d 获取日志
            commandLine.add("logcat");
            commandLine.add("-d");
            ArrayList<String> clearLog = new ArrayList<String>();//logcat -c 清除日志
            clearLog.add("logcat");
            clearLog.add("-c");

            process = Runtime.getRuntime().exec(commandLine.toArray(new String[commandLine.size()]));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));
                logcat = logcat + line + "\n";
            }

            bufferedReader.close();
        } catch (Exception ex) {
            process.destroy();
        }

        return logcat;
    }

    /**
     * 取消/关闭悬浮窗口
     */
    public void stop() {
        if (displayView != null && isShow) {
            windowManager.removeView(displayView);
            isShow = false;
        }
    }

    /**
     * 设置Message
     */
    public void message(String message) {
        messages.offer(message);
        if (messages.size() > 30) {
            messages.poll();
        }

        Spannable spannable = new SpannableString(TextUtils.join("\n", messages));
        spannable.setSpan(new BackgroundColorSpan(0x0), 0, spannable.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        displayView.setText(spannable);
    }

    @Override
    public void onDestroy() {
        QLog.e("DisplayService onDestroy");

        stop();
        super.onDestroy();
    }
}
