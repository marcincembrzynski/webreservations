/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reservations.entity;

import app.availabilities.entity.AvailabilityTime;
import app.customers.entity.Customer;
import app.payments.entity.ReservationPayment;
import app.services.entity.Service;
import app.specialists.entity.Specialist;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author marcin
 */
@Entity
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private BigDecimal price;
    
    private LocalDateTime created;
    
    
    @OneToOne(cascade = CascadeType.ALL)
    private ReservationPayment reservationPayment;
 
    @ManyToOne
    private Customer customer;
    
    @ManyToOne
    private Service service;
    
    @ManyToOne
    private Specialist specialist;
    
    private LocalDateTime appointmentTime;

    public Reservation() {
    }
    
    
    

    public Reservation(AvailabilityTime availabilityTime, ReservationPayment reservationPayment, Customer customer) {
     
        this.price = availabilityTime.getService().getPrice();
        this.created = LocalDateTime.now();
        this.reservationPayment = reservationPayment;
        this.customer = customer;
        this.specialist = availabilityTime.getSpecialist();
        this.service = availabilityTime.getService();
        this.appointmentTime = availabilityTime.getLocalDateTime();
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public ReservationPayment getReservationPayment() {
        return reservationPayment;
    }

    public void setReservationPayment(ReservationPayment reservationPayment) {
        this.reservationPayment = reservationPayment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
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
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", price=" + price + ", created=" + created + ", reservationPayment=" + reservationPayment + ", customer=" + customer.getId() + ", service=" + service.getId() + ", specialist=" + specialist.getId() + ", appointmentTime=" + appointmentTime + '}';
    }

    

    
    
    
}
