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
package com.github.mbenson.privileged.maven;

import java.io.File;
import java.net.URLClassLoader;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.github.mbenson.privileged.weaver.AccessLevel;
import com.github.mbenson.privileged.weaver.FilesystemWeaver;
import com.github.mbenson.privileged.weaver.URLArray;
import com.github.mbenson.privileged.weaver.PrivilegedMethodWeaver.Log;
import com.github.mbenson.privileged.weaver.PrivilegedMethodWeaver.Policy;

/**
 * Defines common properties.
 */
public abstract class AbstractPrivilegedMojo extends AbstractMojo {
    /**
     * Weaving policy to use.
     */
    @Parameter(property = "privilegedMethodWeaver.policy", required = true, defaultValue = "DYNAMIC")
    protected Policy policy;

    @Parameter(defaultValue = "false")
    protected boolean verbose;

    protected abstract List<String> getClasspath();

    protected abstract File getTarget();

    protected abstract AccessLevel getAccessLevel();

    protected FilesystemWeaver createWeaver() {
        return new FilesystemWeaver(policy, new URLClassLoader(URLArray.fromPaths(getClasspath())), getTarget()) {
            @Override
            protected boolean permitMethodWeaving(AccessLevel accessLevel) {
                return getAccessLevel().compareTo(accessLevel) <= 0;
            }
        }.loggingTo(new Log() {

            @Override
            public void info(String message) {
                getLog().info(message);
            }

            @Override
            public void error(String message) {
                getLog().error(message);
            }

            @Override
            public void debug(String message) {
                getLog().debug(message);
            }

            @Override
            public void verbose(String message) {
                if (verbose) {
                    getLog().info(message);
                } else {
                    getLog().debug(message);
                }
            }

            @Override
            public void warn(String message) {
                getLog().warn(message);
            }
        });
    }

}
