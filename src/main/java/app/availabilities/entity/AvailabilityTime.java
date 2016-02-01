/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.availabilities.entity;

import app.services.entity.Service;
import app.specialists.entity.Specialist;
import com.airhacks.LocalDateTimeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author marcin
 */

@XmlRootElement(name="availabilityTime")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailabilityTime implements Serializable {
    
   
    private Service service;
   
    private Specialist specialist;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime localDateTime;

    public AvailabilityTime() {
    }
    
    

    public AvailabilityTime(Service service, Specialist specialist, LocalDateTime localDateTime) {
        this.service = service;
        this.specialist = specialist;
        this.localDateTime = localDateTime;
    }
    
    

    public AvailabilityTime(AvailabilityTime at) {
        this.service = at.getService();
        this.specialist = at.getSpecialist();
        this.localDateTime = at.getLocalDateTime();
    }

   

    public void setService(Service service) {
        this.service = service;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
    
    
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Service getService() {
        return service;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    
    public LocalDateTime getTo(){
        return localDateTime.plusMinutes(service.getDuration());
    }
    
     public LocalDate getLocalDate(){
        return LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.service);
        hash = 97 * hash + Objects.hashCode(this.specialist);
        hash = 97 * hash + Objects.hashCode(this.localDateTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AvailabilityTime other = (AvailabilityTime) obj;
        if (!Objects.equals(this.service, other.service)) {
            return false;
        }
        if (!Objects.equals(this.specialist, other.specialist)) {
            return false;
        }
        if (!Objects.equals(this.localDateTime, other.localDateTime)) {
            return false;
        }
        return true;
    }

    public String getServiceId() {
        return service.getId().toString();
    }

    public String getSpecialistId() {
        return specialist.getId().toString();
    }
    
    
    public String printTime(){
        return "test";
    }

    
    

    @Override
    public String toString() {
        return "AvailabilityTime{" + "service=" + service + ", specialist=" + specialist + ", localDateTime=" + localDateTime + '}';
    }
    
    public String info(){
        return "Reservation: " + service.getName() 
                + ", " + specialist.getFirstName() + " " + specialist.getLastName()
                + ", " + localDateTime
                + ", " + service.getDuration();
    }

   
     
     
    
    
}
