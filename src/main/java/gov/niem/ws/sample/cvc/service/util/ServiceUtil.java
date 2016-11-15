package gov.niem.ws.sample.cvc.service.util;

import gov.lexs.ws.flex.de.jaxb.msg.DomainResponseElement;
import gov.niem.ws.sample.cvc.service.LexsNamespacePrefixMapper;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Copyright 2016 Georgia Tech Research Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ServiceUtil {

    /*========================================================================*/
    /* STATIC VARIABLES */
    /*========================================================================*/
    private static final Logger logger = Logger.getLogger(ServiceUtil.class.getName());

    /**
     * Creates a date
     * @return {@link XMLGregorianCalendar} for the current date
     */
    public static XMLGregorianCalendar createDate(){
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd'T'kk:mm:ss'Z'");
        XMLGregorianCalendar calendar = null;
        try{
            calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(new Date()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * Used to print out the Marshalled XML content of a JAXB Object
     * @param classPackage the package to use for the JAXBContext
     * @param elementName the name of the element being printed
     * @param message the JAXB Object to marshal
     */
    public static void printMessagePart(String classPackage, String elementName, Object message){
        logger.log(Level.INFO, "Printing " + elementName);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(classPackage);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(message, System.out);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        logger.log(Level.INFO, "Finished Printing " + elementName);
    }

    public static Marshaller createAndConfigureMarshaller(JAXBContext context) throws JAXBException{
//        JAXBContext context = JAXBContext.newInstance(packageName);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new LexsNamespacePrefixMapper());
        return marshaller;
    }

    /**
     * Used to unmarshal an XML file to a JAXB Object
     * @param returnType the JAXB Object to return
     * @param packageName the name of the package to use for the JAXBContext object.
     * @param responseFilePath the path to the XML file to unmarshal
     * @param <T> the Type of JAXB object to return.
     * @return a JAXB Object that contains the unmarshalled XML.
     * @throws Exception
     */
//    public static <T> T unmarshalXml( Class<T> returnType, String packageName, String responseFilePath ) throws Exception{
//        JAXBContext context = JAXBContext.newInstance(packageName);
    public static <T> T unmarshalXml( Class<T> returnType, JAXBContext context, String responseFilePath ) throws JAXBException{
//        JAXBContext context = JAXBContext.newInstance(packageName);
        Unmarshaller unmarshaller = context.createUnmarshaller();
//        unmarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new LexsNamespacePrefixMapper());
        Object obj = JAXBIntrospector.getValue(unmarshaller.unmarshal(getResponseFile(responseFilePath)));
        return returnType.cast(obj);
    }

    /**
     * Retrieves a file from the classpath
     * @param path
     * @return
     */
    public static File getResponseFile(String path){
        ClassLoader classLoader = ServiceUtil.class.getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        return file;
    }
}
