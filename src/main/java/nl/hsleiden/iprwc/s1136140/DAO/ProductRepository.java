package nl.hsleiden.iprwc.s1136140.DAO;

import nl.hsleiden.iprwc.s1136140.model.database.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
