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
        String newName = "user2";
        Member member = createMember("user1");
        Long id = member.getId();
        member.setUsername(newName);
        mergeMember(member);

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        Member member3 = em.find(Member.class, id);
        tx.commit();
        assertEquals(member3.getUsername(), newName);

        tx.begin();
        em.remove(member3);
        tx.commit();

        em.close();
    }

    public Member createMember(String username) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Member member = new Member();
        member.setUsername(username);
        member.setAge(12);

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
        Member member = new Member();
        member.setUsername("hahahaha");
        member.setFirstName("Yun");
        member.setLastName("Min");
        member.setAge(2);

        em.persist(member);
        Long id = member.getId();
        System.out.println("create member");

        member.setAge(12);
        member.setUsername("YuYu");
        member.setFirstName("Yun1");

        Member foundMember = em.find(Member.class, id);
        System.out.println("foundMember.getUsername()  = " + foundMember.getUsername());
        System.out.println("foundMember.getAge() = " + foundMember.getAge());
        System.out.println("foundMember.getFullName() = " + foundMember.getFullName());
        foundMember.setUsername("1234");
        foundMember.setFirstName("Good");

        System.out.println("before JPQL");
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("after JPQL");
        System.out.println("member.size() = " + members.size());

        em.remove(member);
        System.out.println("remove member");
    }

}