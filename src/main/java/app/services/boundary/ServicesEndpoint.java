/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.services.boundary;

import app.availabilities.entity.AvailabilityTime;
import app.services.entity.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.util.Comparator.comparing;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author marcin
 */
@Stateless
@Path("/services")
public class ServicesEndpoint {
    
    @PersistenceContext
    EntityManager entityManager;
   
    
    @GET
    @Produces("application/json")
    public List<Service> findAll(){
        
        List<Service> services = new ArrayList<>();
        services = entityManager.createQuery("Select s From Service s").getResultList();
        return services;
        
    }
    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Service findService(@PathParam("id") Long id){
        return entityManager.find(Service.class, id);
        
    }
    
     @GET
    @Path("{id}/availabilityTimes")
    @Produces("application/json")
    public List<AvailabilityTime> findAvailabilityTimesForService(@PathParam("id") Long id){
        
        List<AvailabilityTime> list = new ArrayList<>();
        Service service = findService(id);
        if(service != null){
            list = service.produceAvailabilityTimes();
            list.sort(comparing(AvailabilityTime::getLocalDateTime));
           
           
        }
        return list;
    }
    
    @GET
    @Path("{id}/availabilityTimes/{year}/{month}/{dayOfMonth}")
    @Produces("application/json")
    public List<AvailabilityTime> findaAvailabilityTimesForServiceAndDate(
            @PathParam("id") Long id, 
            @PathParam("year") Integer year,
            @PathParam("month") Integer month,
            @PathParam("dayOfMonth") Integer dayOfMonth){
        
        
        List<AvailabilityTime> list = new ArrayList<>();
        
        Service service = findService(id);
        
        if(service != null){
            list = service.getAvailablityTimesForDate(LocalDate.of(year, month, dayOfMonth));
            list.sort(comparing(AvailabilityTime::getLocalDateTime));
        }
        
        
        return list;
    }
    
    
    
    public Service create(Service service){
        entityManager.persist(service);
        return service;
    }
    
    public Service update(Service service){
        entityManager.merge(service);
        return service;
    }
   
    
    
}
