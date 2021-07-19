package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.mapped.Trip_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripRepository extends JpaRepository<Trip, Trip_MAPPED.Pk> {

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
