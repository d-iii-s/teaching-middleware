package example;

import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletImpl extends HttpServlet {
    @Override public void doGet (HttpServletRequest request, HttpServletResponse response) throws java.io.IOException {

        // Response type is text.
        response.setContentType ("text/plain");

        // Just copy request headers into response.
        PrintWriter output = response.getWriter ();
        output.println ("Servlet request: " + request.getRequestURI ());
        Enumeration <String> headers = request.getHeaderNames ();
        while (headers.hasMoreElements ()) {
            String header = headers.nextElement ();
            output.println (header + ": " + request.getHeader (header));
        }
    }
}
