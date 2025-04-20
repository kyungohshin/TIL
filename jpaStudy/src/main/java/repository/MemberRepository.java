package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import dto.Member;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(int id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
            .setParameter("username", username)
            .getResultList();
    }

    public Member findByIdAndAge(int id, int age) {
        return em.createQuery("select m from Member m where m.id = :id and m.age = :age", Member.class)
            .setParameter("id", id)
            .setParameter("age", age)
            .getSingleResult();
    }

}
