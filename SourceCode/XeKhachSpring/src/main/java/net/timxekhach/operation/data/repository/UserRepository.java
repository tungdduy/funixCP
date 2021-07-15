package net.timxekhach.operation.data.repository;

import net.timxekhach.operation.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findFirstByUsernameOrEmail(String username, String email);

}