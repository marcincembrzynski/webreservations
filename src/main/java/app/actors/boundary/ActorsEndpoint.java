/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.actors.boundary;

import app.actors.entity.Actor;
import app.actors.entity.Role;
import app.result.Failure;
import app.result.Result;
import app.result.Success;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import org.jboss.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author marcin
 */
@Stateless
public class ActorsEndpoint {
    
  
    
    private static final Logger logger = Logger.getLogger(ActorsEndpoint.class.toString());
    

    @PersistenceContext
    EntityManager em;

    @Resource
    SessionContext sessionContext;

    @Produces
    @AuthenticatedActor
    public Actor findAuthenticatedActor() {

        Principal callerPrincipal = sessionContext.getCallerPrincipal();
        logger.info(callerPrincipal.toString());
        Actor actor = new Actor();
        actor.setActorRole(Role.ANONYMOUS);
        
        
        
        if("anonymous".equals(callerPrincipal.getName())){
            return actor;
        }
        
        try {
            actor = (Actor) em.createNamedQuery("findActorByEmail").setParameter("email", callerPrincipal.getName()).getSingleResult();
        } catch (javax.persistence.NoResultException noResultException) {
           logger.info(noResultException);
        }
        
        return actor;
    }
    
    
    public Result<Actor> loginRegister(Actor actor){
        return null;
    }
    
   
    
    public Result<Actor> register(Actor actor){
        
        int size = em.createNamedQuery("findActorByEmail").setParameter("email", actor.getEmail()).getResultList().size();
        if(size > 0){
            
           return new Failure<>("email.address.already.in.use", actor);
        }
        
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(actor.getPasswordForm().getBytes());
            byte byteData[] = md.digest();
            String encodedPassword = DatatypeConverter.printBase64Binary(byteData);
            actor.setPassword(encodedPassword);
          
            
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex);
        }
     
      
        em.persist(actor);
        logger.info(actor);
        return new Success<>("customer.created", actor);
       
    }
}
