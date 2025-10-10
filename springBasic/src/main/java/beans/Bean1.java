package beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Bean1 {

	@Autowired
	@Qualifier("dataBean1")
	private DataBean1 dataBean1;

	@Autowired
	@Qualifier("dataBean2")
	private DataBean1 dataBean2;

}
