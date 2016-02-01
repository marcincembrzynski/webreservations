/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.payments.entity;

/**
 *
 * @author marcin
 */
public class PaypalResponse {
    
    private final String payerId;
    private final String paymentId;

    public PaypalResponse(String payerId, String paymentId) {
        this.payerId = payerId;
        this.paymentId = paymentId;
    }

    public String getPayerId() {
        return payerId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    @Override
    public String toString() {
        return "PaypalResponse{" + "payerId=" + payerId + ", paymentId=" + paymentId + '}';
    }
    
    
}
