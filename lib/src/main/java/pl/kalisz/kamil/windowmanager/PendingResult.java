package pl.kalisz.kamil.windowmanager;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

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
public class PendingResult implements Parcelable {
    public PendingResult(int resultCode, Intent data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    private int resultCode;

    private Intent data;

    protected PendingResult(Parcel in) {
        resultCode = in.readInt();
        data = in.readParcelable(Intent.class.getClassLoader());
    }

    public static final Creator<PendingResult> CREATOR = new Creator<PendingResult>() {
        @Override
        public PendingResult createFromParcel(Parcel in) {
            return new PendingResult(in);
        }

        @Override
        public PendingResult[] newArray(int size) {
            return new PendingResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resultCode);
        dest.writeParcelable(data, flags);
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getData() {
        return data;
    }
}
