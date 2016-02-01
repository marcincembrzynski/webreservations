/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controllers.reservations;


import app.payments.entity.ReservationPayment;
import app.reservations.boundary.ReservationsEndpoint;
import java.io.IOException;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author marcin
 */
@Model
public class ReservationsController {
    
    
    @Inject 
    ReservationsEndpoint reservationsEndpoint;
    
    
    public void payWithPaypal() throws IOException{
       
        ReservationPayment reservationPayment = reservationsEndpoint.payWithPaypal();
        FacesContext.getCurrentInstance().getExternalContext().redirect(reservationPayment.getRedirectUrl());
        
    }
}
