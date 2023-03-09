import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;

import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import data.AnExampleDataClass;

public class Example {

    public static void main (String [] args) {

        AnExampleDataClass data = new AnExampleDataClass ();

        data.anIntField = 123;
        data.aFloatField = 12.34f;
        data.aDoubleField = 12.34e56d;
        data.aBoxedIntField = 987;
        data.aRequiredStringField = "a string";

        data.anArrayWithoutAWrapper = new int [] {1, 2, 3};
        data.anArrayWithAWrapper = new int [] {12, 34, 56};

        data.aListElement = new LinkedList<AnExampleDataClass> ();
        data.aListElement.add (new AnExampleDataClass ());

        data.aSetElement = new HashSet<AnExampleDataClass> ();
        data.aSetElement.add (new AnExampleDataClass ());

        data.aMapElement = new HashMap<Integer, AnExampleDataClass> ();
        data.aMapElement.put (123, new AnExampleDataClass ());
        data.aMapElement.put (456, new AnExampleDataClass ());

        try {
            JAXBContext context = JAXBContext.newInstance (AnExampleDataClass.class);
            Marshaller marshaller = context.createMarshaller ();
            marshaller.marshal (data, System.out);
        } catch (JAXBException e) {
            System.out.println (e);
        }
    }
}
