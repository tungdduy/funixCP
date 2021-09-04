package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import net.timxekhach.operation.data.entity.TripUser;
import net.timxekhach.operation.data.mapped.TripUser_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripUserRepository extends JpaRepository<TripUser, TripUser_MAPPED.Pk> {

    void deleteByTripUserId(Long id);
    void deleteAllByTripUserIdIn(List<Long> ids);
    TripUser findByTripUserId(Long id);
    List<TripUser> findByTripUserIdIn(List<Long> id);
    Integer countTripUserIdByTripId(Long trip);
    @SuppressWarnings("unused")
    void deleteByTripId(Long tripId);
    @SuppressWarnings("unused")
    List<TripUser> findByTripId(Long tripId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    List<TripUser> findByUserUserId(Long userId);
    List<TripUser> findByPhoneNumberInOrEmailIn(List<String> phoneNumber, List<String> email);

    TripUser findFirstByConfirmedByEmployeeId(Long employeeId);

    TripUser findFirstByUserUserId(Long userId);
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
