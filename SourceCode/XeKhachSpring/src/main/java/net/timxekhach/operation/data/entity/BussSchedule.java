package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.BussSchedule_MAPPED;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.utility.XeDateUtils;
import net.timxekhach.utility.XeNumberUtils;

import javax.persistence.Entity;
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
            Long price = this.getPriceBetween(startPoint, endPoint);
            List<PathPoint> pathPoints = this.getPointsBetween(startPoint.getPathPoint(), endPoint.getPathPoint());
            this.preparedTrip = Trip.prepareTrip(this, launchDate, pathPoints, price);
        }
        return isValidOrder;
    }

    public Long getPriceBetween(PathPoint pointFrom, PathPoint pointTo) {
        Long price = this.scheduleUnitPrice;
        BussSchedulePoint schedulePointFrom = this.getSchedulePoint(pointFrom.getPathPointId());
        BussSchedulePoint schedulePointTo = this.getSchedulePoint(pointTo.getPathPointId());
        if (schedulePointFrom.getIsDeductPrice() && schedulePointTo.getIsDeductPrice()) {
            price = XeNumberUtils.equal(schedulePointTo.getPathPointId(), this.endPointPathPointId)
                    ? schedulePointFrom.getPrice() : schedulePointFrom.getPrice() - schedulePointTo.getPrice();
        }
        return price;
    }
    public Long getPriceBetween(BussSchedulePoint pointFrom, BussSchedulePoint pointTo) {
        Long price = this.scheduleUnitPrice;
        if (pointFrom.getIsDeductPrice() && pointTo.getIsDeductPrice()) {
            price = XeNumberUtils.equal(pointTo.getPathPointId(), this.endPointPathPointId)
                    ? pointFrom.getPrice() : pointFrom.getPrice() - pointTo.getPrice();
        }
        return price;
    }

    public BussSchedulePoint getSchedulePoint(Long pathPointId) {
        return this.getBussSchedulePoints().stream()
                .filter(p -> p.getPathPointId().equals(pathPointId))
                .findFirst()
                .orElse(null);
    }

    public List<PathPoint> getPointsBetween(PathPoint pointFrom, PathPoint pointTo) {
        Integer minOrder = Math.min(pointFrom.getPointOrder(), pointTo.getPointOrder());
        Integer maxOrder = Math.max(pointFrom.getPointOrder(), pointTo.getPointOrder());
        return this.getBussSchedulePoints()
                .stream()
                .filter(point -> point.getPathPoint().getPointOrder() >= minOrder && point.getPathPoint().getPointOrder() <= maxOrder)
                .map(BussSchedulePoint::getPathPoint).sorted((p1, p2) -> p1.getPointOrder().compareTo(p2.getPointOrder())).collect(Collectors.toList());
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
        if(this.bussScheduleId == null) {
            this.bussScheduleId = CommonUpdateService.getBussScheduleRepository().save(this).getBussScheduleId();
        }
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

    public boolean isAscendingOrder() {
        return this.startPoint.getPointOrder() < this.endPoint.getPointOrder();
    }
    public boolean isValidDirection(PathPoint startPoint, PathPoint endPoint) {
        return (this.isAscendingOrder() && startPoint.getPointOrder() < endPoint.getPointOrder())
                || (!this.isAscendingOrder() && startPoint.getPointOrder() > endPoint.getPointOrder());
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

