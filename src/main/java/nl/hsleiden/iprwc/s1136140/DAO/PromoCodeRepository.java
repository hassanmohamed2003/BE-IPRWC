package nl.hsleiden.iprwc.s1136140.DAO;

import nl.hsleiden.iprwc.s1136140.model.database.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoCodeRepository extends JpaRepository<PromoCode, String> {
}
