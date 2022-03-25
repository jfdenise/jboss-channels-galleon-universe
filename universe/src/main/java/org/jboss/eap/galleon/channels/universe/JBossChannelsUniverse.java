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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.jboss.galleon.ProvisioningException;
import org.jboss.galleon.universe.FeaturePackLocation;
import org.jboss.galleon.universe.Universe;
import org.jboss.galleon.universe.UniverseSpec;
import org.jboss.galleon.universe.maven.MavenErrors;
import org.jboss.galleon.util.CollectionUtils;

/**
 *
 * @author jdenise@redhat.con
 */
public class JBossChannelsUniverse implements Universe<JBossChannelsProducer> {

    private static final String ZIP = "zip";

    static String toMavenCoords(FeaturePackLocation fpl) throws ProvisioningException {
        final String producer = fpl.getProducerName();
        final int colon = producer.indexOf(':');
        if(colon <= 0) {
            throw new ProvisioningException("Failed to determine group and artifact IDs for " + fpl);
        }
        final StringBuilder buf = new StringBuilder();
        buf.append(producer.substring(0, colon)).append(':').append(producer.substring(colon + 1)).append(':').append(ZIP);
        buf.append(':').append(fpl.getBuild());
        return buf.toString();
    }

    final JBossChannelsRepoManager artifactResolver;
    private Map<String, JBossChannelsProducer> producers = Collections.emptyMap();

    public JBossChannelsUniverse(JBossChannelsRepoManager artifactResolver) {
        this.artifactResolver = artifactResolver;
    }

    @Override
    public String getFactoryId() {
        return JBossChannelsUniverseFactory.ID;
    }

    @Override
    public String getLocation() {
        return null;
    }

    @Override
    public boolean hasProducer(String producerName) throws ProvisioningException {
        return true;
    }

    @Override
    public JBossChannelsProducer getProducer(String producerName) throws ProvisioningException {
        if (!artifactResolver.getProducers().contains(producerName)) {
            throw MavenErrors.producerNotFound(producerName);
        }
        JBossChannelsProducer producer = producers.get(producerName);
        if(producer == null) {
            producer = new JBossChannelsProducer(this, producerName);
            producers = CollectionUtils.put(producers, producerName, producer);
        }
        return producer;
    }

    @Override
    public Collection<JBossChannelsProducer> getProducers() throws ProvisioningException {
        return producers.values();
    }
}
