package pl.kalisz.kamil.windowmanager;

import android.content.Intent;
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

/**
 * Class for handling and cacheing results from activities
 */
//TODO add saving pending results on screen rotation
public class ResultHandler implements IntentHandler
{
    private Logger logger = LoggerFactory.getLogger(ResultHandler.class);

    public class PendingResult
    {
        public PendingResult(int resultCode, Intent data) {
            this.resultCode = resultCode;
            this.data = data;
        }

        int resultCode;

        Intent data;
    }

    private Map<String, IntentHandler> registeredHandlers = new HashMap<>();

    private HashMap<String, PendingResult> pendingResults = new HashMap<>();

    //private Map<String,PendingResult> cachedResults = new HashMap<>();

    @Override
    public void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode) {
        if(logger.isDebugEnabled())
        {
            logger.debug("handling result for requestCode: {} with data: {} and resultCode: {}",requestCode,intent,resultCode);
        }
        if (registeredHandlers.containsKey(requestCode))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("returned result for: {} to : %s",requestCode,registeredHandlers.get(requestCode));
            }
            registeredHandlers.get(requestCode).onActivityResult(requestCode, intent, resultCode);
        } else
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("cached result for requestCode: {}",requestCode);
            }
            pendingResults.put(requestCode,new PendingResult(resultCode,intent));
        }
    }

    /**
     *
     * @param requestCode request code for identifying result for handler
     * @param intentHandler intent handler that will handle result for this {@code requestCode}
     */
    public void registerIntentHandler(String requestCode, IntentHandler intentHandler) {
        registeredHandlers.put(requestCode, intentHandler);
        if(logger.isDebugEnabled())
        {
            logger.debug("registered handler for requestCode: {} with: {}",requestCode, intentHandler);
        }
        if(pendingResults.containsKey(requestCode))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("returned result for: {} to : %s",requestCode,intentHandler);
            }
            PendingResult pendingResult = pendingResults.remove(requestCode);
            intentHandler.onActivityResult(requestCode,pendingResult.data,pendingResult.resultCode);
        }
    }
}
