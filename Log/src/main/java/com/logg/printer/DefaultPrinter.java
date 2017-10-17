package com.logg.printer;

import android.util.Log;

import com.logg.config.LoggConfiguration;
import com.logg.interceptor.callback.GlobalCallback;

/**
 * Default Printer
 */
public class DefaultPrinter implements Printer {

    private LoggConfiguration configuration;

    public DefaultPrinter(LoggConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void printer(Type type, String tag, String object) {
        switch (type) {
            case V:
                Log.v(tag, object);
                break;
            case D:
                Log.d(tag, object);
                break;
            case I:
                Log.i(tag, object);
                break;
            case W:
                Log.w(tag, object);
                break;
            case E:
                Log.e(tag, object);
                break;
            case WTF:
                Log.wtf(tag, object);
                break;
            case J:
                Log.d(tag, object);
                break;
            case X:
                Log.d(tag, object);
                break;
            default:
                break;
        }

        GlobalCallback.getInstance().printerAll(type, tag, object);
    }
}