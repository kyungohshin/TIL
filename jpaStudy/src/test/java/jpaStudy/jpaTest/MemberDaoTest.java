package jpaStudy.jpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dto.Member;
import dto.Team;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/resources/*.xml" })
public class MemberDaoTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;

    @BeforeAll
    public static void setupAll() {
        emf = Persistence.createEntityManagerFactory("shin"); // 전체 애플리케이션에서 1개만생성
    }

    @BeforeEach
    public void setup() {
        this.em = emf.createEntityManager(); // 실제로 쓸때마다 생성
        tx = em.getTransaction();
    }

    @AfterEach
    public void close() {
        em.close();
    }

    @AfterAll
    public static void closeAll() {
        emf.close();
    }

    @Test
    public void testInsertMember() throws Exception {
        tx.begin();

        Member vo = new Member();
        vo.setId(7);
        vo.setAge(24);
        vo.setUsername("gwon");
        vo.setRegdate(new Date());
        em.persist(vo);

        Team t = new Team();
        t.setId(3);
        t.setName("teamC");
        t.getMember().add(vo);
        em.persist(t);

        tx.commit();
    }

    @Test
    public void testReadMember() throws Exception {
        tx.begin();
        Member m = em.find(Member.class, 3);
        assertThat(m.getAge()).isEqualTo(30);
        tx.commit();
    }

    @Test
    public void testReadWithTeam() throws Exception {
        tx.begin();
        String jpql = "select m from Member m where m.id = 3 and m.age = 30";
        Member m = em.createQuery(jpql, Member.class).getSingleResult();
        assertThat(m).isNotNull();

        tx.commit();
    }

    @Test
    public void testJoin() throws Exception { // Team -> Member만 가능함. 단방향이므로
        tx.begin();
        String jpql = "select t from Team t join t.member m where t.id = 1 order by t.id";
        List<Team> t = em.createQuery(jpql, Team.class).getResultList();
        assertThat(t.size()).isEqualTo(2);
        assertThat(t.get(1).getMember().get(1).getAge()).isEqualTo(28);
        tx.commit();
    }
}