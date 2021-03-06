package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.Trip_MAPPED;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.utility.XeDateUtils;
import net.timxekhach.utility.XeStringUtils;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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
    public List<Integer> getConfirmedSeats() {
        return this.getTripUsers().stream().filter(TripUser::isConfirmed).flatMap(tripUser -> tripUser.getSeats().stream()).collect(Collectors.toList());
    }
    public Long getTotalConfirmedTripUsers() {
        return this.getTripUsers().stream().filter(TripUser::isConfirmed).count();
    }
    public Long getTotalUnConfirmedTripUsers() {
        return this.getTripUsers().stream().filter(tripUser -> !tripUser.isDeleted()).count();
    }
    public Integer getTotalBookedSeats() {
        return this.getTripUsers().stream().filter(tripUser -> !tripUser.isDeleted()).mapToInt(tripUser -> tripUser.getSeats().size()).sum();
    }
    public Integer getTotalLockedSeats() {
        return this.getLockedSeats().size();
    }

    public List<Integer> getLockedSeats() {
        return XeStringUtils.commaGt0ToStreamSortedAsc(this.lockedSeatsString).collect(Collectors.toList());
    }



    public boolean isLaunched() {
        Date currentDateTime = new Date();
        Date tripDateTime = XeDateUtils.mergeDateAndTime(this.launchDate, this.launchTime);
        return currentDateTime.after(tripDateTime);
    }

    @Transient
    List<Integer> lockedBussSeats;

    public List<Integer> getLockedBussSeats() {
        if (this.lockedBussSeats == null) {
            this.lockedBussSeats = this.getBussSchedule().getBuss().getLockedSeats();
        }
        return lockedBussSeats;
    }

    public List<Integer> getBookedSeats() {
        return this.getTripUsers().stream()
                .filter(tripUser -> !tripUser.isDeleted())
                .flatMap(tripUser -> tripUser.getSeats().stream())
                .collect(Collectors.toList());
    }

    public Integer getTotalAvailableSeats() {
        return this.getAvailableSeats().size();
    }

    @Transient
    List<Integer> availableSeats;
    public List<Integer> getAvailableSeats() {
        if (this.availableSeats == null) {
            this.availableSeats = this.bussSchedule.getBuss().getBussType().getSeats();
            this.availableSeats.removeAll(this.getLockedBussSeats());
            this.availableSeats.removeAll(this.getLockedSeats());
            this.availableSeats.removeAll(this.getBookedSeats());
            return this.availableSeats;
        }
       return this.availableSeats;
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

    public void prepareTripUser(TripUser tripUser) {
        this.preparedTripUser = tripUser;
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
                .filter(tripUser -> !tripUser.isDeleted())
                .filter(tripUser -> tripUser.isOverlapPoint(this.getPreparedTripUser()))
                .filter(tripUser -> !tripUser.getTripUserId().equals(this.getPreparedTripUser().getTripUserId()))
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

    public List<TripUser> getTripUsers() {
        return this.tripUsers;
    }

    @Override
    protected void prePersist() {
        String dateSearch = XeDateUtils.buildSearchString(this.launchDate);
        ErrorCode.PLEASE_INPUT.throwIfNull(this.launchDate, "launchDate");
        if(!this.getBussSchedule().getWorkingDays().contains(dateSearch)) {
            ErrorCode.INVALID_BUSS_SCHEDULE_WORKING_DAY.throwNow(this.bussSchedule.getWorkingDays());
        }
        if (this.getLaunchDate().before(this.getBussSchedule().getEffectiveDateFrom())) {
           ErrorCode.INVALID_EFFECTIVE_DATE.throwNow(this.bussSchedule.getEffectiveDateFrom().toString());
        }
        ErrorCode.DATA_EXISTED.throwIf(Trip.findOrEmulateTrip(this.getBussSchedule(), this.launchDate).tripId != null);
        this.tripUnitPrice = this.bussSchedule.getScheduleUnitPrice();
        this.launchTime = this.bussSchedule.getLaunchTime();
    }

    @Override
    protected void preUpdate() {

    }

    @Override
    public void preSetFieldAction() {
    }

    @Override
    public void postSetFieldAction() {

    }

    protected boolean isValidLaunchDateTime(Trip trip){
        boolean valid = false;
        LocalDateTime currentLDT = LocalDateTime.now(ZoneId.systemDefault());

        //check launch date
        LocalDateTime searchLDT = LocalDateTime.ofInstant(trip.getLaunchDate().toInstant(), ZoneId.systemDefault());
        LocalDate searchLD = searchLDT.toLocalDate();
        if (currentLDT.toLocalDate().isBefore(searchLD))
            //only accept search date is after today
            //otherwise (equal today) need to check time
            valid = true;
        else if (currentLDT.toLocalDate().isAfter(searchLD))
            //not accept search in the pass
            return false;

        // check launch Time
        LocalDateTime launchLDT = LocalDateTime.ofInstant(trip.getLaunchTime().toInstant(), ZoneId.systemDefault());

        LocalTime currentLT = currentLDT.toLocalTime();
        LocalTime launchLT = launchLDT.toLocalTime();

        if ( launchLT.getHour() > currentLT.getHour() )
            valid = true;
        else if (launchLT.getHour() == currentLT.getHour()){
            //if current minute less than 5 minutes then ok
            if (launchLT.getMinute() > currentLT.getMinute() - 5 )
                valid = true;
        }

        return valid;
    }

    public Date getLaunchDateTime() {
        return XeDateUtils.mergeDateAndTime(this.launchDate, this.launchTime);
    }
// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<      PREPARE FOR FINDING SCHEDULE
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

