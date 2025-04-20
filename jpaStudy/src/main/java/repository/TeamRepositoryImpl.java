package repository;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;

import dto.QMember;
import dto.QTeam;
import dto.Team;

public class TeamRepositoryImpl extends QuerydslSupport implements TeamRepositoryCustom {

    @Override
    public List<Team> findByDynamicQuery(Integer age, String username) {
        QTeam team = QTeam.team;
        QMember member = QMember.member;

        return getQueryFactory().selectFrom(team)
            .join(team.member, member) // team.member와 member를 조인
            .where(
                eqAge(age),
                eqUsername(username) // 조건 동적 추가
            )
            .fetch();
    }

    // 조건 메서드로 분리
    private BooleanExpression eqAge(Integer age) {
        return age != null ? QMember.member.age.eq(age) : null;
    }

    private BooleanExpression eqUsername(String username) {
        return username != null ? QMember.member.username.eq(username) : null;
    }
}
