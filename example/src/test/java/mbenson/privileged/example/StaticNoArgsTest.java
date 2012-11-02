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
package mbenson.privileged.example;

import java.security.AccessController;
import java.security.PrivilegedAction;

import junit.framework.TestCase;
import mbenson.privileged.example.StaticNoArgs.CheckedException1;
import mbenson.privileged.example.StaticNoArgs.CheckedException2;

public class StaticNoArgsTest extends TestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        AccessController.doPrivileged(new PrivilegedAction<Void>() {

            @Override
            public Void run() {
                System.setProperty("foo", "foo-value");
                return null;
            }
        });
    }

    public void testThrowAwayFoo() {
        StaticNoArgs.throwAwayFoo();
    }

    public void testGetFoo() {
        assertEquals("foo-value", StaticNoArgs.getFoo());
    }

    public void testGetTrue() {
        assertSame(Boolean.TRUE, StaticNoArgs.getTrue());
    }

    public void testGetFalse() {
        assertFalse(StaticNoArgs.getFalse());
    }

    public void testThrowingCheckedException1() {
        try {
            StaticNoArgs.throwingCheckedException1();
            fail();
        } catch (CheckedException1 e) {
        }
    }

    public void testThrowingCheckedException2() {
        try {
            StaticNoArgs.throwingCheckedException2();
        } catch (CheckedException1 e) {
        } catch (CheckedException2 e) {
            return;
        }
        fail();
    }
}
