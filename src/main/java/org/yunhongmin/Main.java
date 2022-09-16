package org.yunhongmin;

import org.yunhongmin.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpapractice");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            createRetrieveDeleteMember(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Exception = " + e.toString());

        } finally {
            em.close();
        }

        emf.close();
    }

    private static void createRetrieveDeleteMember(EntityManager em) {
        String id = "1";
        Member member = new Member();
        member.setId(id);
        member.setName("Yunhong");
        member.setAge(2);

        em.persist(member);

        member.setAge(12);

        Member foundMember = em.find(Member.class, id);
        System.out.println("foundMember.getName()  = " + foundMember.getName());
        System.out.println("foundMember.getAge() = " + foundMember.getAge());

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("member.size: " + members.size());

        em.remove(member);
    }
}