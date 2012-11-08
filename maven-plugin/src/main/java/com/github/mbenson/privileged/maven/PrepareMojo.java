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

import javassist.NotFoundException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

/**
 * Prepare for weaving by deleting classes previously woven with a different
 * policy.
 */
@Mojo(name = "prepare", defaultPhase = LifecyclePhase.INITIALIZE, requiresDependencyCollection = ResolutionScope.COMPILE)
public class PrepareMojo extends PrivilegedMojo {
    @Component
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException {
        if (target.exists()) {
            try {
                createWeaver().prepare();
            } catch (NotFoundException e) {
                throw new MojoExecutionException("error", e);
            }
        }
    }

}
