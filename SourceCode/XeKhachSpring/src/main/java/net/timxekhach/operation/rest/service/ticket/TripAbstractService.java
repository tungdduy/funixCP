package net.timxekhach.operation.rest.service.ticket;

public abstract class TripAbstractService implements ITripService{

    protected Long startPoint;
    protected Long endPoint;
    protected Long startTime;

    public void setStartPoint(Long startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Long endPoint) {
        this.endPoint = endPoint;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
}
