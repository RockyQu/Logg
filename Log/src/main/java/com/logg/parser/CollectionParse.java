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

package com.logg.parser;

import com.logg.config.LoggConstant;
import com.logg.util.Utils;

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

    @Override
    public String parseString(Collection collection) {
        String simpleName = collection.getClass().getName();
        String msg = "%s size = %d [" + LoggConstant.BR;
        msg = String.format(msg, simpleName, collection.size());
        if (!collection.isEmpty()) {
            Iterator<Object> iterator = collection.iterator();
            int flag = 0;
            while (iterator.hasNext()) {
                String itemString = "[%d]:%s%s";
                Object item = iterator.next();
                msg += String.format(itemString, flag, Utils.objectToString(item),
                        flag++ < collection.size() - 1 ? "," + LINE_SEPARATOR : LINE_SEPARATOR);
            }
        }
        return msg + "]";
    }
}
