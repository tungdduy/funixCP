package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.User;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface UserRepository extends JpaRepository<User, User_MAPPED.Pk> {

    @Modifying
    @Query("delete from User e where e.userId in ?1")
    void deleteByUserId(Long... id);
    User findByUserId(Long id);


// ____________________ ::BODY_SEPARATOR:: ____________________ //

    boolean existsByEmail(String email);

    User findFirstByUsernameOrEmail(String username, String email);

    User findFirstByEmail(String email);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
