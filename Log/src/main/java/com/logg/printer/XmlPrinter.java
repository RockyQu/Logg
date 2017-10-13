package com.logg.printer;

import com.logg.printer.manager.PrinterManager;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * XML Printer
 */
public class XmlPrinter extends DefaultPrinter {

    public XmlPrinter() {

    }

    @Override
    public void printer(Type type, String tag, String object) {
        String xml;
        try {
            Source xmlInput = new StreamSource(new StringReader(object));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            xml = xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (TransformerException e) {
            xml = object;
        }
        super.printer(type, tag, xml);
    }
}
