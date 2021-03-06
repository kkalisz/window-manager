package pl.kalisz.kamil.windowmanager;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Copyright (C) 2016 Kamil Kalisz.
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
/**
 * interface for handling results from activity
 */
public interface IntentHandler
{
    /**
     * @param requestCode request code for identifying results
     * @param intent intent to be started
     * @param resultCode result from started intent
     */
    void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode);
}
