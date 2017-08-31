/*
 * Copyright JiongBull 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.logg.printer;

import android.util.Log;

import com.logg.printer.manager.PrinterManager;

import java.util.List;

/**
 * Default Printer
 */
public class DefaultPrinter implements Printer {

    private List<PrinterManager.LoggListener> listeners = null;

    public DefaultPrinter() {

    }

    public DefaultPrinter(List<PrinterManager.LoggListener> listeners) {
        this.listeners = listeners;
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

        if (listeners != null && listeners.size() != 0) {
            for (PrinterManager.LoggListener listener : listeners) {
                listener.logg(type, tag, object);
            }
        }
    }
}