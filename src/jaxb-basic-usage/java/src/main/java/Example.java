import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import data.AnExampleDataClass;

public class Example {

    public static void main (String [] args) {

        AnExampleDataClass data = new AnExampleDataClass ();

        try {
            JAXBContext context = JAXBContext.newInstance (AnExampleDataClass.class);
            Marshaller marshaller = context.createMarshaller ();
            marshaller.marshal (data, System.out);
        } catch (JAXBException e) {
            System.out.println (e);
        }
    }
}
