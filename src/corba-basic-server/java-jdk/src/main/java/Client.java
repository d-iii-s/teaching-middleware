import java.io.FileReader;
import java.io.BufferedReader;

public class Client {
    public static void main (String [] asArgs) {
        try {

            // Initialize the CORBA library.
            //
            // This includes getting references to the ORB object,
            // which can convert string references into proxies.

            org.omg.CORBA.ORB oORB = org.omg.CORBA.ORB.init (asArgs, null);

            // Create proxy for server object.
            //
            // String reference is expected in file.

            String oReferenceText;
            try (BufferedReader oReferenceFile = new BufferedReader (new FileReader ("ior"))) {
                oReferenceText = oReferenceFile.readLine ();
            }

            org.omg.CORBA.Object oServiceBase = oORB.string_to_object (oReferenceText);
            AnExampleService oService = AnExampleServiceHelper.narrow (oServiceBase);

            // This is the actual remote call.

            oService.display ("Hello from plain Java client !");

        } catch (java.lang.Exception oEx) {
            oEx.printStackTrace ();
        }
    }
}
