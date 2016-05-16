package pl.kalisz.kamil.windowmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

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
@RunWith(AndroidJUnit4.class)
public class ResultHandlerTest {
    @Test
    public void whenThereIsListenerForResultListenerIsFiredAfterOnActivityResultIsCalled() {
        String requestCode = "REQUEST_CODE";
        int resultCode = Activity.RESULT_FIRST_USER;
        Intent resultIntent = new Intent();

        final CallbackHandler<Intent> callbackHandler = new CallbackHandler<>();
        ResultHandler resultHandler = new ResultHandler();

        resultHandler.registerIntentHandler(requestCode, new IntentHandler() {
            @Override
            public void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode) {
                callbackHandler.setCallback(intent);
            }
        });

        resultHandler.onActivityResult(requestCode, resultIntent, resultCode);

        Assert.assertEquals(resultIntent, callbackHandler.getLastCallback());
    }

    @Test
    public void whenThereIsNoListenerForResultResultIsCachedListenerIsFiredAfterListenerIsAdded() {
        String requestCode = "REQUEST_CODE";
        int resultCode = Activity.RESULT_FIRST_USER;
        Intent resultIntent = new Intent();

        final CallbackHandler<Intent> callbackHandler = new CallbackHandler<>();
        ResultHandler resultHandler = new ResultHandler();

        resultHandler.onActivityResult(requestCode, resultIntent, resultCode);

        resultHandler.registerIntentHandler(requestCode, new IntentHandler() {
            @Override
            public void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode) {
                callbackHandler.setCallback(intent);
            }
        });

        Assert.assertEquals(resultIntent, callbackHandler.getLastCallback());
    }

    @Test
    public void whenResultIcCachedResultIsRemovedFromCacheAfterListenerIsRegisteredAdFired() {
        String requestCode = "REQUEST_CODE";
        int resultCode = Activity.RESULT_FIRST_USER;
        Intent resultIntent = new Intent();

        final CallbackHandler<Intent> callbackHandler = new CallbackHandler<>();
        ResultHandler resultHandler = new ResultHandler();

        resultHandler.onActivityResult(requestCode, resultIntent, resultCode);

        resultHandler.registerIntentHandler(requestCode, new IntentHandler() {
            @Override
            public void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode) {
                callbackHandler.setCallback(intent);
            }
        });

        Assert.assertEquals(resultIntent, callbackHandler.getLastCallback());
        Assert.assertEquals(1, callbackHandler.getHits());

        resultHandler.registerIntentHandler(requestCode, new IntentHandler() {
            @Override
            public void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode) {
                callbackHandler.setCallback(intent);
            }
        });

        Assert.assertEquals(1, callbackHandler.getHits());
    }

    @Test
    public void whenPendingResultsAreSavedAndRestoredCorrectResultsAreRestoredTest() {
        ResultHandler originalResultHandler = new ResultHandler();
        String firstRequestCode = "REQ_CODE";
        String secondRequestCode = "SEC_REQ_CODE";
        Integer firstResultCode = 23;
        Integer secondResultCode = 45;
        originalResultHandler.onActivityResult(firstRequestCode, new Intent(), firstResultCode);
        originalResultHandler.onActivityResult(secondRequestCode, new Intent(), secondResultCode);

        Bundle savedState = new Bundle();
        originalResultHandler.onSaveInstanceState(savedState);

        Parcel parcel = Parcel.obtain();

        savedState.writeToParcel(parcel,0);
        parcel.setDataPosition(0);

        Bundle restoredState = Bundle.CREATOR.createFromParcel(parcel);
        restoredState.setClassLoader(getClass().getClassLoader());

        ResultHandler restoredResultHandler = new ResultHandler();
        restoredResultHandler.onRestoreInstanceState(restoredState);

        final CallbackHandler<Integer> integerCallbackHandler = new CallbackHandler<>();

        restoredResultHandler.registerIntentHandler(firstRequestCode, new IntentHandler() {
            @Override
            public void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode) {
                integerCallbackHandler.setCallback(resultCode);
            }
        });

        restoredResultHandler.registerIntentHandler(secondRequestCode, new IntentHandler() {
            @Override
            public void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode) {
                integerCallbackHandler.setCallback(resultCode);
            }
        });

        Assert.assertEquals(2, integerCallbackHandler.getHits());
        Assert.assertEquals(firstResultCode,integerCallbackHandler.getCallback(0));
        Assert.assertEquals(secondResultCode,integerCallbackHandler.getCallback(1));
    }

    @Test
    public void whenPendingResultsAreSavedAndRestoredResultAreCorrectAddedToNewPendingResultsTest() {
        ResultHandler originalResultHandler = new ResultHandler();
        String firstRequestCode = "REQ_CODE";
        String secondRequestCode = "SEC_REQ_CODE";
        String thirdRequestCode = "Third_REQ_CODE";
        Integer firstResultCode = 23;
        Integer secondResultCode = 45;
        Integer thirdResultCode = 48;
        originalResultHandler.onActivityResult(firstRequestCode, new Intent(), firstResultCode);
        originalResultHandler.onActivityResult(secondRequestCode, new Intent(), secondResultCode);

        Bundle savedState = new Bundle();
        originalResultHandler.onSaveInstanceState(savedState);

        Parcel parcel = Parcel.obtain();

        savedState.writeToParcel(parcel,0);
        parcel.setDataPosition(0);

        Bundle restoredState = Bundle.CREATOR.createFromParcel(parcel);
        restoredState.setClassLoader(getClass().getClassLoader());

        ResultHandler restoredResultHandler = new ResultHandler();

        restoredResultHandler.onActivityResult(thirdRequestCode, new Intent(), thirdResultCode);


        restoredResultHandler.onRestoreInstanceState(restoredState);

        final CallbackHandler<Integer> integerCallbackHandler = new CallbackHandler<>();

        restoredResultHandler.registerIntentHandler(firstRequestCode, new IntentHandler() {
            @Override
            public void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode) {
                integerCallbackHandler.setCallback(resultCode);
            }
        });

        restoredResultHandler.registerIntentHandler(secondRequestCode, new IntentHandler() {
            @Override
            public void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode) {
                integerCallbackHandler.setCallback(resultCode);
            }
        });

        restoredResultHandler.registerIntentHandler(thirdRequestCode, new IntentHandler() {
            @Override
            public void onActivityResult(@NonNull String requestCode, @NonNull Intent intent, int resultCode) {
                integerCallbackHandler.setCallback(resultCode);
            }
        });

        Assert.assertEquals(3, integerCallbackHandler.getHits());
        Assert.assertEquals(firstResultCode,integerCallbackHandler.getCallback(0));
        Assert.assertEquals(secondResultCode,integerCallbackHandler.getCallback(1));
        Assert.assertEquals(thirdResultCode,integerCallbackHandler.getCallback(2));

    }
}