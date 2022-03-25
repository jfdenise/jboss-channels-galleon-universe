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

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.jboss.galleon.ProvisioningException;
import org.jboss.galleon.universe.Channel;
import org.jboss.galleon.universe.FeaturePackLocation;
import org.jboss.galleon.universe.maven.MavenArtifact;

/**
 *
 * @author jdenise@redhat.com
 */
public class JBossChannelsChannel implements Channel {

    private final JBossChannelsUniverse universe;
    private final String name;

    JBossChannelsChannel(JBossChannelsUniverse universe, String name) {
        this.universe = universe;
        this.name = name;
    }

    /* (non-Javadoc)
     * @see org.jboss.galleon.universe.Channel#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see org.jboss.galleon.universe.Channel#resolve(org.jboss.galleon.FeaturePackLocation)
     */
    @Override
    public Path resolve(FeaturePackLocation fpl) throws ProvisioningException {
        return universe.artifactResolver.resolve(JBossChannelsUniverse.toMavenCoords(fpl));
    }

    @Override
    public List<String> getAllBuilds(FeaturePackLocation fpl) throws ProvisioningException {
        return Collections.emptyList();
    }

    @Override
    public String getLatestBuild(FeaturePackLocation fpl) throws ProvisioningException {
        final String producer = fpl.getProducerName();
        final int colon = producer.indexOf(':');
        if(colon <= 0) {
            throw new ProvisioningException("Failed to determine group and artifact IDs for " + fpl);
        }
        final StringBuilder buf = new StringBuilder();
        buf.append(producer.substring(0, colon)).append(':').append(producer.substring(colon + 1));

        MavenArtifact artifact = new MavenArtifact();
        artifact.setExtension("zip");
        artifact.setGroupId(producer.substring(0, colon));
        artifact.setArtifactId(producer.substring(colon + 1));
        universe.artifactResolver.resolve(artifact);
        return artifact.getVersion();
    }

    @Override
    public boolean isResolved(FeaturePackLocation fpl) throws ProvisioningException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getLatestBuild(FeaturePackLocation.FPID fpid) throws ProvisioningException {
        return getLatestBuild(fpid.getLocation());
    }

    @Override
    public boolean isDevBuild(FeaturePackLocation.FPID fpid) {
        return false;
    }
}
