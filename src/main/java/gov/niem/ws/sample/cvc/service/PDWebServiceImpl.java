package gov.niem.ws.sample.cvc.service;

import gov.justice.lexs.meta._5.MessageMetadataElement;
import gov.lexs.ws.flex.jaxws.*;
import gov.lexs.ws.flex.pd.jaxb.msg.DoPublishAcknowledgedRequestElement;
import gov.lexs.ws.flex.pd.jaxb.msg.DoPublishAcknowledgedResponseElement;
import gov.lexs.ws.flex.pd.jaxb.msg.DoPublishElement;
import gov.niem.ws.sample.cvc.service.util.ServiceUtil;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;
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

@WebService(serviceName = "PDWebService",
portName = "PDWebServicePort",
endpointInterface = "gov.lexs.ws.flex.jaxws.PDWebServicePortType",
targetNamespace = "http://lexs.justice.gov/publishdiscover/5.0/ws",
wsdlLocation = "WEB-INF/wsdl/lexsPDwebserviceImpl.wsdl")
public class PDWebServiceImpl implements PDWebServicePortType {
    /*========================================================================*/
    /* CONSTANTS */
    /*========================================================================*/
    private static final String OBJECT_FACTORY_PACKAGE = "gov.lexs.ws.flex.pd.jaxb.msg";
    private static final String META_OBJECT_FACTORY_PACKAGE = "gov.justice.lexs.meta._5";
    private static final String RESPONSE_PATH = "samples/doPublishResponse.xml";

    /*========================================================================*/
    /* STATIC VARIABLES */
    /*========================================================================*/
    private static final Logger logger = Logger.getLogger(PDWebServiceImpl.class.getName());

    /*========================================================================*/
    /* PRIVATE VARIABLES */
    /*========================================================================*/
    private JAXBContext jaxbContext;

    /*========================================================================*/
    /* CONSTRUCTOR */
    /*========================================================================*/
    public PDWebServiceImpl() {
        try {
            jaxbContext = JAXBContext.newInstance(OBJECT_FACTORY_PACKAGE);
            ServiceUtil.createAndConfigureMarshaller(jaxbContext);
        }
        catch(JAXBException e){
            e.printStackTrace();
        }
    }

    /*========================================================================*/
    /* PUBLIC METHODS */
    /*========================================================================*/
    @Override
    public void doPublish(DoPublishElement parameters, MessageMetadataElement messageMetadata) {
        logger.log(Level.INFO, "Received doPublish");
        ServiceUtil.printMessagePart(META_OBJECT_FACTORY_PACKAGE, "Message Metadata", messageMetadata);
        ServiceUtil.printMessagePart(OBJECT_FACTORY_PACKAGE, "doPublish", parameters);
        //TODO: process incoming message
        logger.log(Level.INFO, "Finished processing doPublish");
    }

    @Override
    public DoPublishAcknowledgedResponseElement doPublishAcknowledged(DoPublishAcknowledgedRequestElement parameters, Holder<MessageMetadataElement> messageMetadata) throws BusinessRuleNotMetFault, InvalidRequestFault, NetworkFailureFault, OtherErrorFault, TimeoutFault {
        logger.log(Level.INFO, "Received doPublishAcknowledged");
        //TODO: Do some processing on incoming message
        //for now just return a canned response.
        DoPublishAcknowledgedResponseElement doPublishAcknowledgedResponseElement = null;
        try {
//            doPublishAcknowledgedResponseElement = ServiceUtil.unmarshalXml(DoPublishAcknowledgedResponseElement.class, OBJECT_FACTORY_PACKAGE, RESPONSE_PATH);
            doPublishAcknowledgedResponseElement = ServiceUtil.unmarshalXml(DoPublishAcknowledgedResponseElement.class, jaxbContext, RESPONSE_PATH);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, "Finished doPublishAcknowledged");
        return doPublishAcknowledgedResponseElement;
    }
}
