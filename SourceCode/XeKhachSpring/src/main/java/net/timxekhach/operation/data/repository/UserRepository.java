package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface UserRepository extends JpaRepository<User, User_MAPPED.Pk> {

// ____________________ ::BODY_SEPARATOR:: ____________________ //

    boolean existsByEmail(String email);

    User findFirstByUsernameOrEmail(String username, String email);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
