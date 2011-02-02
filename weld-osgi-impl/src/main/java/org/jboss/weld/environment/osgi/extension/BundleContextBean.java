package org.jboss.weld.environment.osgi.extension;

import org.osgi.framework.BundleContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.AnnotationLiteral;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: guillaume
 * Date: 01/02/11
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
public class BundleContextBean implements Bean<BundleContext> {

    private BundleContext bundleContext;

    public BundleContextBean(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public Set<Type> getTypes() {
        return Collections.singleton((Type) BundleContext.class);
    }

    @Override
    public Set<Annotation> getQualifiers() {
        Set<Annotation> s = new HashSet<Annotation>();
        s.add(new AnnotationLiteral<Default>() {});
        s.add(new AnnotationLiteral<Any>() {});
        return s;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return ApplicationScoped.class;
    }

    @Override
    public String getName() {
        return "BundleContext [" + bundleContext.getBundle().toString() + "]";
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
        return null;
    }

    @Override
    public Class<BundleContext> getBeanClass() {
        return BundleContext.class;
    }

    @Override
    public boolean isAlternative() {
        return false;
    }

    @Override
    public boolean isNullable() {
        return false;
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return Collections.emptySet();
    }

    @Override
    public BundleContext create(CreationalContext<BundleContext> bundleContextCreationalContext) {
        return bundleContext;
    }

    @Override
    public void destroy(BundleContext instance, CreationalContext<BundleContext> bundleContextCreationalContext) {
        // no-op
    }
}
