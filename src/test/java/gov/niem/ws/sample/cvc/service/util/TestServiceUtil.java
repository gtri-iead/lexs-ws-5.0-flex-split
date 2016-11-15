package gov.niem.ws.sample.cvc.service.util;


import gov.lexs.ws.flex.sr.jaxb.msg.DoSearchResponseElement;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import static junit.framework.Assert.*;

import java.io.File;
import java.io.StringWriter;
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
public class TestServiceUtil {

    /*========================================================================*/
    /* CONSTANTS */
    /*========================================================================*/
    private String TEST_FILE_PATH = "samples/textSearchResponse.xml";

    /*========================================================================*/
    /* STATIC VARIABLES */
    /*========================================================================*/
    private static final Logger logger = Logger.getLogger(TestServiceUtil.class.getName());

    /*========================================================================*/
    /* TESTS */
    /*========================================================================*/
    @Test
    public void testNamespaceGen() throws Exception{
        logger.log(Level.INFO, "Testing Namespace Gen");
        Boolean didError = false;
        //create a jaxbcontext
        JAXBContext jaxbContext = jaxbContext = JAXBContext.newInstance("gov.lexs.ws.flex.sr.jaxb.msg");

        //unmarshall to an object
        logger.log(Level.INFO, "Unmarshalling File");
        DoSearchResponseElement doSearchResponseElement = null;
        try {
            doSearchResponseElement = ServiceUtil.unmarshalXml(DoSearchResponseElement.class, jaxbContext, TEST_FILE_PATH );
        }
        catch (Exception e) {
            e.printStackTrace();
            didError = true;
        }
        assertFalse(didError);
        assertNotNull(doSearchResponseElement);

        logger.log(Level.INFO, "Marshalling back to text");
        Marshaller marshaller = ServiceUtil.createAndConfigureMarshaller(jaxbContext);
        //now marshall
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(doSearchResponseElement, stringWriter);
        String marshalledXML = stringWriter.toString();

        logger.log(Level.INFO, "Testing marshalled XML");
        assertFalse( marshalledXML.isEmpty());
        assertTrue(marshalledXML.contains("<lexssr:"));
        assertTrue(marshalledXML.contains("<lexs:"));
        assertTrue(marshalledXML.contains("<nc:"));
        assertTrue(marshalledXML.contains("<j:"));

        logger.log(Level.INFO, "Finished Testing Namespace Gen");
    }

    @Test
    public void testGetFile() throws Exception{
        File testFile = ServiceUtil.getResponseFile(TEST_FILE_PATH);
        assertNotNull(testFile);
        assertTrue(testFile.exists());
    }

    @Test
    public void testCreateDate() throws Exception{
        XMLGregorianCalendar calendar = ServiceUtil.createDate();
        assertNotNull(calendar);
    }
}
