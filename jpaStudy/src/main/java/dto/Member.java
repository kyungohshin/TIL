package dto;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
    @Id
	private int id;

	private int age;

    @Lob
	private String lobbyte;

    @Lob
	private String lobstring;

//  @Enumerated(EnumType.STRING)
	private String membertype;

	private String username;

    @Temporal(TemporalType.TIMESTAMP) // 동시적용 x
    private Date regdate;

	private int team_id;

}
