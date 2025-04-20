package jpaStudy.jpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dto.Member;
import dto.Team;
import repository.MemberRepository;
import repository.TeamRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/resources/*.xml" })
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

//    @Test
    public void testInsertMember() throws Exception {

        Member vo = new Member();
        vo.setId(7);
        vo.setAge(24);
        vo.setUsername("gwon");
        vo.setRegdate(new Date());
        memberRepository.save(vo);

        Team t = new Team();
        t.setId(3);
        t.setName("teamC");
        t.getMember().add(vo);
        teamRepository.save(t);

    }

    @Test
    public void testReadMember() throws Exception {
        Member m = memberRepository.findOne(3);
        assertThat(m.getAge()).isEqualTo(30);
    }

    @Test
    public void testReadTeam() throws Exception { // Mebmer -> Team 가능
        Member m = memberRepository.findOne(3);
        Team t = m.getTeam();
        assertThat(m.getAge()).isEqualTo(30);
        assertThat(t.getName()).isEqualTo("B팀");
    }

    @Test
    public void testReadWithTeam() throws Exception {
        Member m = memberRepository.findByIdAndAge(3, 30);
        assertThat(m).isNotNull();

    }

}