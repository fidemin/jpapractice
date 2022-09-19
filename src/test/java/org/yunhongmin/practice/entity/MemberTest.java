package org.yunhongmin.practice.entity;

import org.junit.jupiter.api.Test;
import org.yunhongmin.EntityManagerFactoryManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberTest {
    EntityManagerFactory emf = EntityManagerFactoryManager.getEntityManagerFactory("jpapractice");

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

    @Test
    public void team() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team1 = new Team();
        team1.setName("center");
        em.persist(team1);

        Member member1 = new Member();
        member1.setUsername("a1");
        member1.setTeam(team1);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("a1");
        member2.setTeam(team1);
        em.persist(member2);

        em.remove(member2);
        em.remove(member1);
        em.remove(team1);

        tx.commit();
        em.close();
    }

    @Test
    public void teamJPQL() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team1 = new Team();
        team1.setName("center");
        em.persist(team1);

        Member member1 = new Member();
        member1.setUsername("a1");
        member1.setTeam(team1);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("a2");
        member2.setTeam(team1);
        em.persist(member2);

        String jpql = "select m from Member m join m.team t where t.name = :teamName";
        List<Member> members = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "center").getResultList();

        assertEquals(2, members.size());

        Team team2 = new Team();
        team2.setName("team2");
        em.persist(team2);

        member1.setTeam(team2);
        List<Member> members2 = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "center").getResultList();

        assertEquals(1, members2.size());

        member2.setTeam(null);
        List<Member> members3 = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "center").getResultList();

        assertEquals(0, members3.size());


        em.remove(member2);
        em.remove(member1);
        em.remove(team1);
        em.remove(team2);

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