/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.specialists.boundary;

import app.actors.entity.Role;
import app.availabilities.entity.Availability;
import app.specialists.entity.Specialist;
import java.security.Principal;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author marcin
 */
@Stateless
//@DeclareRoles(value = "SPECIALIST")
@Path("/specialists")
public class SpecialistsEndpoint {
    
    @PersistenceContext 
    EntityManager em;
    
    @Resource
    SessionContext sessionContext;
    
    public Specialist create(Specialist specialist){
        em.persist(specialist);
        return specialist;
    }
    
    public Specialist update(Specialist specialist){
        em.merge(specialist);
        return specialist;
    }
    
    @GET
    @Produces("application/json")
    @javax.enterprise.inject.Produces @AllSpecialists
    public List<Specialist> findAll(){
        return em.createQuery("Select s From Specialist s").getResultList();
    }
    
    //@RolesAllowed(value = "SPECIALIST")
    @javax.enterprise.inject.Produces @AuthenticatedSpecialist
    public Specialist findAuthenticatedSpecialist(){
        
        Specialist specialist = new Specialist();
        Principal callerPrincipal = sessionContext.getCallerPrincipal();
        
        if(sessionContext.isCallerInRole(Role.SPECIALIST.toString())){
            return (Specialist) em.createQuery("Select c From Specialist c Where c.email = :email").setParameter("email", callerPrincipal.getName()).getSingleResult();
        }
        return specialist;
    }
    
    //@RolesAllowed("SPECIALIST")
    public Availability create(Availability availability){
        em.persist(availability);
        return availability;
    }
}
