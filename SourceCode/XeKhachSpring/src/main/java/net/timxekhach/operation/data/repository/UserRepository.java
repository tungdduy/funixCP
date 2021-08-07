package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import java.util.List;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.User;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface UserRepository extends JpaRepository<User, User_MAPPED.Pk> {

    void deleteByUserId(Long id);
    void deleteAllByUserIdIn(List<Long> ids);
    User findByUserId(Long id);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

    boolean existsByEmail(String email);

    User findFirstByUsernameOrEmail(String username, String email);

    User findFirstByEmail(String email);

    List<User> findByUserIdIn(List<Long> userIds);

    boolean existsByUserIdIn(List<Long> userIds);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
