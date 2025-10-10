package tacos.repo;

import tacos.dto.Order;

public interface OrderRepository {
	Order save(Order order);

}
