package tacos.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tacos.dto.Order;

//	Order save(Order order);
public interface OrderRepository extends CrudRepository<Order, Long> {
	List<Order> findByDeliveryZip(String deliverZip);

	// 스프링 데이터 JPA가 알아서 이름을 분석해서 결과값을 준다.
	List<Order> findByDeliveryZipAndPlaceAtBetween(String deliverZip, Date StartDate, Date endDate);

	List<Order> findByDeliveryZipOrderByDeliveryCity(String deliverZip);

	@Query("select o from Order o where o.deliveryCity='Seattle'") // 명명규칙으로는 안될때 사용
	List<Order> findOrderDeliveredInSeattle();
}
