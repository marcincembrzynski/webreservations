/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.payments.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author marcin
 */
@Entity
public class ReservationPayment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String paymentId;
    private String redirectUrl;
    private String payerId;
    private BigDecimal amount;
    private Boolean successful = false;
    private String message;
    
    public ReservationPayment() {
    }

    public ReservationPayment(String redirectUrl, String paymentId) {
        this.paymentId = paymentId;
        this.redirectUrl = redirectUrl;
    }

    public ReservationPayment(BigDecimal amount) {
        this.amount = amount;
    }

    public ReservationPayment(String redirectUrl, String paymentId, BigDecimal amount) {
        this.redirectUrl = redirectUrl;
        this.paymentId = paymentId;
        this.amount = amount;
    }
    
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservationPayment)) {
            return false;
        }
        ReservationPayment other = (ReservationPayment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReservationPayment{" + "id=" + id + ", paymentId=" + paymentId + ", redirectUrl=" + redirectUrl + ", payerId=" + payerId + ", amount=" + amount + ", successful=" + successful + ", message=" + message + '}';
    }

    

    
    
}
