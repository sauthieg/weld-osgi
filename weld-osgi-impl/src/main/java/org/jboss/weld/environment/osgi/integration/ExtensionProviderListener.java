package org.jboss.weld.environment.osgi.integration;

import org.jboss.weld.environment.osgi.api.extension.ExtensionProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 01/02/11
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */
public class ExtensionProviderListener implements ServiceListener {

    List<ExtensionProvider> providers = new ArrayList<ExtensionProvider>();
    private BundleContext bundleContext;

    public ExtensionProviderListener(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public void serviceChanged(final ServiceEvent event) {

        switch (event.getType()) {
            case ServiceEvent.REGISTERED:
                providers.add(getService(event.getServiceReference()));
                break;
            case ServiceEvent.UNREGISTERING:
                providers.remove(getService(event.getServiceReference()));
                break;
        }

    }

    private ExtensionProvider getService(ServiceReference reference) {
        return (ExtensionProvider) bundleContext.getService(reference);
    }

    public List<ExtensionProvider> getProviders() {
        return providers;
    }

    public void addProvider(ExtensionProvider provider) {
        providers.add(provider);
    }
}
