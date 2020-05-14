package example;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletImpl extends HttpServlet {
    @Override public void doGet (HttpServletRequest request, HttpServletResponse response) throws java.io.IOException {

        // Response type is text.
        response.setContentType ("text/plain");

        // Just copy request headers into response.
        java.io.PrintWriter output = response.getWriter ();
        output.println ("Servlet request: " + request.getRequestURI ());
        for (java.util.Enumeration headers = request.getHeaderNames () ; headers.hasMoreElements () ; ) {
            String header = (String) headers.nextElement ();
            output.println (header + ": " + request.getHeader (header));
        }
    }
}
