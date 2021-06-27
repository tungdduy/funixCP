package net.timxekhach.operation.repository;

import net.timxekhach.operation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findFirstByUsernameOrEmail(String username, String email);

}