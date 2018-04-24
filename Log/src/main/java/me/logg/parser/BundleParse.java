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

import android.os.Bundle;

import me.logg.config.LoggConstant;
import me.logg.util.Utils;

/**
 * Bundle
 */
public class BundleParse implements Parser<Bundle> {

    @Override
    public Class<Bundle> parseClassType() {
        return Bundle.class;
    }

    @Override
    public String parseString(Bundle bundle) {
        if (bundle != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(bundle.getClass().getName()).append(" [");

            for (String key : bundle.keySet()) {
                builder.append(String.format("'%s' => %s ", key, Utils.objectToString(bundle.get(key))));
            }

            return builder.append("]").toString();
        }
        return null;
    }
}
