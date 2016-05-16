package pl.kalisz.kamil.windowmanager;

import android.os.Bundle;
import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


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
@RunWith(AndroidJUnit4.class)
public class RequestCodeGeneratorTest {

    @Test
    public void firstGeneratedCodeIsBiggerThnZero() {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator();
        String stringCode = "REQUEST_CODE";

        int generatedCode = requestCodeGenerator.generate(stringCode);

        Assert.assertTrue(generatedCode > 0);
    }

    @Test
    public void whenGenerateCodeForStringNextGenerationWillReturnSameCode() {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator();
        String stringCode = "REQUEST_CODE";

        int firstGeneratedCode = requestCodeGenerator.generate(stringCode);
        int secondGeneratedCode = requestCodeGenerator.generate(stringCode);

        Assert.assertEquals(firstGeneratedCode, secondGeneratedCode);
    }

    @Test
    public void whenGenerateCodeForTwoDifferentStringCodesAreDifferent() {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator();
        String requestCode = "REQUEST_CODE";
        String otherRequestCode = "OTHER_REQUEST_CODE";

        int firstGeneratedCode = requestCodeGenerator.generate(requestCode);
        int secondGeneratedCode = requestCodeGenerator.generate(otherRequestCode);

        Assert.assertNotEquals(firstGeneratedCode, secondGeneratedCode);
    }

    @Test
    public void whenGenerateCodeForSameStringWhenOtherCodesWasGeneratedCodesAreTheSame() {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator();
        String requestCode = "REQUEST_CODE";
        String otherRequestCode = "OTHER_REQUEST_CODE";

        int firstGeneratedCode = requestCodeGenerator.generate(requestCode);
        requestCodeGenerator.generate(otherRequestCode);
        int secondGeneratedCode = requestCodeGenerator.generate(requestCode);

        Assert.assertEquals(firstGeneratedCode, secondGeneratedCode);
    }

    @Test
    public void whenGetRawRequestCodeForValueNotGeneratedByGeneratorWillReturnNull() {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator();

        Assert.assertNull(requestCodeGenerator.getRawRequestCode(4));
    }

    @Test
    public void whenGetRawRequestCodeForValueGeneratedByGeneratorWillReturnSameCode() {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator();

        String requestCode = "REQUEST_CODE";

        int generatedRequestCode = requestCodeGenerator.generate(requestCode);

        Assert.assertEquals(requestCode,requestCodeGenerator.getRawRequestCode(generatedRequestCode));
    }

    @Test
    public void whenGenerateCodeAndSaveGeneratorAfterRestoringGeneratedValuesAreRestored()
    {
        RequestCodeGenerator requestCodeGenerator = new RequestCodeGenerator();
        String requestCode = "REQUEST_CODE";
        requestCodeGenerator.generate(requestCode);

        Bundle savedState = new Bundle();
        requestCodeGenerator.onSaveInstanceState(savedState);

        Parcel parcel = Parcel.obtain();

        savedState.writeToParcel(parcel,0);
        parcel.setDataPosition(0);

        Bundle restoredState = Bundle.CREATOR.createFromParcel(parcel);

        requestCodeGenerator.onRestoreInstanceState(restoredState);
    }
}