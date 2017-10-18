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

package com.logg.printer.manager;

import com.logg.config.LoggConfiguration;
import com.logg.interceptor.LoggInterceptor;

public interface IPrinterManager {

    void init();

    void init(LoggConfiguration configuration);

    void v(Object object);

    void v(String tag, Object object);

    void d(Object object);

    void d(String tag, Object object);

    void i(Object object);

    void i(String tag, Object object);

    void w(Object object);

    void w(String tag, Object object);

    void e(Object object);

    void e(String tag, Object object);

    void wtf(Object object);

    void wtf(String tag, Object object);

    void json(Object object);

    void json(String tag, Object object);

    void xml(Object object);

    void xml(String tag, Object object);

    void addInterceptor(LoggInterceptor interceptor);

    void removeInterceptor(LoggInterceptor interceptor);

    void clearInterceptors();

    LoggConfiguration getConfiguration();
}