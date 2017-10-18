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

package com.logg.interceptor;

import com.logg.printer.Type;

/**
 * 这是一个 Logg 拦截器，思想类似 Okhttp 的 Interceptor
 * 当你添加一个{@link Interceptor}它会在最终打印Log前进行拦截处理，你可以在{@link #intercept(LoggStructure)}方法里进行日志的重构处理
 * 添加一个新的 Interceptor 请继承 {@link LoggInterceptor} 根据需求重写相关方法
 */
public class LoggInterceptor implements Interceptor {

    /**
     * Logg 输出开关控件，可以根据{@link Type#getValue()}类型来过滤不同级别的日志信息
     *
     * @param type 类型
     */
    @Override
    public boolean isLoggable(Type type) {
        return true;
    }

    /**
     * {@link LoggStructure}包含Logg相关的所有信息
     *
     * @param structure
     * @see LoggStructure
     */
    @Override
    public LoggStructure intercept(LoggStructure structure) {
        // 如果不做任何处理返回 structure
        return structure;
    }
}