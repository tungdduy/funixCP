package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.TripUser_MAPPED;
import net.timxekhach.operation.data.enumeration.TripUserStatus;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.utility.XeNumberUtils;
import net.timxekhach.utility.XeStringUtils;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class TripUser extends TripUser_MAPPED {

    public TripUser() {}
    public TripUser(Trip trip, User user) {
        super(trip, user);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    @Transient
    private List<Integer> seats;

    public static TripUser prepareTripUser(Trip trip, Long price, List<PathPoint> pathPoints) {
        TripUser tripUser = new TripUser();
        tripUser.setTrip(trip);
        tripUser.tripUserPoints = pathPoints;
        tripUser.unitPrice = price;
        tripUser.getStartPoint();
        tripUser.getEndPoint();
        return tripUser;
    }

    @Transient
    List<PathPoint> tripUserPoints;
    public List<PathPoint> getTripUserPoints() {
        if(this.tripUserPoints == null) {
            tripUserPoints = new ArrayList<>();
            List<Long> pathPointIds = XeStringUtils.commaGt0ToStream(this.tripUserPointsString)
                    .map(Integer::longValue)
                    .collect(Collectors.toList());
            if (!pathPointIds.isEmpty()) {
                tripUserPoints = CommonUpdateService.getPathPointRepository().findByPathPointIdIn(pathPointIds);
            }
        }
        return this.tripUserPoints;
    }

    public Integer getTotalTripUserPoints(){
        return tripUserPoints == null ? null : tripUserPoints.size();
    }

    @Transient
    PathPoint startPoint;

    public PathPoint getStartPoint() {
        startPoint = tripUserPoints == null ? null : tripUserPoints.get(0);
        return startPoint;
    }

    @Transient
    PathPoint endPoint;

    public PathPoint getEndPoint() {
        if (endPoint == null) {
            endPoint = tripUserPoints == null ? null : tripUserPoints.get(tripUserPoints.size() - 1);
        }
        return endPoint;
    }

    public boolean isDeleted() {
        return this.status != null && this.status == TripUserStatus.DELETED;
    }

    public Trip getTrip(){
        return this.trip.getTripId() != null ? this.trip : null;
    }

    boolean isOverlapPoint(TripUser tripUser) {
        if(tripUser == null || tripUser.isDeleted()) return false;
        return this.getStartPoint() != null && this.getEndPoint() != null
                && tripUser.getStartPoint() != null && tripUser.getEndPoint() != null
                && XeNumberUtils.anyNullOrOverlap(
                tripUser.getStartPoint().getPointOrder(),
                tripUser.getEndPoint().getPointOrder(),
                startPoint.getPointOrder(),
                endPoint.getPointOrder());
    }

    public List<Integer> getSeats() {
        if (this.seats == null) {
            this.seats = XeStringUtils.commaGt0ToStream(this.seatsString)
                    .filter(seat -> seat <= this.getTrip()
                            .getBussSchedule()
                            .getBuss()
                            .getBussType()
                            .getTotalSeats())
                    .collect(Collectors.toList());
        }
        return this.seats;
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

