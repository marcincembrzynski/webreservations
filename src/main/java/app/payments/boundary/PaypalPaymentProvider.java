/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.payments.boundary;



import app.availabilities.entity.AvailabilityTime;
import app.config.ConfigProperties;
import app.payments.entity.ReservationPayment;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;


/**
 *
 * @author marcin
 */

@Stateless
public class PaypalPaymentProvider{

    @Inject @ConfigProperties
    private Map<String,String> config;
    private final static String CLIENT_ID = "CLIENT_ID";
    private final static String CLIENT_SECRET = "CLIENT_SECRET";
    private final static String CANCEL_URL = "CANCEL_URL";
    private final static String RETURN_URL = "RETURN_URL";
    private Map<String, String> sdkConfig;
    
    
    @PostConstruct 
    private void init(){
        sdkConfig = new HashMap<>();
        sdkConfig.put("mode", "sandbox");
    }

  
    public ReservationPayment initializePayment(AvailabilityTime availabilityTime) {
        
        try {
            
            
            String accessToken = new OAuthTokenCredential(config.get(CLIENT_ID), config.get(CLIENT_SECRET), sdkConfig).getAccessToken();
            
            APIContext apiContext = new APIContext(accessToken);
            apiContext.setConfigurationMap(sdkConfig);
            
            Amount amount = new Amount("GBP", availabilityTime.getService().getPrice().toString());
            Transaction transaction = new Transaction();
            transaction.setDescription(availabilityTime.info());
            transaction.setAmount(amount);
         
            
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            
            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");
           
            Payment payment = new Payment();
            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setTransactions(transactions);
           
          
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(config.get(CANCEL_URL));
            redirectUrls.setReturnUrl(config.get(RETURN_URL));
            payment.setRedirectUrls(redirectUrls);
            
            Payment createdPayment = payment.create(apiContext);
            List<Links> links = createdPayment.getLinks();
            
            Optional<String> appprovalUrl = createdPayment.getLinks().stream().filter(link -> "approval_url".equals(link.getRel())).map(e -> e.getHref()).findFirst();
            System.out.println("Payment id: " + createdPayment.getId());
          
            return new ReservationPayment(appprovalUrl.get(), createdPayment.getId(), availabilityTime.getService().getPrice());
          
            
            
            
        } catch (PayPalRESTException ex) {
            Logger.getLogger(PaypalPaymentProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ReservationPayment();

    }

   
    public ReservationPayment executePayment(ReservationPayment reservationPayment){
       
        
        try {
            Map<String, String> sdkConfig = new HashMap<>();
            sdkConfig.put("mode", "sandbox");
            
            String accessToken = new OAuthTokenCredential(config.get(CLIENT_ID), config.get(CLIENT_SECRET), sdkConfig).getAccessToken();
            APIContext apiContext = new APIContext(accessToken);
            apiContext.setConfigurationMap(sdkConfig);
            
            Payment payment = new Payment();
            payment.setId(reservationPayment.getPaymentId());
            
            
            
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(reservationPayment.getPayerId());
            Payment executedPayment = payment.execute(apiContext, paymentExecute);
            
            boolean paid = executedPayment.getState().equals("approved");
            reservationPayment.setSuccessful(paid);
           
           
            System.out.println("paid...: " + paid);
            
        
            return reservationPayment;
        } catch (PayPalRESTException ex) {
            Logger.getLogger(PaypalPaymentProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return reservationPayment;

    }

   



}
