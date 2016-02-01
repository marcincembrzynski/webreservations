/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.services.entity;

import app.availabilities.entity.AvailabilityTime;
import app.specialists.entity.Specialist;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@XmlRootElement(name="service")
@XmlAccessorType(XmlAccessType.FIELD)
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private BigDecimal price;
    private Long duration;
    @XmlTransient
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Specialist> specialists = new HashSet<>();

    public Service() {
      
    }
    
    
    
    public Service(Long id, String name, BigDecimal price, Long duration) {
      
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
    }
    
    public Service(String name, BigDecimal price, Long duration) {
     
      
        this.name = name;
        this.price = price;
        this.duration = duration;
    }
    
    public void addSpecialist(Specialist specialist1) {
        specialists.add(specialist1);
    }
    
   
    
     /**
     * 
     * @param date
     * @return availabilityTimes for date 
     */
    public List<AvailabilityTime> getAvailablityTimesForDate(LocalDate date){
       
      return specialists.stream()
                .flatMap(s -> s.getAvailabilityTimesforDate(this, date)
                        .stream()).collect(Collectors.toList());
      
    }
    
    /**
     * 
     * @return availibilityTimes for Service 
     */
    
    public List<AvailabilityTime> produceAvailabilityTimes(){
        
        return specialists.stream()
                .flatMap(s -> s.getAvailabilityTimes(this)
                        .stream()).collect(Collectors.toList());
    }
    
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Set<Specialist> getSpecialists() {
        return specialists;
    }

    public void setSpecialists(Set<Specialist> specialists) {
        this.specialists = specialists;
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
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.services.entity.Service[ id=" + id + " ]";
    }

  
    
}
