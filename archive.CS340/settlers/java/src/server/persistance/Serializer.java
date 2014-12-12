package server.persistance;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by mitch10e on 12/10/14.
 */
public class Serializer {
    public static String serializeObject(Object object) {
        XStream xstream = new XStream(new DomDriver());
        String xml = xstream.toXML(object);
        return xml;
    }



//    public static Object deserializeObject(Object object) {
//
//    }

}
