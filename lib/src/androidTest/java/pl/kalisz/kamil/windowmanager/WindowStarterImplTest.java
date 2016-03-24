package pl.kalisz.kamil.windowmanager;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.kalisz.kamil.windowmanager.ui.TestActivity;

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
//TODO rotation test
@RunWith(AndroidJUnit4.class)
public class WindowStarterImplTest
{
    public static final String TEST_REQUEST_CODE = "TEST_REQUEST_CODE";

    @Rule
    public ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);

    private Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

    @Test
    public void whenStartActivityFromManagerActivityIsCalled()
    {
        Intent activityIntent = new Intent(activityTestRule.getActivity(), Activity.class);

        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(0,new Intent());
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(Activity.class.getName(),activityResult,false);

        activityTestRule.getActivity().getWindowStarter().startActivity(TEST_REQUEST_CODE,activityIntent);

        Assert.assertEquals(1,monitor.getHits());
    }

    @Test
    public void whenStartActivityFromManagerActivityIsAndListenerIsRegisteredResultIsReturned()
    {
        WindowStarter windowStarter = activityTestRule.getActivity().getWindowStarter();

        Intent activityIntent = new Intent(activityTestRule.getActivity(), Activity.class);

        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(0,new Intent());
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(Activity.class.getName(),activityResult,false);

        final CallbackHandler<Intent> resultCallback = new CallbackHandler<>();

        windowStarter.register(TEST_REQUEST_CODE, new IntentHandler()
        {
            @Override
            public void onActivityResult(String requestCode, @NonNull Intent intent, int resultCode) {
                resultCallback.setCallback(intent);
            }
        });

        windowStarter.startActivity(TEST_REQUEST_CODE,activityIntent);

        Assert.assertEquals(activityIntent,resultCallback.getLastCallback());
        Assert.assertEquals(1,resultCallback.getHits());
    }


}