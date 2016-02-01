/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.specialists.entity;

import app.availabilities.entity.Availability;
import app.availabilities.entity.AvailabilityTime;
import app.actors.entity.Actor;
import app.actors.entity.Role;
import app.reservations.entity.Reservation;
import app.services.entity.Service;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author marcin
 */
@Entity
@XmlRootElement(name="specialist")
@XmlAccessorType(XmlAccessType.FIELD)
public class Specialist extends Actor implements Serializable {
    
    private static final long serialVersionUID = 1L;
   
    
    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Availability> availabilities = new TreeSet<>();
    
    @XmlTransient
    @ManyToMany(mappedBy = "specialists", fetch = FetchType.EAGER)
    private List<Service> services;
    
    @XmlTransient
    @OneToMany(mappedBy = "specialist", fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    public Specialist() {
      this.actorRole = Role.SPECIALIST;
    }

    public Specialist(String email, String firstName, String lastName, String password) {
        super(email, firstName, lastName, password);
        this.actorRole = Role.SPECIALIST;
    }
    
    
    
    

    
    public void addAvailability(Availability availability){
        
        availabilities.add(availability);
    }
    
    public void addAvailabilities(List<Availability> as){
        as.forEach(a -> this.addAvailability(a));
    }
    
    public List<AvailabilityTime> getAvailabilityTimesforDate(Service service, LocalDate date){
        return getAvailabilityTimes(service).stream().filter(a -> a.getLocalDate().equals(date)).collect(Collectors.toList());
        
    }
    
    public List<AvailabilityTime> getAvailabilityTimes(Service service){
        List<AvailabilityTime> list = availabilities.stream()
                .flatMap(a -> a.getAvailabilityTimesForService(service, this).stream())
                .filter(a -> a.getLocalDateTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
        return list;
    }
    
     
      /**
     * *
     *
     * @param availability
     * @param availabilityTime
     * @return
     */
    public List<Availability> splitAvailability(Availability availability, AvailabilityTime availabilityTime) {

        List<Availability> list = new ArrayList<>();
        //-----
        //----------
        if (availabilityTime.getTo().isBefore(availability.getToTime())) {
            Availability a = new Availability(availabilityTime.getTo(), availability.getToTime());
            list.add(a);

        }
        //     -----
        //----------
        if (availabilityTime.getLocalDateTime().isAfter(availability.getFromTime())) {
            Availability a = new Availability(availability.getFromTime(), availabilityTime.getLocalDateTime());
            list.add(a);
        }

        return list;
    }
    
    
    /**
     * *
     * checks if availability still exists for given availablityTime
     *
     * @param availabilityTime
     * @return
     */
    public Optional<Availability> checkIfAvailabilityExists(AvailabilityTime availabilityTime) {

        return availabilities.stream()
                .filter(a
                        -> (availabilityTime.getLocalDateTime().isEqual(a.getFromTime()) && availabilityTime.getTo().isEqual(a.getToTime()))
                        || (availabilityTime.getLocalDateTime().isAfter(a.getFromTime()) && availabilityTime.getTo().isEqual(a.getToTime()))
                        || (availabilityTime.getLocalDateTime().isEqual(a.getFromTime()) && availabilityTime.getTo().isBefore(a.getToTime()))
                        || (availabilityTime.getLocalDateTime().isAfter(a.getFromTime()) && availabilityTime.getTo().isBefore(a.getToTime()))
                ).findFirst();
    }



    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
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
        if (!(object instanceof Specialist)) {
            return false;
        }
        Specialist other = (Specialist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  "Specialist{ " + firstName + " " + lastName + " " + "availabilities=" + availabilities + ", services=" + services + '}';
    }

  
    
    
}
