/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controllers.reservations;

import app.payments.entity.PaypalResponse;
import app.reservations.boundary.ReservationsEndpoint;
import app.reservations.entity.Reservation;
import com.paypal.base.rest.PayPalRESTException;
import java.util.Optional;
import org.jboss.logging.Logger;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author marcin
 */

@Model
public class PaypalResponseController {
    
    private final static Logger logger = Logger.getLogger(PaypalResponseController.class);
    
    private String paymentId;
    private String payerId;
    
    @Inject
    ReservationsEndpoint reservationEndpoint;
    
    
    public String processResponse() throws PayPalRESTException{
        
        Optional<Reservation> reservation = reservationEndpoint.processPaypalResponse(new PaypalResponse(payerId, paymentId));
        //logger.info(reservation);
        
        if(reservation.isPresent() && reservation.get().getReservationPayment().getSuccessful()){
            return "/secure/payment/confirmation.html";
        }else{
            return "/secure/payment/paymentError.html";
        }
        
        
        
        
        
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }
    
    
    
}
