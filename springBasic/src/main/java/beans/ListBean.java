package beans;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ListBean {
	private List<String> list;
	private List<Integer> list2;
	private List<TestBean> list3;
	private Set<String> set;
	private Map<String, Object> map;
	private Properties property;

	public Properties getProperty() {
		return property;
	}

	public void setProperty(Properties property) {
		this.property = property;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public List<Integer> getList2() {
		return list2;
	}

	public void setList2(List<Integer> list2) {
		this.list2 = list2;
	}

	public List<TestBean> getList3() {
		return list3;
	}

	public void setList3(List<TestBean> list3) {
		this.list3 = list3;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
