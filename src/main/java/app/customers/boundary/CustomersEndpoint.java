/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.customers.boundary;

import app.actors.boundary.AuthenticatedActor;
import app.actors.entity.Actor;
import app.actors.entity.Role;
import app.customers.entity.Customer;
import java.security.Principal;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import org.jboss.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author marcin
 */
@Stateless
public class CustomersEndpoint {
    
    private final static Logger logger = Logger.getLogger(CustomersEndpoint.class);
    
    @PersistenceContext
    EntityManager em;
    
    @Resource
    SessionContext sessionContext;
    
    public Customer register(Customer customer){
        em.persist(customer);
        return customer;
    }
    
    @Produces
    @AllCustomers
    public List<Customer> findAllCustomers(){
        return em.createQuery("Select c From Customer c").getResultList();
    }
    
    @Produces @AuthenticatedCustomer
    public Customer findAuthenticatedCustomer(){
        
        
        Customer customer = new Customer();
        Principal callerPrincipal = sessionContext.getCallerPrincipal();
        logger.info(callerPrincipal.getName());
        logger.info(sessionContext.isCallerInRole(Role.CUSTOMER.toString()));
        
        if(sessionContext.isCallerInRole(Role.CUSTOMER.toString())){
            customer = (Customer) em.createQuery("Select c From Customer c Where c.email = :email").setParameter("email", callerPrincipal.getName()).getSingleResult();

        }
        logger.info(customer);
        return customer;
    }
    
    
    
    
}
