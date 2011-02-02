package org.jboss.weld.environment.osgi.api.extension;

import org.osgi.framework.BundleContext;

import javax.enterprise.inject.spi.Extension;

/**
 * This service interface allows Extension to be discovered as OSGi services.
 * The Extension instances will have access to the BundleContext of the explored bundle.
 */
public interface ExtensionProvider {

    /**
     * Creates a new {@code Extension} instance for the given BundleContext.
     * @param bundleContext traversed Bundle
     * @return a new Extension instance
     */
    Extension newExtension(BundleContext bundleContext);
}
