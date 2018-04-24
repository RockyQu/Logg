/*
 * Copyright 2016-2018 RockyQu
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

package me.logg.parser;

import android.annotation.SuppressLint;

import me.logg.config.LoggConstant;
import me.logg.util.Utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Collection
 */
public class CollectionParse implements Parser<Collection> {

    @Override
    public Class<Collection> parseClassType() {
        return Collection.class;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String parseString(Collection collection) {
        StringBuilder msg = new StringBuilder();
        msg.append(LoggConstant.SPACE).append(LoggConstant.BR)
                .append("%s size = %d").append(LoggConstant.SPACE).append(LoggConstant.BR).append("[").append(LoggConstant.BR);

        msg = new StringBuilder(String.format(msg.toString(), collection.getClass().getName(), collection.size()));
        if (!collection.isEmpty()) {
            Iterator iterator = collection.iterator();
            int flag = 0;
            while (iterator.hasNext()) {
                String itemString = "[%d]:%s%s";
                Object item = iterator.next();
                msg.append(LoggConstant.SPACE).append(LoggConstant.SPACE).append(LoggConstant.SPACE).append(LoggConstant.SPACE)
                        .append(String.format(itemString, flag, Utils.objectToString(item),
                        flag++ < collection.size() - 1 ? "," + LoggConstant.BR : LoggConstant.BR));
            }
        }
        return msg.append("]").toString();
    }
}
