/*
 * Copyright 2016-2018 DesignQu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.logg.printer;

import android.util.Log;

import com.logg.config.LoggConfiguration;
import com.logg.interceptor.Interceptor;
import com.logg.interceptor.LoggInterceptor;
import com.logg.interceptor.LoggStructure;
import com.logg.interceptor.callback.GlobalCallback;

import java.util.List;

/**
 * Default Printer
 */
public class DefaultPrinter implements Printer {

    /**
     * LoggInterceptors
     */
    private List<LoggInterceptor> interceptors;

    public DefaultPrinter(LoggConfiguration configuration) {
        interceptors = configuration.getInterceptors();
    }

    @Override
    public void printer(Type type, String tag, String object) {
        LoggStructure item = new LoggStructure(type, tag, object, null);
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                if (interceptor.isLoggable(type)) {
                    item = interceptor.intercept(item);
                    if (item == null) {
                        throw new NullPointerException();
                    }

                    if (item.getTag() == null) {
                        throw new NullPointerException();
                    }

                    if (item.getObject() == null) {
                        throw new NullPointerException();
                    }
                }
            }
        }

        switch (item.getType()) {
            case V:
                Log.v(item.getTag(), item.getObject());
                break;
            case D:
                Log.d(item.getTag(), item.getObject());
                break;
            case I:
                Log.i(item.getTag(), item.getObject());
                break;
            case W:
                Log.w(item.getTag(), item.getObject());
                break;
            case E:
                Log.e(item.getTag(), item.getObject());
                break;
            case WTF:
                Log.wtf(item.getTag(), item.getObject());
                break;
            case J:
                Log.d(item.getTag(), item.getObject());
                break;
            case X:
                Log.d(item.getTag(), item.getObject());
                break;
            default:
                break;
        }

        GlobalCallback.getInstance().printerAll(item.getType(), item.getTag(), item.getObject());
    }

    public void addInterceptor(LoggInterceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
    }

    public void removeInterceptor(LoggInterceptor interceptor) {
        if (interceptor != null) {
            interceptors.remove(interceptor);
        }
    }

    public void clearInterceptors() {
        interceptors.clear();
    }
}