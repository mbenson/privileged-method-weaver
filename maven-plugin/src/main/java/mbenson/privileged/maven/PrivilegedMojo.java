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
package mbenson.privileged.maven;

import java.io.File;
import java.net.URLClassLoader;
import java.util.List;

import mbenson.privileged.weaver.FilesystemWeaver;
import mbenson.privileged.weaver.PrivilegedMethodWeaver.Log;
import mbenson.privileged.weaver.PrivilegedMethodWeaver.Policy;
import mbenson.privileged.weaver.URLArray;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Defines common properties.
 */
public abstract class PrivilegedMojo extends AbstractMojo {
    /**
     * Weaving policy to use.
     */
    @Parameter(property = "privilegedMethodWeaver.policy", required = true, defaultValue = "DYNAMIC")
    protected Policy policy;

    @Parameter(readonly = true, required = true, defaultValue = "${project.compileClasspathElements}")
    protected List<String> classpath;

    @Parameter(readonly = true, required = true, defaultValue = "${project.build.outputDirectory}")
    protected File target;

    protected FilesystemWeaver createWeaver() {
        return new FilesystemWeaver(policy, new URLClassLoader(URLArray.fromPaths(classpath)), target)
            .loggingTo(new Log() {

                public void info(String message) {
                    getLog().info(message);
                }

                public void error(String message) {
                    getLog().error(message);
                }

                public void debug(String message) {
                    getLog().debug(message);
                }
            });
    }

}
