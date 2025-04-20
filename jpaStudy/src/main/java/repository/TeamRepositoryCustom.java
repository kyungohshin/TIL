package repository;

import java.util.List;

import dto.Team;

public interface TeamRepositoryCustom {

    public List<Team> findByDynamicQuery(Integer age, String username);
}
