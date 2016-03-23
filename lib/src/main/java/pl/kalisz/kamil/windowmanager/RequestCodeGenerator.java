package pl.kalisz.kamil.windowmanager;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Copyright (C) 2016 Kamil Kalisz.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//TODO add saving and restoring state after rotation
public class RequestCodeGenerator {
    private Logger logger = LoggerFactory.getLogger(RequestCodeGenerator.class);

    private HashMap<String, Integer> generatedCodes = new HashMap<>();

    public int generate(@NonNull String requestCode) {
        if (!generatedCodes.containsKey(requestCode)) {
            generatedCodes.put(requestCode, generatedCodes.size());

            if (logger.isDebugEnabled()) {
                logger.debug("Generated int request code for: {} with value: {}"
                        , requestCode, generatedCodes.get(requestCode));
            }
        }
        return generatedCodes.get(requestCode);
    }
}
