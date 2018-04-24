/*
 * Copyright 2016-2018 RockyQu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.logg.printer;

import me.logg.config.LoggConfiguration;
import me.logg.config.LoggConstant;

import java.io.StringReader;
import java.io.StringWriter;

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

    public XmlPrinter(LoggConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void printer(Type type, String tag, String object) {
        StringBuilder xml = new StringBuilder();
        xml.append(LoggConstant.SPACE).append(LoggConstant.BR);

        try {
            Source xmlInput = new StreamSource(new StringReader(object));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            xml.append(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            xml.append(object);
        }
        super.printer(type, tag, xml.toString());
    }
}
