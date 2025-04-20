package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import dto.Team;

@Repository
public class TeamRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Team team) {
        em.persist(team);
    }

    public Team findOne(int id) {
        return em.find(Team.class, id);
    }

    public List<Team> findAll() {
        return em.createQuery("select t from Team t", Team.class).getResultList();
    }

    public List<Team> findByName(String name) {
        return em.createQuery("select t from Team t where t.name = :name", Team.class)
            .setParameter("name", name)
            .getResultList();
    }

    public List<Team> findByDynamicQuery(Integer age, String username) {
        StringBuilder jpql = new StringBuilder("select t from Team t join t.member m");
        List<String> condition = new ArrayList<>();

        if (age != null) {
            condition.add(" m.age = :age");
        }

        if (username != null) {
            condition.add(" m.username = :username");
        }

        if (condition.size() > 0) {
            jpql.append(" where ");
        }

        for (int i = 0; i < condition.size(); i++) {
            if (i > 0) {
                jpql.append(" and ");
            }
            jpql.append(condition.get(i));
        }

        TypedQuery<Team> query = em.createQuery(jpql.toString(), Team.class);

        if (age != null) {
            query.setParameter("age", age);
        }

        if (username != null) {
            query.setParameter("username", username);
        }

        return query.getResultList();
    }
}
