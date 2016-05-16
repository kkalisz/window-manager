package pl.kalisz.kamil.windowmanager;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) 2016 Kamil Kalisz.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class RequestCodeGenerator implements StateSaver{
    private static final int MINIMAL_REQUEST_CODE = 1;
    private static final String STATE = "RequestCodeGenerator_STATE";
    private Logger logger = LoggerFactory.getLogger(RequestCodeGenerator.class);

    private HashMap<String, Integer> generatedCodes = new HashMap<>();

    public int generate(@NonNull String requestCode) {
        if (!generatedCodes.containsKey(requestCode)) {
            // well if we wana get result from activity request code must be bigger than 0
            generatedCodes.put(requestCode, generatedCodes.size()+ MINIMAL_REQUEST_CODE);

            if (logger.isDebugEnabled()) {
                logger.debug("Generated int request code for: {} with value: {}"
                        , requestCode, generatedCodes.get(requestCode));
            }
        }
        return generatedCodes.get(requestCode);
    }

    public String getRawRequestCode(int requestCode) {
        for (Map.Entry<String, Integer> entry : generatedCodes.entrySet()) {
            if (entry.getValue() == requestCode) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedState) {
        savedState.putSerializable(STATE,generatedCodes);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle state) {
        HashMap<String,Integer> savedCodes = (HashMap<String, Integer>) state.getSerializable(STATE);
        generatedCodes.putAll(savedCodes);
    }
}
