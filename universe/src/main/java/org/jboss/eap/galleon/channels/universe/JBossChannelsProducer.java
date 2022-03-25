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
import org.jboss.galleon.universe.Producer;
import org.jboss.galleon.universe.maven.MavenErrors;
import org.jboss.galleon.util.CollectionUtils;

/**
 *
 * @author jdenise@redhat.com
 */
class JBossChannelsProducer implements Producer<JBossChannelsChannel> {

    private final JBossChannelsUniverse universe;
    private final String name;
    private Map<String, JBossChannelsChannel> channels = Collections.emptyMap();

    JBossChannelsProducer(JBossChannelsUniverse universe, String name) {
        this.universe = universe;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasFrequencies() {
        return false;
    }

    @Override
    public Collection<String> getFrequencies() {
        return Collections.emptySet();
    }

    @Override
    public boolean hasDefaultFrequency() {
        return false;
    }

    @Override
    public String getDefaultFrequency() {
        return null;
    }

    @Override
    public boolean hasChannel(String name) throws ProvisioningException {
        return true;
    }

    @Override
    public JBossChannelsChannel getChannel(String channelName) throws ProvisioningException {
        String currentChannel = universe.artifactResolver.getChannel(name);
        JBossChannelsChannel channel;
        if (currentChannel != null && currentChannel.contains(channelName)) {
            channel = new JBossChannelsChannel(universe, channelName);
            channels = CollectionUtils.put(channels, channelName, channel);
        } else {
            throw MavenErrors.channelNotFound(name, channelName);
        }
        return channel;
    }

    @Override
    public Collection<JBossChannelsChannel> getChannels() throws ProvisioningException {
        return channels.values();
    }

    @Override
    public boolean hasDefaultChannel() {
        return false;
    }

    @Override
    public JBossChannelsChannel getDefaultChannel() {
        return null;
    }
}
