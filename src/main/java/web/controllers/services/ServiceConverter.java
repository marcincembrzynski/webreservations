/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controllers.services;

import app.services.boundary.ServicesEndpoint;
import app.services.entity.Service;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author marcin
 */
@FacesConverter(forClass = Service.class)
public class ServiceConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
         Service service = new Service();
        try {
            ServicesEndpoint servicesEndpoint = (ServicesEndpoint) InitialContext.doLookup("java:module/ServicesEndpoint");
            service = servicesEndpoint.findService((new Long(value)));
          
        } catch (NamingException ex) {
            Logger.getLogger(ServiceConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return service;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Service) value).getId().toString();
    }
    
}
