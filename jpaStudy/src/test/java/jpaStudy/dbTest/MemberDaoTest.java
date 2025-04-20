package jpaStudy.dbTest;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dao.MemberDao;
import dto.Member;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/resources/*.xml" })
public class MemberDaoTest {

	@Autowired
	private MemberDao dao;

    @BeforeEach
	public void setup() {

	}

//	@Test
	public void testInsertMember() throws Exception {
		Member vo = new Member();
		vo.setId(5);
		vo.setAge(29);
		vo.setTeam_id(2);
		vo.setUsername("shin");

		dao.insertMember(vo);
	}

	@Test
	public void testReadMember() throws Exception {
		Member m = dao.selectMember(3);
        assertThat(m.getAge()).isEqualTo(30);
	}

	@Test
	public void testReadWithTeam() throws Exception {
		Member m = dao.selectMemberWithAge(3, 30);
        assertThat(m).isNotNull();
	}

	@Test
	public void testJoin() throws Exception {
		List<Member> m = dao.selectMemberJoinTeam(1);
        assertThat(m).hasSize(2);
        assertThat(m.get(1).getAge()).isEqualTo(28);
	}
}
