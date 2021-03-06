/*
 *  Copyright the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.mbenson.privileged.example;

import com.github.mbenson.privileged.Privileged;

public abstract class StaticOverloading {
    private StaticOverloading() {
    }

    @Privileged
    static String get() {
        return System.getProperty("foo");
    }

    @Privileged
    static String get(String s) {
        return System.getProperty(s);
    }

    @Privileged
    static String get(int i, char c, short s) {
        return System.getProperty(new String(new char[] { (char) i, c, (char) s }));
    }
}
