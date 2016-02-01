/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.customers.boundary;

import app.availabilities.entity.AvailabilityTime;
import java.io.Serializable;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author marcin
 */
@Path("/customers")
@Named
@SessionScoped
public class CustomerReservationEndpoint implements Serializable{
    
    
    
    private final static Logger logger = Logger.getLogger(CustomerReservationEndpoint.class.getName());
    
   
    
    private AvailabilityTime availabilityTime;
   
    
    @POST
    @Path("/availabilityTime")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setAvailabilityTime(AvailabilityTime availabilityTime){
        
     
        this.availabilityTime = availabilityTime;
        
        logger.log(Level.INFO, "availabilityTime: {0}", this.availabilityTime);
       
        URI uri = URI.create("customers/availabilityTime");
        
        return Response.created(uri).build();
    }
    
    @GET
    @Path("/availabilityTime")
    @Produces(MediaType.APPLICATION_JSON)
    public AvailabilityTime getAvailabilityTime(){
      
        logger.info(" getAvailabilityTime() " + availabilityTime);
        return availabilityTime;
    }

  
    
    
    
    
}
