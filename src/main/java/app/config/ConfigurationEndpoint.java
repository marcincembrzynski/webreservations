/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.config;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marcin
 */
@Singleton
@Startup
public class ConfigurationEndpoint {

    @PersistenceContext
    private EntityManager em;

    private Map<String, String> properties = new HashMap<>();
    
    @Resource(name = "env")
    private String env;

    @PostConstruct
    private void init() {
        
    }

    @Produces
    @ConfigProperties
    public Map<String, String> getProperties() {
        
        if(properties.isEmpty()){
            ConfigProperty c = em.createQuery("Select c from ConfigProperty c Where c.environment = :env ", ConfigProperty.class).setParameter("env", Environment.valueOf(env)).getSingleResult();
            properties = c.getProperties();
        }

        return properties;
    }

    public void create(ConfigProperty configProperty) {
        em.persist(configProperty);
    }

}
