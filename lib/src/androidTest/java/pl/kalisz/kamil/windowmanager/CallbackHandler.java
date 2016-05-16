package pl.kalisz.kamil.windowmanager;

import java.util.ArrayList;
import java.util.List;

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
 * Simple class for tracking callback from anonymous test callbacks
 * @param <V>
 */
public class CallbackHandler<V>
{
    private List<V> value = new ArrayList<>();

    public int getHits() {
        return value.size();
    }

    public V getLastCallback() {
        return value.get(value.size()-1);
    }

    public V getCallback(int position) {
        return value.get(position);
    }

    public void setCallback(V value)
    {
        this.value.add(value);
    }
}
