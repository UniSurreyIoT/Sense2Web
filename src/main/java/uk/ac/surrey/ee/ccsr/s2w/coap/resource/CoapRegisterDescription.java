/**
 * *****************************************************************************
 * Copyright (c) 2012, Institute for Pervasive Computing, ETH Zurich. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. 3. Neither the name of the Institute nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE INSTITUTE AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN
 * NO EVENT SHALL THE INSTITUTE OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * This file is part of the Californium (Cf) CoAP framework.
 *****************************************************************************
 */
package uk.ac.surrey.ee.ccsr.s2w.coap.resource;

import java.util.List;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.server.resources.CoapExchange;
import uk.ac.surrey.ee.ccsr.s2w.config.EntityConfigParams;
import uk.ac.surrey.ee.ccsr.s2w.config.ResourceConfigParams;
import uk.ac.surrey.ee.ccsr.s2w.config.ServiceConfigParams;
import uk.ac.surrey.ee.ccsr.s2w.model.iota.restlet.resource.RegisterDescription;


public class CoapRegisterDescription extends CoapResource {

    public CoapRegisterDescription(String name) {
		super(name);
	}
	
    @Override
    public void handlePOST(CoapExchange exchange) {
        
        String regDescription = exchange.getRequestText();

//        System.out.println("This is the URI path:" + request.getOptions().getURIPathString());
//        System.out.println("This is the URI host:" + request.getOptions().getURIHost());
//        System.out.println("This is the URI query:" + request.getOptions().getURIQueryString());

        String objId = "";
        String objType = "";        
        int queryCount = exchange.getRequestOptions().getURIQueryCount();
        List<String> queries = exchange.getRequestOptions().getUriQuery();
        
        objType = exchange.getRequestOptions().getUriPathString();        
        if (objType.contains(ResourceConfigParams.objType)) {
            objType = ResourceConfigParams.objType;
        } else if (objType.contains(EntityConfigParams.objType)) {
            objType = EntityConfigParams.objType;
        } else if (objType.contains(ServiceConfigParams.objType)) {
            objType = ServiceConfigParams.objType;
        }

        for (int i = 0; i < queryCount; i++) {

            String queryStr = queries.get(i);
            String[] querySplit = queryStr.split("=");
            String queryName = querySplit[0];
            String queryValue = querySplit[1];

            System.out.println("query name " +i+ " is: " + queryName);
            System.out.println("query value " +i+ " is: " + queryValue);

            switch (queryName){
                case "objectID":
                    objId=queryValue;
                    break;
//                case "resultFormat":
//                    format=queryValue;
//                    break;
            }            
        }
        
        RegisterDescription rstltRegRes = new RegisterDescription();
        

        String result = rstltRegRes.register(objType, objId, regDescription);

        // create response
        Response response = new Response(CoAP.ResponseCode.CONTENT);

        // set payload
        response.setPayload(result);        

        // complete the request
         exchange.respond(response);
    }
}
