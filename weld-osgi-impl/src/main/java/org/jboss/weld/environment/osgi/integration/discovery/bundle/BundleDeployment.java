/**
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.environment.osgi.integration.discovery.bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.environment.osgi.api.extension.ExtensionProvider;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.osgi.framework.Bundle;

import javax.enterprise.inject.spi.Extension;

/**
 * Weld Deployment for OSGi environment.
 * 
 * @author Peter Royle
 */
public class BundleDeployment implements Deployment {

    private BeanDeploymentArchive beanDeploymentArchive;
    private ServiceRegistry serviceRegistry;
    private List<Metadata<Extension>> extensions;

    public BundleDeployment(Bundle bundle,
                            Bootstrap bootstrap,
                            BundleBeanDeploymentArchiveFactory factory,
                            List<ExtensionProvider> providers) {
        this.beanDeploymentArchive = factory.scan(bundle, bootstrap);
        ResourceLoader loader = new BundleResourceLoader(bundle);
        this.beanDeploymentArchive.getServices().add(ResourceLoader.class, loader);
        this.serviceRegistry = new SimpleServiceRegistry();

        installExtensions(bundle, providers);
    }

    private void installExtensions(Bundle bundle, List<ExtensionProvider> providers) {
        extensions = new ArrayList<Metadata<Extension>>();
        for (ExtensionProvider provider : providers) {
            final Extension extension = provider.newExtension(bundle.getBundleContext());
            extensions.add(new Metadata<Extension>() {
                @Override
                public Extension getValue() {
                    return extension;
                }

                @Override
                public String getLocation() {
                    return "";
                }
            });
        }
    }

    @Override
    public List<BeanDeploymentArchive> getBeanDeploymentArchives() {
        return Collections.singletonList(beanDeploymentArchive);
    }

    @Override
    public BeanDeploymentArchive loadBeanDeploymentArchive(Class<?> beanClass) {
        return beanDeploymentArchive;
    }

    public BeanDeploymentArchive getBeanDeploymentArchive() {
        return beanDeploymentArchive;
    }

    @Override
    public ServiceRegistry getServices() {
        return serviceRegistry;
    }

    @Override
    public Iterable<Metadata<Extension>> getExtensions() {
        return extensions;
    }


}
