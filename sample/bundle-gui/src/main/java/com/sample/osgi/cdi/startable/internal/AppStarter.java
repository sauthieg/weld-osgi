package com.sample.osgi.cdi.startable.internal;

import com.sample.osgi.cdi.startable.Starter;
import com.sample.osgi.cdi.gui.internal.SpellCheckerGui;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.jboss.weld.environment.osgi.api.extension.Specification;
import org.jboss.weld.environment.osgi.api.extension.events.AbstractServiceEvent;
import org.jboss.weld.environment.osgi.api.extension.events.BundleContainerInitialized;
import org.jboss.weld.environment.osgi.api.extension.events.BundleContainerShutdown;
import org.osgi.framework.BundleContext;

@ApplicationScoped
public class AppStarter implements Starter {

    @Inject SpellCheckerGui gui;

    @Inject
    private BundleContext bundleContext;

    @PostConstruct
    @Override
    public void init() {
        gui.start();
        System.out.println("BundleCOntext: " + bundleContext);

    }

    @PreDestroy
    @Override
    public void stop() {
        gui.stop();
    }

    public void bindService(@Observes @Specification(Instance.class) AbstractServiceEvent event) {
        if (event.eventType() == AbstractServiceEvent.EventType.SERVICE_ARRIVAL) {
            event.type(Instance.class).getService();
        }
    }

    public void onStartup(@Observes BundleContainerInitialized event) {
        System.out.println("CDI Container for bundle "
                + event.getBundleContext().getBundle() + " started");
    }

    public void onShutdown(@Observes BundleContainerShutdown event) {
        System.out.println("CDI Container for bundle "
                + (event.getBundleContext() == null ? "gui" : event.getBundleContext().getBundle())
                + " stopped");
    }
}
