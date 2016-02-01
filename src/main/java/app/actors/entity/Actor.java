/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.actors.entity;

import app.actors.entity.constraints.Email;
import app.actors.entity.constraints.ActorName;
import app.actors.entity.constraints.Password;
import app.actors.entity.constraints.Phone;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 *
 * @author marcin
 */
@Entity
@NamedQuery(name = "findActorByEmail", query = "Select a From Actor a Where a.email = :email")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Actor implements Serializable {
  
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
  
    @Column(unique = true)
    @Email
    protected String email;
    
    @ActorName
    protected String firstName;
    
    @ActorName
    protected String lastName;
    
    protected String password;
    
    @Transient
    @Password
    protected String passwordForm;
    
    @Phone
    protected String phone;
    
    @Enumerated(EnumType.STRING)
    protected Role actorRole;
    

    public Actor() {
    }

    
    
    

    public Actor(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordForm = password;
    }

    
    
      public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
  


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getActorRole() {
        return actorRole;
    }

    public void setActorRole(Role actorRole) {
        this.actorRole = actorRole;
    }

    public String getPasswordForm() {
        return passwordForm;
    }

    public void setPasswordForm(String passwordForm) {
        this.passwordForm = passwordForm;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Actor other = (Actor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Actor{" + "id=" + id + ", email=" + email 
                + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", actorRole=" + actorRole + '}';
    }

    

    
    
}
