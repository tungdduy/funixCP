package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.Buss_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussRepository extends JpaRepository<Buss, Buss_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateBuss(Map<String, String> data) {
        Buss buss = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(Buss.pk(data)));
        buss.setFieldByName(data);
        this.save(buss);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
