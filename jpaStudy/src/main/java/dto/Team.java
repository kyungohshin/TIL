package dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Team {
    @Id
	private int id;

	private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> member = new ArrayList<>();
}
