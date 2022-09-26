package org.yunhongmin.practice.entity;

import org.junit.jupiter.api.Test;
import org.yunhongmin.EntityManagerFactoryManager;
import org.yunhongmin.practice.dto.MemberDto;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void proxy() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team = new Team();
        team.setName("H's room");
        em.persist(team);

        Member member = new Member();
        String username = "yunhongmin";
        member.setUsername(username);
        member.setTeam(team);


        em.persist(member);

        tx.commit();
        em.close();

        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        Member foundMember1 = em.getReference(Member.class, member.getId());
        assertFalse(em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(foundMember1));
        assertEquals(username, foundMember1.getUsername());
        assertTrue(em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(foundMember1));

        // foundMember1 is instance of proxy class based on Member.class
        assertNotEquals(foundMember1.getClass(), Member.class);

        em.remove(foundMember1);

        tx.commit();
        em.close();
    }

    @Test
    public void criteriaQuery() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Member member = new Member();
        member.setUsername("yunhong");
        em.persist(member);
        tx.commit();

        tx.begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        Root<Member> m = query.from(Member.class);
        CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "yunhong"));
        List<Member> resultList = em.createQuery(cq).getResultList();
        assertEquals(1, resultList.size());
        tx.commit();
    }

    @Test
    public void JpqlForEntity() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        createMembersWithTeam(em);
        List<Team> teams = em.createQuery("select m.team from Member m", Team.class).getResultList();
        assertEquals(2, teams.size());
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        assertEquals(2, teams.size());
        tx.rollback();
        em.close();
    }

    @Test
    public void JpqlForJoin() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        createMembersWithTeam(em);
        List<Object[]> teamAndMembers = em.createQuery(
                "select t, m from Team t LEFT JOIN t.members m", Object[].class).getResultList();
        assertEquals(2, teamAndMembers.size());
        tx.rollback();
        em.close();
    }

    @Test
    public void JpqlForDto() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        createMembersWithTeam(em);
        tx.commit();

        tx.begin();
        String query = "select new org.yunhongmin.practice.dto.MemberDto(m.username, m.age) from Member m";
        List<MemberDto> memberDtos = em.createQuery(query, MemberDto.class).getResultList();
        assertEquals(2, memberDtos.size());
        tx.commit();
        em.close();
    }

    @Test
    public void JpqlForGroupBy() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team = new Team();
        team.setName("team1");
        em.persist(team);

        Member member1 = new Member();
        member1.setUsername("member1");
        member1.setAge(14);
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("member2");
        member2.setAge(16);
        member2.setTeam(team);
        em.persist(member2);

        Team team2 = new Team();
        team.setName("team2");
        em.persist(team2);

        Member member3 = new Member();
        member3.setUsername("member3");
        member3.setAge(9);
        member3.setTeam(team2);
        em.persist(member3);


        String query = "select m.team.name, AVG(m.age), MAX(m.age), MIN(m.age) " +
                "from Member m " +
                "GROUP BY m.team HAVING AVG(m.age) > 10";

        List<Object[]> resultList = em.createQuery(query).getResultList();

        for (Object[] row: resultList) {
            System.out.println("row = " + row[0] + " " + row[1] + " " + row[2] + " " + row[3]);
        }

        tx.rollback();
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

    public void createMembersWithTeam(EntityManager em) {
        Team team = new Team();
        team.setName("team1");
        em.persist(team);

        Member member = new Member();
        member.setUsername("yunhong");
        member.setAge(12);
        member.setTeam(team);

        em.persist(member);

        Member member2 = new Member();
        member2.setUsername("yunhong1");
        member2.setAge(13);
        member2.setTeam(team);

        em.persist(member2);
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