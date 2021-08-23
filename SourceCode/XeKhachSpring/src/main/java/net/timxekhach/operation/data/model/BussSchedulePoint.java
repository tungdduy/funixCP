package net.timxekhach.operation.data.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.operation.data.entity.PathPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BussSchedulePoint {
    Long bussScheduleId, pathPointId;
    Long price;
    PathPoint pathPoint;
    Integer pointOrder;
    Boolean isDeductPriceFromPreviousPoint;


    private BussSchedulePoint() {}

    public static List<BussSchedulePoint> parse(String jsonValue) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return Arrays.stream(objectMapper.readValue(jsonValue, BussSchedulePoint[].class)).collect(Collectors.toList());
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public static BussSchedulePoint schedule(BussSchedule schedule) {
        BussSchedulePoint point = new BussSchedulePoint();
        point.bussScheduleId = schedule.getBussScheduleId();
        point.price = schedule.getPrice();
        return point;
    }

    public BussSchedulePoint pathPoint(PathPoint pathPoint) {
        this.pathPoint = pathPoint;
        this.pathPointId = pathPoint.getPathPointId();
        this.pointOrder = pathPoint.getPointOrder();
        return this;
    }

}
