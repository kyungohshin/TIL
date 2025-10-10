package tacos.repo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.dto.Order;
import tacos.dto.Taco;

@Component
public class JdbcOrderRepostitory implements OrderRepository {
	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInsterter;
	private ObjectMapper objectMapper;

	@Autowired
	public JdbcOrderRepostitory(JdbcTemplate jdbc) {
		// db가 생성해주는 id로 사용
		this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
		this.orderTacoInsterter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public Order save(Order order) {
		order.setPlaceAt(new Date());
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		List<Taco> tacos = order.getTacos();

		for (Taco taco : tacos) {
			saveTacoToOrder(taco, orderId);
		}

		return order;
	}

	private long saveOrderDetails(Order order) {
		@SuppressWarnings("unchecked")
		// 일일히 map에 넣는거보다 objectMapper를 쓰는게 더 쉽다..
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlaceAt());

		// db에 insert후 id값 반환
		long orderId = orderInserter.executeAndReturnKey(values).longValue();
		return orderId;
	}

	private void saveTacoToOrder(Taco taco, long orderId) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());
		orderTacoInsterter.execute(values);
	}

}
