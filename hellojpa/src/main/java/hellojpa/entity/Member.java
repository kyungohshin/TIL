package hellojpa.entity;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="USERNAME", nullable=false, length=20) // ���� ����÷��� USERNAME, �ٵ� �ڹ� name�� ��Ī�ϰڴٴ¶�
	private String name;
	
	private int age;
	
//	@Column(name="TEAM_ID")
//	private Long teamId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TEAM_ID")
	private Team team;
	
	@Temporal(TemporalType.TIMESTAMP) @GeneratedValue(strategy=GenerationType.AUTO) // ��¥Ÿ��
	private Date regDate;

	@Enumerated(EnumType.STRING) // �ڹ� EnumŸ�� ����, �׻� STRING���� �Ұ�! ORDINAL�� �⺻���ε� �׷� 0,1,2 ������ ��
	private MemberType memberType;

	@Lob
	private String lobString;

	@Lob
	private byte[] lobByte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

	public String getLobString() {
		return lobString;
	}

	public void setLobString(String lobString) {
		this.lobString = lobString;
	}

	public byte[] getLobByte() {
		return lobByte;
	}

	public void setLobByte(byte[] lobByte) {
		this.lobByte = lobByte;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", age=" + age + ", team=" + team + ", regDate=" + regDate
				+ ", memberType=" + memberType + ", lobString=" + lobString + ", lobByte=" + Arrays.toString(lobByte)
				+ "]";
	}
	
	
}
