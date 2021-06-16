package net.timxekhach.operation.repository;

import net.timxekhach.operation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);

    User findByUsername(String username);

}