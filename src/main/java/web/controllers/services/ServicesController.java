/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controllers.services;

import app.availabilities.entity.AvailabilityTime;
import app.services.entity.Service;
import app.services.boundary.ServicesEndpoint;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author marcin
 */
@Named
@RequestScoped
public class ServicesController implements Serializable {

    @Inject
    ServicesEndpoint servicesEndpoint;
    @Inject
    Conversation conversation;
    private Long id;
    private Service service;
    private List<AvailabilityTime> times;

    public List<Service> getServices() {
        return servicesEndpoint.findAll();
    }

    public void findService() {

        this.service = servicesEndpoint.findService(id);
        this.times = service.produceAvailabilityTimes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
    
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<AvailabilityTime> getTimes() {
        return times;
    }

}
