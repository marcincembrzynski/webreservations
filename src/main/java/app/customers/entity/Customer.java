/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.customers.entity;

import app.actors.entity.Actor;
import app.actors.entity.Role;
import app.reservations.entity.Reservation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author marcin
 */
@Entity
public class Customer extends Actor implements Serializable {
    
    private static final long serialVersionUID = 1L;
   
   
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reservation> reservations;
    
   

    public Customer() {
         this.actorRole = Role.CUSTOMER;
    }

    public Customer(String email, String firstName, String lastName, String password) {
        super(email, firstName, lastName, password);
        this.actorRole = Role.CUSTOMER;
    }
    
    public void addReservation(Reservation reservation){
        if(null == reservations){
            reservations = new ArrayList<>();
        }
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    

    @Override
    public String toString() {
        return "Actor{" + "id=" + id + ", email=" + email 
                + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password 
                + ", actorRole=" + actorRole + '}' + "Customer{" + "reservations=" + reservations + '}';
    }
    
    

    
    
    
}
