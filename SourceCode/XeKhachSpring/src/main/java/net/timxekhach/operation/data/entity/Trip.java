package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.Trip_MAPPED;
import net.timxekhach.operation.data.model.TripSeat;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.utility.XeDateUtils;
import net.timxekhach.utility.XeStringUtils;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class Trip extends Trip_MAPPED {

    public Trip() {}
    public Trip(BussSchedule bussSchedule) {
        super(bussSchedule);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //

    public Long getTotalApprovedTripUsers() {
        return this.getTripUsers().stream().filter(tripUser -> tripUser.isApproved()).count();
    }
    public Long getTotalUnApprovedTripUsers() {
        return this.getTripUsers().stream().filter(tripUser -> !tripUser.isDeleted()).count();
    }

    public Integer getTotalBookedSeats() {
        return this.getTripUsers().stream().filter(tripUser -> !tripUser.isDeleted()).mapToInt(tripUser -> tripUser.getSeats().size()).sum();
    }

    public boolean isLaunched() {
        Date currentDateTime = new Date();
        Date tripDateTime = XeDateUtils.mergeDateAndTime(this.launchDate, this.launchTime);
        return currentDateTime.after(tripDateTime);
    }

    public List<Integer> getLockedSeats() {
        return XeStringUtils.commaGt0ToStream(this.lockedSeatsString).collect(Collectors.toList());
    }

    @Transient
    List<Integer> lockedBussSeats;

    public List<Integer> getLockedBussSeats() {
        if (this.lockedBussSeats == null) {
            this.lockedBussSeats = this.getBussSchedule().getBuss().getLockedSeats();
        }
        return lockedBussSeats;
    }

    // PREPARE FOR FINDING SCHEDULE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Transient
    TripUser preparedTripUser;

    public static Trip prepareTrip(BussSchedule bussSchedule, Date launchDate, List<PathPoint> pathPoints, Long price) {
        Trip trip = findOrEmulateTrip(bussSchedule, launchDate);
        trip.launchDate = launchDate;
        trip.launchTime = bussSchedule.getLaunchTime();
        trip.tripUnitPrice = bussSchedule.getScheduleUnitPrice();
        trip.preparedTripUser = TripUser.prepareTripUser(trip, price, pathPoints);
        return trip;
    }

    List<TripSeat> getPreparedTripSeats() {
        return IntStream.range(1, this.getTotalSeats())
                .boxed()
                .map(seatId -> {
                    TripSeat tripSeat = new TripSeat();
                    tripSeat.setSeatId(seatId);
                    tripSeat.setIsBooked(this.getPreparedBookedSeats().contains(seatId));
                    tripSeat.setIsLocked(this.getLockedBussSeats().contains(seatId));
                    tripSeat.setIsFree(!tripSeat.getIsBooked() && !tripSeat.getIsLocked());
                    return tripSeat;
                }).collect(Collectors.toList());
    }

    public Integer getTotalSeats() {
        return this.getBussSchedule().getBuss().getBussType().getTotalSeats();
    }

    public boolean isFull() {
        return this.getPreparedAvailableSeats().isEmpty();
    }


    @Transient
    List<Integer> preparedBookedSeats;

    public List<Integer> getPreparedBookedSeats() {
        return this.getTripUsers().stream()
                .filter(tripUser -> tripUser.isOverlapPoint(this.getPreparedTripUser()))
                .flatMap(tripUser -> tripUser.getSeats().stream())
                .collect(Collectors.toList());
    }

    @Transient
    List<Integer> preparedAvailableSeats;

    public List<Integer> getPreparedAvailableSeats() {
        if (this.preparedAvailableSeats == null) {
            List<Integer> fetchingSeats = IntStream.range(1, this.getTotalSeats() + 1)
                    .boxed()
                    .collect(Collectors.toList());
            fetchingSeats.removeAll(this.getPreparedBookedSeats());
            fetchingSeats.removeAll(this.getLockedBussSeats());
            fetchingSeats.removeAll(this.getLockedSeats());
            this.preparedAvailableSeats = fetchingSeats;
        }
        return this.preparedAvailableSeats;
    }

    public Integer getPreparedTotalAvailableSeats() {
        return this.getPreparedAvailableSeats().size();
    }

    private static Trip findOrEmulateTrip(BussSchedule schedule, Date launchDate) {
        return CommonUpdateService.getTripRepository()
                .findFirstByBussScheduleIdAndLaunchDate(schedule.getBussScheduleId(), launchDate)
                .orElse(new Trip(schedule));
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<      PREPARE FOR FINDING SCHEDULE
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

