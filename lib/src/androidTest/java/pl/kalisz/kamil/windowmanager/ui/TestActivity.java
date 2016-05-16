package pl.kalisz.kamil.windowmanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.kalisz.kamil.windowmanager.ActivityStarter;
import pl.kalisz.kamil.windowmanager.WindowStarter;
import pl.kalisz.kamil.windowmanager.WindowStarterImpl;

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
public class TestActivity extends Activity implements ActivityStarter {

    private WindowStarterImpl windowStarter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        windowStarter = new WindowStarterImpl(this);
        windowStarter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void startActivity(@NonNull int requestCode, @NonNull Intent intent) {
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        windowStarter.handleResult(requestCode,data,resultCode);
    }

    public WindowStarter getWindowStarter() {
        return windowStarter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        windowStarter.onSaveInstanceState(outState);
    }
}
