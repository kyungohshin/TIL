package jpaStudy.jpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dto.Team;
import repository.TeamRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/resources/*.xml" })
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @Transactional // 트랜잭션 내에 존재하게 하여 지연로딩이 가능하게함.
    public void testJoin() throws Exception { // Team -> Member 가능
        List<Team> t = teamRepository.findByDynamicQuery(32, "shin");
        assertThat(t.size()).isEqualTo(1);
        assertThat(t.get(0).getMember().get(0).getUsername()).isEqualTo("shin");
    }
}