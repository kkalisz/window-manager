package pl.kalisz.kamil.windowmanager;

import android.content.Intent;

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
public interface WindowStarter
{
    /**
     * @param requestCode request code for identifying result
     * @param intent intent to start
     */
    void startActivity(String requestCode, Intent intent);

    /**
     * @param requestCode request code for identifying result
     * @param intentHandler listener to handle result
     */
    void register(String requestCode, IntentHandler intentHandler);

    /**
     * @param requestCode request code with intent was started
     * @param resultIntent intent with result data
     * @param resultCode result code from started intent
     * @return true if result was handled by WindowStarter
     */
    boolean handleResult(int requestCode, Intent resultIntent, int resultCode);
}
