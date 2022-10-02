package entity;

import org.junit.Test;
import org.yunhongmin.practice.entity.Locker;
import org.yunhongmin.practice.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.junit.Assert.*;

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

        Locker locker1 = em.find(Locker.class, locker.getId());
        assertEquals(member.getId(), locker1.getMember().getId());

        tx.rollback();
        em.close();
    }

}