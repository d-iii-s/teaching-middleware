package example;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.namespace.PackageNamespace;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.HttpService;

public class ActivatorImpl implements BundleActivator, ServiceListener {

    BundleContext context;

    void displayDependencies () {
        Bundle bundle = context.getBundle ();
        BundleWiring wiring = bundle.adapt (BundleWiring.class);
        for (BundleWire wire : wiring.getRequiredWires (PackageNamespace.PACKAGE_NAMESPACE)) {
            String pakage = (String) wire.getCapability ().getAttributes ().get (PackageNamespace.PACKAGE_NAMESPACE);
            String source = wire.getProviderWiring ().getBundle ().getLocation ();
            System.out.println ("Dependency " + pakage + ": " + source);
        }
    }

    void tryRegisterServlet (ServiceReference reference) {
        try {
            HttpService service = (HttpService) context.getService (reference);
            service.registerServlet ("/", new ServletImpl (), null, null);
            System.out.println ("Example: Servlet registered.");
        }
        catch (Exception e) {
        }
    }

    public void start (BundleContext context) {
        System.out.println ("Activator: Bundle started.");

        // Needed for service reference resolution.
        this.context = context;

        // Display bundle dependencies to demonstrate bundle wiring navigation.
        displayDependencies ();

        // Listen to service availability events but try to register the servlet
        // immediately in case the service is already running.
        context.addServiceListener (this);
        tryRegisterServlet (context.getServiceReference (HttpService.class.getName ()));
    }

    public void stop (BundleContext context) {
        System.out.println ("Activator: Bundle stopped.");
    }

    public void serviceChanged (ServiceEvent event) {
        if (event.getType () == ServiceEvent.REGISTERED) {
            // A more complex code would examine the service properties
            // to determine whether to try to register the servlet.
            tryRegisterServlet (event.getServiceReference ());
        }
    }
}
