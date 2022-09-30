package org.yunhongmin.practice.entity;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

import static org.junit.Assert.assertEquals;


class TeamV2Test {
    private final EntityManagerFactory emf = EntityManagerFactoryManager.getEntityManagerFactory("jpapractice");

    @Test
    public void oneToMany() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        TeamV2 team = new TeamV2();
        team.setName("team1");

        Member member1 = new Member();
        member1.setUsername("member1");
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("member2");
        em.persist(member2);

        List<Member> members = team.getMembers();
        members.add(member1);
        members.add(member2);
        em.persist(team);

        tx.commit();
        em.close();

        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
        TeamV2 team1 = em.find(TeamV2.class, team.getId());
        members = team1.getMembers();
        assertEquals(2, members.size());

        tx.commit();
        em.close();

        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
        Member member = em.find(Member.class, member1.getId());
        assertEquals(team.getId(), member.getTeamV2().getId());

        tx.commit();
        em.close();
    }

}