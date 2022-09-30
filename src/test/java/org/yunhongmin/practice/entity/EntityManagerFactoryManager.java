package org.yunhongmin.practice.entity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;

public class EntityManagerFactoryManager {
    private static final HashMap<String, EntityManagerFactory> emfMap = new HashMap<>();

    static {
        emfMap.put("jpapractice", Persistence.createEntityManagerFactory("jpapractice"));
//        emfMap.put("shop", Persistence.createEntityManagerFactory("shop"));
    }

    public static EntityManagerFactory getEntityManagerFactory(String persistentUnit) {
        EntityManagerFactory emf = emfMap.get(persistentUnit);
        if (emf == null) {
            throw new RuntimeException("no key") ;
        }
        return emf;
    }
}
