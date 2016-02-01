/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controllers.specialists.converters;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.jboss.logging.Logger;

/**
 *
 * @author marcin
 */
@FacesConverter(forClass = LocalDateTime.class)
public class LocalDateTimeConverter implements Converter{
    
    private static final Logger logger = Logger.getLogger(LocalDateTimeConverter.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        logger.info(value);
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
