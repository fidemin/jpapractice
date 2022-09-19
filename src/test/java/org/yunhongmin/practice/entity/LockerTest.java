package org.yunhongmin.practice.entity;

import org.junit.jupiter.api.Test;
import org.yunhongmin.EntityManagerFactoryManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.*;

class LockerTest {
    @Test
    public void saveLocker() {
        EntityManagerFactory emf = EntityManagerFactoryManager.getEntityManagerFactory("jpapractice");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member();
        member.setUsername("member1");
        member.setAge(20);
        em.persist(member);

        Locker locker = new Locker();
        locker.setMember(member);
        em.persist(locker);

        tx.commit();
        em.close();

        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        Locker locker1 = em.find(Locker.class, locker.getId());
        assertEquals(member.getId(), locker1.getMember().getId());

        tx.commit();
        em.close();


    }

}