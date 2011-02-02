package org.jboss.weld.environment.osgi.extension;

import org.jboss.weld.environment.osgi.api.extension.ExtensionProvider;
import org.osgi.framework.BundleContext;

import javax.enterprise.inject.spi.Extension;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 01/02/11
 * Time: 20:20
 * To change this template use File | Settings | File Templates.
 */
public class OSGiExtensionProvider implements ExtensionProvider {
    @Override
    public Extension newExtension(BundleContext bundleContext) {
        CDIOSGiExtension extension = new CDIOSGiExtension();
        extension.setBundleContext(bundleContext);
        return extension;
    }
}
