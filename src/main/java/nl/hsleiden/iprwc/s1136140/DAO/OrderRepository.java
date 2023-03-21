package nl.hsleiden.iprwc.s1136140.DAO;

import nl.hsleiden.iprwc.s1136140.model.database.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
