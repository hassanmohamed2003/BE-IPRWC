package nl.hsleiden.iprwc.s1136140.DAO;

import nl.hsleiden.iprwc.s1136140.model.database.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
