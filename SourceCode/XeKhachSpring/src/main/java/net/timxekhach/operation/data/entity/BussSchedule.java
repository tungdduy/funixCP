package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.BussSchedule_MAPPED;
import net.timxekhach.operation.data.model.BussSchedulePoint;
import javax.persistence.Transient;
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
    @Transient
    private List<BussSchedulePoint> bussSchedulePoints;

    public List<BussSchedulePoint> getBussSchedulePoints() {
        if (this.bussSchedulePoints == null) {
            Integer startOrder = this.getStartPoint().getPointOrder();
            Integer endOrder = this.getEndPoint().getPointOrder();
            boolean isAsc = startOrder < endOrder;
            Integer minOrder = isAsc ? startOrder : endOrder;
            Integer maxOrder = isAsc ? endOrder : startOrder;
            List<BussSchedulePoint> currentPoints = BussSchedulePoint.parse(this.jsonBussSchedulePoints);

            this.bussSchedulePoints = this.getPath().getPathPoints()
                    .stream().filter(point ->
                            point.getPointOrder() > minOrder
                                    && point.getPointOrder() < maxOrder
                    ).map(point -> {
                        for (BussSchedulePoint currentPoint : currentPoints) {
                            if (point.getPathPointId().equals(currentPoint.getPathPointId())) {
                                currentPoint.pathPoint(point);
                                return currentPoint;
                            }
                        }
                        return BussSchedulePoint.schedule(this).pathPoint(point);
                    })
                    .collect(Collectors.toList());
        }

        return this.bussSchedulePoints;
    }

    public Integer getTotalMiddlePoints() {
        return this.getBussSchedulePoints().size();
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

