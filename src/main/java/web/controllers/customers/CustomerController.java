/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controllers.customers;

import app.actors.boundary.ActorsEndpoint;
import app.availabilities.entity.AvailabilityTime;
import app.customers.boundary.AllCustomers;
import app.customers.boundary.AuthenticatedCustomer;
import app.customers.boundary.CustomerReservationEndpoint;
import app.customers.entity.Customer;
import app.result.Result;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.jboss.logging.Logger;

/**
 *
 * @author marcin
 */

@Model
public class CustomerController implements Serializable{
    
    
    private static final Logger logger = Logger.getLogger(CustomerController.class);
    
    @Inject
    CustomerReservationEndpoint customerReservationEndpoint;
    
    @Inject @AuthenticatedCustomer
    Customer authenticatedCustomer;
    private Result result;
    
  
    
    @Inject
    ActorsEndpoint actorsEndpoint;
    
    @Inject @AllCustomers
    private List<Customer> allCustomers;
    
    
    private Customer newCustomer = new Customer();
    
    private AvailabilityTime availabilityTime;
    
    public String registerCustomer() throws ServletException{
        this.result = actorsEndpoint.register(newCustomer);
        this.newCustomer = (Customer) result.element();
        logger.info(newCustomer);
        
        if(result.success()){
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("result", result);
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.login(newCustomer.getEmail(), newCustomer.getPasswordForm());
            return "/secure/account/index?faces-redirect=true";
        }
        // flash attribute;
        return "register";
    }
    
    

    public AvailabilityTime getAvailabilityTime() {
        return customerReservationEndpoint.getAvailabilityTime();
    }

    public Customer getAuthenticatedCustomer() {
        return authenticatedCustomer;
    }

    public Customer getNewCustomer() {
       
        return newCustomer;
    }

    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }

    public List<Customer> getAllCustomers() {
        return allCustomers;
    }

    public Result getResult() {
        return result;
    }
    
    
    
    
    
}
