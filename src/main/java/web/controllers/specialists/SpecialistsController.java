/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controllers.specialists;

import app.availabilities.entity.Availability;
import app.specialists.boundary.AllSpecialists;
import app.specialists.boundary.AuthenticatedSpecialist;
import app.specialists.boundary.SpecialistsEndpoint;
import app.specialists.entity.Specialist;
import java.util.List;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author marcin
 */
@Model
public class SpecialistsController {
    
    @Inject @AllSpecialists
    private List<Specialist> specialists;
    
    @Inject
    SpecialistsEndpoint specialistsEndpoint;
    
    private Availability availablity = new Availability();
    
    @Inject @AuthenticatedSpecialist
    private Specialist specialist;

    public List<Specialist> getSpecialists() {
        return specialists;
    }

    public Specialist getSpecialist() {
        return specialist;
    }
    
    
    public void addAvailability(){
        specialistsEndpoint.create(availablity);
    }

    public Availability getAvailablity() {
        return availablity;
    }

    public void setAvailablity(Availability availablity) {
        this.availablity = availablity;
    }
    
    
    
    
}
