package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.User;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface UserRepository extends JpaRepository<User, User_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateUser(Map<String, String> data) {
        User user = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(User.pk(data)));
        user.setFieldByName(data);
        this.save(user);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //

    boolean existsByEmail(String email);

    User findFirstByUsernameOrEmail(String username, String email);

    User findFirstByEmail(String email);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
