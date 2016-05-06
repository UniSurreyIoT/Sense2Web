/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.iot.s2w.model.iota.restlet.response;

import uk.ac.surrey.ee.iot.s2w.probengine.servlet.ETContextListener;
import uk.ac.surrey.ee.iot.s2w.config.ConfigParameters;

/**
 *
 * @author te0003
 */
public class S2W_ETStatusResponse {
    
    
    boolean iot_resource_index_created=false;
    boolean iot_entity_index_created=false;
    boolean iot_service_index_created=false;
    String iot_indexing_message= "";
    
    public S2W_ETStatusResponse( boolean iota_resource, boolean iota_entity, boolean iota_service){
    
        this.iot_resource_index_created=iota_resource;
        this.iot_entity_index_created=iota_entity;
        this.iot_service_index_created=iota_service;
        
        ConfigParameters cp = new ConfigParameters(ETContextListener.servletContext);
        int minTrainingSet = cp.getTraining_set_min();

        if (!(this.iot_resource_index_created ||this.iot_entity_index_created || this.iot_service_index_created)){
            iot_indexing_message = "To enable indexing for an IoT Element, minimum number of registrations is "+ minTrainingSet;
            
        
        }
    }
    
    
}
