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

/**
 * 提供全局回调接口，如果你设置了此接口，在不影响底层输出的情况下，所有的Log都会回调到这个接口里
 * 你可以在{@link LoggCallback#logg(Type, String, String)} 方法里保存你需要的Log信息至文件中
 * 全局回调内部维护的是一个集合，注意控制集合的数量，避免存放过多接口而导致性能上的问题
 * 注意：请不要在 {@link LoggCallback#logg(Type, String, String)} 方法里再次调用 Logg 会造成死循环
 * 执行{@link LoggCallback#logg(Type, String, String)} 方法是在Log信息打印周期结束后执行
 */
public interface LoggCallback {

    /**
     * @param type    日志类型
     * @param tag     前缀
     * @param message 日志内容
     */
    void logg(Type type, String tag, String message);
}