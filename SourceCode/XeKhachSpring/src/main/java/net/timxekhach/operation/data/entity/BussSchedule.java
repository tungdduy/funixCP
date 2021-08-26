package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.BussSchedule_MAPPED;
import lombok.AccessLevel;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.utility.XeDateUtils;
import net.timxekhach.utility.XeNumberUtils;
import javax.persistence.Transient;
import javax.xml.ws.Holder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class BussSchedule extends BussSchedule_MAPPED {

    public BussSchedule() {}
    public BussSchedule(Buss buss) {
        super(buss);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    //search schedules
    public static List<BussSchedule> findBussSchedules(Long locationIdStart, Long locationIdEnd, Date date) {
        String dayInWeekExpression = XeDateUtils.buildSearchString(date);
        List<BussSchedule> validDateSchedules = CommonUpdateService.getBussScheduleRepository()
                .findByEffectiveDateFromLessThanEqualAndWorkingDaysContains(date, dayInWeekExpression);
        Holder<BussSchedulePoint> startPoint = new Holder<>();
        Holder<BussSchedulePoint> endPoint = new Holder<>();
        return validDateSchedules.stream()
                .filter(schedule -> schedule.getBussSchedulePoints()
                        .stream()
                        .filter(point -> {
                            if (point.getPathPoint().getLocation().getLocationId().equals(locationIdStart)) {
                                startPoint.value = point;
                                return true;
                            }
                            if (point.getPathPoint().getLocation().getLocationId().equals(locationIdEnd)) {
                                endPoint.value = point;
                                return true;
                            }
                            return false;
                        })
                        .count() >= 2)
                .filter(schedule -> schedule.validateDirectionAndGetTrip(date, startPoint.value, endPoint.value))
                .collect(Collectors.toList());
    }

    @Transient
    Trip preparedTrip;

    private boolean validateDirectionAndGetTrip(Date launchDate, BussSchedulePoint startPoint, BussSchedulePoint endPoint) {
        int pointOrderStart = startPoint.getPathPoint().getPointOrder();
        int pointOrderEnd = endPoint.getPathPoint().getPointOrder();
        boolean isValidOrder = (pointOrderEnd - pointOrderStart > 0 && this.isAscendingOrder())
                || (pointOrderEnd - pointOrderStart < 0 && !this.isAscendingOrder());

        if (isValidOrder) {
            Long price = this.scheduleUnitPrice;
            if (startPoint.getIsDeductPrice() && endPoint.getIsDeductPrice()) {
                price = XeNumberUtils.equal(endPoint.getPathPointId(), this.endPointPathPointId) ? startPoint.getPrice() : startPoint.getPrice() - endPoint.getPrice();
            }
            Integer minOrder = Math.min(startPoint.getPathPoint().getPointOrder(), endPoint.getPathPoint().getPointOrder());
            Integer maxOrder = Math.max(startPoint.getPathPoint().getPointOrder(), endPoint.getPathPoint().getPointOrder());
            this.sortSchedulePoints();
            List<PathPoint> pathPoints = this.getSortedBussSchedulePoints()
                    .stream()
                    .filter(point -> point.getPathPoint().getPointOrder() >= minOrder && point.getPathPoint().getPointOrder() <= maxOrder)
                    .map(BussSchedulePoint::getPathPoint)
                    .collect(Collectors.toList());
            this.preparedTrip = Trip.prepareTrip(this, launchDate, pathPoints, price);
        }
        return isValidOrder;
    }

    @Transient
    @Getter(AccessLevel.PROTECTED)
    protected Long pathIdBeforeSetField;
    @Transient
    @Getter(AccessLevel.PROTECTED)
    protected Long startPathPointIdBeforeSetField;
    @Transient
    @Getter(AccessLevel.PROTECTED)
    protected Long endPathPointIdBeforeSetField;

    @Override
    public void preSetFieldAction() {
        this.pathIdBeforeSetField = this.pathPathId;
        this.startPathPointIdBeforeSetField = this.startPointPathPointId;
        this.endPathPointIdBeforeSetField = this.endPointPathPointId;
    }

    @Override
    public void postSetFieldAction() {
        if (XeNumberUtils.notEqual(this.pathIdBeforeSetField, this.pathPathId)) {
            this.initBussSchedulePoints();
            return;
        }

        if (XeNumberUtils.notEqual(this.startPathPointIdBeforeSetField, this.startPointPathPointId)
                || XeNumberUtils.notEqual(this.endPathPointIdBeforeSetField, this.endPointPathPointId)
        ) {
            this.updateBussSchedulePoints();
        }
    }

    private void initBussSchedulePoints() {
        CommonUpdateService.getBussSchedulePointRepository().deleteByBussScheduleId(this.bussScheduleId);
        int startOrder = this.startPoint.getPointOrder();
        int endOrder = this.endPoint.getPointOrder();
        int min = Math.min(startOrder, endOrder);
        int max = Math.max(startOrder, endOrder);
        this.path.getPathPoints().stream().filter(
                point -> point.getPointOrder() >= min
                        && point.getPointOrder() <= max)
                .forEach(point -> BussSchedulePoint.init(this, point));
    }

    private void updateBussSchedulePoints() {
        int min = Math.min(this.startPoint.getPointOrder(), this.endPoint.getPointOrder());
        int max = Math.max(this.startPoint.getPointOrder(), this.endPoint.getPointOrder());
        removeRedundantSchedulePoints(min, max);
        addMissingSchedulePoints(min, max);
    }

    private void addMissingSchedulePoints(int min, int max) {
        List<Long> currentPathPointIds = this.getBussSchedulePoints()
                .stream()
                .map(BussSchedulePoint::getPathPointId)
                .collect(Collectors.toList());
        Holder<Integer> totalAddedPoints = new Holder<>(0);
        this.path.getPathPoints().stream()
                .filter(
                        point -> point.getPointOrder() >= min
                                && point.getPointOrder() <= max
                                && !currentPathPointIds.contains(point.getPathPointId()))
                .peek(p -> totalAddedPoints.value++)
                .forEach(point -> BussSchedulePoint.init(this, point));
        if (totalAddedPoints.value > 0) {
            this.sortSchedulePoints();
        }
    }

    private void removeRedundantSchedulePoints(int min, int max) {
        List<Long> redundantPointIds = this.getBussSchedulePoints()
                .stream()
                .filter(point ->
                        point.getPathPoint().getPointOrder() < min
                                || point.getPathPoint().getPointOrder() > max
                ).map(BussSchedulePoint::getBussSchedulePointId)
                .collect(Collectors.toList());
        if (!redundantPointIds.isEmpty()) {
            CommonUpdateService.getBussSchedulePointRepository().deleteAllByBussSchedulePointIdIn(redundantPointIds);
            this.sortSchedulePoints();
        }

    }

    private boolean isAscendingOrder() {
        return this.startPoint.getPointOrder() < this.endPoint.getPointOrder();
    }

    @Transient
    private List<BussSchedulePoint> sortedBussSchedulePoints;

    public List<BussSchedulePoint> getSortedBussSchedulePoints() {
        if (this.sortedBussSchedulePoints == null) {
            this.sortSchedulePoints();
        }

        return this.sortedBussSchedulePoints;
    }

    void sortSchedulePoints() {
        this.sortedBussSchedulePoints = new ArrayList<>(this.getBussSchedulePoints());
        sortedBussSchedulePoints.sort((p1, p2) -> this.isAscendingOrder()
                ? p1.getPathPoint().getPointOrder() - p2.getPathPoint().getPointOrder()
                : p2.getPathPoint().getPointOrder() - p1.getPathPoint().getPointOrder());
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

