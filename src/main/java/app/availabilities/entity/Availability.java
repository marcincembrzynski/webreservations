/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.availabilities.entity;

import app.services.entity.Service;
import app.specialists.entity.Specialist;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author marcin
 */
@Entity
public class Availability implements Serializable, Comparable<Availability>{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private LocalDateTime fromTime;
   
    @NotNull
    private LocalDateTime toTime;

    public Availability() {
    }

    public Availability(LocalDateTime fromTime, LocalDateTime toTime) {
        this.fromTime = fromTime;
        this.toTime = toTime;
    }
    
    
    public LocalDate getLocalDate(){
        return LocalDate.of(fromTime.getYear(), fromTime.getMonth(), fromTime.getDayOfMonth());
    }
    
    public List<AvailabilityTime> getAvailabilityTimesForService(Service service, Specialist specialist){
        List<AvailabilityTime> list = new ArrayList<>();
        LocalDateTime time = LocalDateTime.from(fromTime);
        
       
        while((time.plusMinutes(service.getDuration()).isBefore(toTime)) || (time.plusMinutes(service.getDuration())).isEqual(toTime)){
            //service.setSpecialists(new HashSet<>());
            //specialist.setAvailabilities(new TreeSet<>());
            list.add(new AvailabilityTime(service, specialist, time));
            time = time.plusMinutes(service.getDuration());
        }
        return list;
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime toTime) {
        this.toTime = toTime;
    }

   
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Availability)) {
            return false;
        }
        Availability other = (Availability) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Availability{" + "id=" + id + ", fromTime=" + fromTime + ", toTime=" + toTime + '}';
    }

    

    @Override
    public int compareTo(Availability other) {
        return fromTime.compareTo(other.fromTime);
    }
    
}
