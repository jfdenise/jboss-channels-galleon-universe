/*
 * Copyright 2016-2022 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.eap.galleon.channels.universe;

import org.jboss.galleon.ProvisioningException;
import org.jboss.galleon.repo.RepositoryArtifactResolver;
import org.jboss.galleon.universe.Universe;
import org.jboss.galleon.universe.UniverseFactory;
import org.jboss.galleon.universe.maven.repo.MavenRepoManager;

/**
 *
 * @author Alexey Loubyansky
 */
public class JBossChannelsUniverseFactory implements UniverseFactory {

    public static final String ID = "jboss-channels";

    /* (non-Javadoc)
     * @see org.jboss.galleon.universe.UniverseFactory#getFactoryId()
     */
    @Override
    public String getFactoryId() {
        return ID;
    }

    /* (non-Javadoc)
     * @see org.jboss.galleon.universe.UniverseFactory#getRepositoryId()
     */
    @Override
    public String getRepositoryId() {
        return MavenRepoManager.REPOSITORY_ID;
    }

    @Override
    public Universe<?> getUniverse(RepositoryArtifactResolver artifactResolver, String location, boolean absoluteLatest)
            throws ProvisioningException {
        if (! (artifactResolver instanceof JBossChannelsRepoManager)) {
            throw new ProvisioningException("JBoss channels have not been configured.");
        }
        return new JBossChannelsUniverse((JBossChannelsRepoManager)artifactResolver);
    }
}
