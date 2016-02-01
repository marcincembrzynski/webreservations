/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.specialists.entity;

import app.availabilities.entity.Availability;
import app.availabilities.entity.AvailabilityTime;
import app.services.entity.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.jboss.logging.Logger;
import static org.junit.Assert.*;

/**
 *
 * @author marcin
 */
public class SpecialistTest {
    
    private final static Logger logger = Logger.getLogger(SpecialistTest.class);
    
    public SpecialistTest() {
    }
    
   
    @org.junit.Test
    public void testSplitAvailability1() {
        Specialist specialist1 = new Specialist("james@bond.co.uk", "James", "Bond", "qwerty");
        Service service1 = new Service("Service 1", BigDecimal.TEN, (long) 60);
        LocalDate date2 = LocalDate.now().plusDays(2);
        Availability availability = new Availability(LocalDateTime.of(date2, LocalTime.of(9, 50)), LocalDateTime.of(date2, LocalTime.of(14,1)));
        
        specialist1.addAvailability(availability);
        AvailabilityTime availabilityTime = new AvailabilityTime(service1, specialist1, LocalDateTime.of(date2, LocalTime.of(12, 50)));
        List<Availability> splitAvailability = specialist1.splitAvailability(availability, availabilityTime);
        assertEquals(2, splitAvailability.size());
        specialist1.getAvailabilities().remove(availability);
        specialist1.addAvailabilities(splitAvailability);
        logger.info(splitAvailability.toString());
         assertEquals(2, specialist1.getAvailabilities().size());
        
    }
    
    @org.junit.Test
    public void testSplitAvailability2() {
        
        Specialist specialist1 = new Specialist("james@bond.co.uk", "James", "Bond", "qwerty");
        Service service1 = new Service("Service 1", BigDecimal.TEN, (long) 60);
        LocalDate date2 = LocalDate.now().plusDays(2);
        Availability availability = new Availability(LocalDateTime.of(date2, LocalTime.of(9, 50)), LocalDateTime.of(date2, LocalTime.of(14,1)));
        
        specialist1.addAvailability(availability);
        AvailabilityTime availabilityTime = new AvailabilityTime(service1, specialist1, LocalDateTime.of(date2, LocalTime.of(9, 50)));
        List<Availability> splitAvailability = specialist1.splitAvailability(availability, availabilityTime);
        assertEquals(1, splitAvailability.size());
        logger.info(splitAvailability.toString());
        
    }

    /**
     * Test of checkIfAvailabilityExists method, of class Specialist.
     */
    @org.junit.Test
    public void testCheckIfAvailabilityExists() {
        
    }

   
    
}
