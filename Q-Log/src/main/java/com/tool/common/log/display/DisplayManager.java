package com.tool.common.log.display;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 屏幕显示控制类
 */
public class DisplayManager {

    //DisplayService
    private DisplayService service = null;

    public DisplayManager() {
        ;
    }

    /**
     * 类级的内部类，也就是静态类的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class DisplayHolder {

        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static DisplayManager display = new DisplayManager();
    }

    /**
     * 获取单例
     *
     * @return DisplayManager
     */
    public static DisplayManager getInstance() {
        return DisplayManager.DisplayHolder.display;
    }

    /**
     * 显示悬浮窗口
     */
    public void start(Context context) {
        context.getApplicationContext().bindService(new Intent(context, DisplayService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 取消/关闭悬浮窗口
     */
    public void stop(Context context) {
        context.getApplicationContext().unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder ibinder) {
            service = ((DisplayService.DisplayBinder) ibinder).getService();
            service.start();
        }
    };

    public DisplayService getService() {
        return service;
    }
}
