package nl.hsleiden.iprwc.s1136140.DAO;

import nl.hsleiden.iprwc.s1136140.model.database.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
