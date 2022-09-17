package org.yunhongmin.entity;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpapractice");

    @Test
    public void basic() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            createRetrieveDeleteMember(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Exception = " + e);
        } finally {
            em.close();
        }

        emf.close();
    }

    @Test
    public void detachAndMerge() {
        String id = "memberA";
        String newName = "user2";
        Member member = createMember(id, "user1");
        member.setName(newName);
        mergeMember(member);

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        Member member3 = em.find(Member.class, id);
        tx.commit();
        assertEquals(member3.getName(), newName);
    }

    public Member createMember(String id, String name) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Member member = new Member(id, name, 12);

        tx.begin();
        em.persist(member);
        tx.commit();

        em.close();
        return member;
    }

    public Member mergeMember(Member member) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        Member mergedMember = em.merge(member);
        tx.commit();

        em.close();
        return mergedMember;
    }

    private void createRetrieveDeleteMember(EntityManager em) {
        String id = "1";
        Member member = new Member();
        member.setId(id);
        member.setName("Yunhong");
        member.setAge(2);

        em.persist(member);
        System.out.println("create member");

        member.setAge(12);
        member.setName("YuYu");

        Member foundMember = em.find(Member.class, id);
        System.out.println("foundMember.getName()  = " + foundMember.getName());
        System.out.println("foundMember.getAge() = " + foundMember.getAge());

        System.out.println("before JPQL");
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("after JPQL");
        System.out.println("member.size() = " + members.size());

        em.remove(member);
        System.out.println("remove member");
    }

}