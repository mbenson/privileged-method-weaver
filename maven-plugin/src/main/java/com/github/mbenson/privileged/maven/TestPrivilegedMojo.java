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
import java.util.List;

import org.apache.maven.plugins.annotations.Parameter;

import com.github.mbenson.privileged.weaver.AccessLevel;

public abstract class TestPrivilegedMojo extends AbstractPrivilegedMojo {

    @Parameter(readonly = true, required = true, defaultValue = "${project.testClasspathElements}")
    protected List<String> classpath;

    @Parameter(readonly = true, required = true, defaultValue = "${project.build.testOutputDirectory}")
    protected File target;

    @Parameter(readonly = false, required = true, defaultValue = "PUBLIC")
    protected AccessLevel accessLevel;

    @Override
    protected List<String> getClasspath() {
        return classpath;
    }

    @Override
    protected File getTarget() {
        return target;
    }

    @Override
    protected AccessLevel getAccessLevel() {
        return accessLevel;
    }
}
