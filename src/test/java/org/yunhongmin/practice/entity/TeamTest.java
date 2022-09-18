package org.yunhongmin.practice.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;


class TeamTest {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpapractice");

    @Test
    public void biDirection() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team = new Team();
        team.setName("team1");
        em.persist(team);

        Member member1 = new Member();
        member1.setUsername("member1");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("member2");
        member2.setTeam(team);
        em.persist(member2);

        tx.commit();
        em.close();

        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        team = em.find(Team.class, team.getId());
        System.out.println("team found");
        List<Member> members = team.getMembers();
        System.out.println("members found");

        for (Member member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
        }
        Assertions.assertEquals(2, members.size());

        tx.commit();
        em.close();
    }

}