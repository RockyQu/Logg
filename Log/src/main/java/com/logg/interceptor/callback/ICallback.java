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

package com.logg.interceptor.callback;

import com.logg.printer.Type;

public interface ICallback {

    /**
     * 添加一个全局回调
     */
    void addCallback(LoggCallback callback);

    /**
     * 移除一个全局回调
     */
    void removeCallback(LoggCallback callback);

    /**
     * 清空全部
     */
    void clearCallbacks(boolean clear);

    /**
     * 遍历全局回调集合，并将Log信息回调至{@link LoggCallback#logg(Type, String, String)}方法
     */
    void printerAll(Type type, String tag, String object);
}