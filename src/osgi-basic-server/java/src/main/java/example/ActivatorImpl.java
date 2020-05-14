package example;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

public class ActivatorImpl implements BundleActivator {
    public void start (BundleContext context) {
        try {
            System.out.println ("Example bundle started.");

            // Get reference to the HTTP service object.
            ServiceReference reference = context.getServiceReference (HttpService.class.getName ());
            HttpService service = (HttpService) context.getService (reference);

            service.registerServlet ("/", new ServletImpl (), null, null);
        }
        catch (Exception e) {
            e.printStackTrace ();
        }
      }

    public void stop (BundleContext context) {
        System.out.println ("Example bundle stopped.");
    }
}
