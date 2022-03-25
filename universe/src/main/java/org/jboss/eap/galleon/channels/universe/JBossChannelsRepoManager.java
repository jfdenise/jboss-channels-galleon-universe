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

import java.util.Set;
import org.jboss.galleon.universe.maven.repo.MavenRepoManager;

/**
 * This interface will be moved to an abstract class with support shared between installer and plugin.
 * @author jdenise@redhat.com
 */
public interface JBossChannelsRepoManager extends MavenRepoManager {
    public String getChannel(String producer);

    public Set<String> getProducers();
}
