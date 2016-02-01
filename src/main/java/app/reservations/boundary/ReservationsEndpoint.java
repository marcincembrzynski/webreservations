/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reservations.boundary;

import app.availabilities.entity.Availability;
import app.availabilities.entity.AvailabilityTime;
import app.customers.boundary.AuthenticatedCustomer;
import app.customers.boundary.CustomerReservationEndpoint;
import app.customers.entity.Customer;
import app.payments.boundary.PaypalPaymentProvider;
import app.payments.entity.PaypalResponse;
import app.payments.entity.ReservationPayment;
import app.reservations.entity.Reservation;
import app.specialists.entity.Specialist;
import com.paypal.base.rest.PayPalRESTException;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.logging.Logger;

/**
 *
 * @author marcin
 */
@Stateless
@DeclareRoles(value = {"CUSTOMER"})
public class ReservationsEndpoint {
    
    private final static Logger logger = Logger.getLogger(ReservationsEndpoint.class);

    @PersistenceContext
    EntityManager em;

    @Inject
    CustomerReservationEndpoint customerReservationEndpoint;
    
    @Inject @AuthenticatedCustomer
    private Customer customer;

    @EJB
    PaypalPaymentProvider paypalPaymentProvider;

    /**
     * initialize paypal payment
     *
     * @return
     */
    
    @RolesAllowed(value = {"CUSTOMER"})
    public ReservationPayment payWithPaypal() {

        ReservationPayment reservationPayment = paypalPaymentProvider.initializePayment(customerReservationEndpoint.getAvailabilityTime());
       
        Reservation reservation = new Reservation(customerReservationEndpoint.getAvailabilityTime(), reservationPayment, customer);

        
        em.persist(reservation);
        
        customer.addReservation(reservation);
        
        em.merge(customer);

        logger.info(reservationPayment);
        logger.info(customer);

        return reservationPayment;
    }

    /**
     *
     * @param paypalResponse
     * @return
     * @throws PayPalRESTException
     */
    @RolesAllowed(value = {"CUSTOMER"})
    public Optional<Reservation> processPaypalResponse(PaypalResponse paypalResponse) throws PayPalRESTException {
        
        logger.info(paypalResponse);
        logger.info(customer);
     

        Optional<Reservation> optionalReservation = customer.getReservations().stream()
                .filter(r -> r.getReservationPayment().getPaymentId().equals(paypalResponse.getPaymentId())).findFirst();
        
        logger.info(optionalReservation);

        optionalReservation.ifPresent((Reservation reservation) -> {

            ReservationPayment reservationPayment = reservation.getReservationPayment();
            reservationPayment.setPayerId(paypalResponse.getPayerId());
            Specialist specialist = reservation.getSpecialist();

            specialist.checkIfAvailabilityExists(customerReservationEndpoint.getAvailabilityTime()).ifPresent((Availability availability) -> {
                
                ReservationPayment updatedReservationPayment = paypalPaymentProvider.executePayment(reservationPayment);
                logger.info(availability);
                if(updatedReservationPayment.getSuccessful()){
                    
                    List<Availability> updatedAvailabilities = specialist.splitAvailability(availability, customerReservationEndpoint.getAvailabilityTime());
                    logger.info("#### updatedAvailabilities size: " + updatedAvailabilities.size() );
                    specialist.getAvailabilities().remove(availability);
                    logger.info("#### specialist.getAvailabilities().size() after remove: " + specialist.getAvailabilities().size() );
                    updatedAvailabilities.forEach(a -> em.persist(a));
                    specialist.addAvailabilities(updatedAvailabilities);
                    logger.info("#### specialist.getAvailabilities().size() after addAvailabilities: " + specialist.getAvailabilities().size() );
                    em.merge(specialist);
                    customerReservationEndpoint.setAvailabilityTime(new AvailabilityTime());
                    
                }
                
                // updated availability
               
            });
            
            
         
            
            logger.info(reservation);
            logger.info(customer);

        });
        em.merge(customer);
        
        return optionalReservation;

    }

   

}
