package pl.kalisz.kamil.windowmanager;

import android.content.Intent;
import android.support.annotation.NonNull;

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
public class WindowStarterImpl implements WindowStarter {
    private ActivityStarter activityStarter;

    private RequestCodeGenerator requestCodeGenerator;

    private ResultHandler resultHandler;

    public WindowStarterImpl(@NonNull ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
        requestCodeGenerator = new RequestCodeGenerator();
        resultHandler = new ResultHandler();
    }

    @Override
    public void startActivity(String requestCode, Intent intent) {
        int requestCodeForIntent = requestCodeGenerator.generate(requestCode);
        activityStarter.startActivity(requestCodeForIntent,intent);
    }

    @Override
    public void register(String requestCode, IntentHandler intentHandler) {
        resultHandler.registerIntentHandler(requestCode,intentHandler);
    }

    @Override
    public boolean handleResult(int requestCode, Intent resultIntent, int resultCode){
        String rawRequestCode = requestCodeGenerator.getRawRequestCode(requestCode);
        //if raw request code is null that means, intent was not started from this Window starter
        // and we should not try to handler result
        if(rawRequestCode != null) {
            resultHandler.onActivityResult(rawRequestCode, resultIntent, resultCode);
        }
        return rawRequestCode != null;
    }


}
