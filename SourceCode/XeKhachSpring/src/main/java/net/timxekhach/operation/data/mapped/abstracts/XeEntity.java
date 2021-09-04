package net.timxekhach.operation.data.mapped.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.timxekhach.operation.data.entity.*;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.security.model.SecurityResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@MappedSuperclass
public abstract class XeEntity implements Serializable {

    @Transient
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    public String buildProfileImagePath() {
        return String.format("%s/profile.jpg", this.buildUniquePath());
    }

    protected String getProfileImageUrl() {
        if(new File(this.buildProfileImagePath()).exists()) {
            return String.format("%s/profile.jpg", this.buildUniqueUrl());
        }
        return String.format("https://robohash.org/xekhach/%s", this.buildRelativePath());
    }

    private String buildUniquePath() {
        return String.format("%s%s", SecurityResource.instance.get().getResourcePath(), this.buildRelativePath());
    }

    private String buildUniqueUrl() {
        return String.format("%s%s", SecurityResource.instance.get().getResourceUrl(), this.buildRelativePath());
    }

    private String buildRelativePath() {
        return String.format("%s/%s", this.getLowerName(), this.getIncrementId());
    }

    abstract protected Long getIncrementId();

    @Transient
    String lowerName;
    String getLowerName() {
        if(lowerName == null) {
            lowerName = this.getClass().getSimpleName().toLowerCase();
        }
        return this.lowerName;
    }

    protected void deleteProfileImage() {
        File file = new File(this.buildProfileImagePath());
        if(file.exists()) {
            file.delete();
        }
    }

    public void saveProfileImage(MultipartFile profileImage) throws IOException {
        File file = new File(this.buildProfileImagePath());
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        profileImage.transferTo(file);
    }
    @Transient
    @JsonIgnore
    protected boolean isPrePersisted;
    @PrePersist
    private void _prePersist(){
        if (!this.isPrePersisted) { //avoid call twice on persist
            this.isPrePersisted = true;
            this.prePersist();
        }
    }
    protected void prePersist() {};

    @Transient
    @JsonIgnore
    protected boolean isPreUpdate;
    @PreUpdate
    private void _preUpdate(){
        if (!this.isPreUpdate) { //avoid call twice on persist
            this.isPreUpdate = true;
            this.preUpdate();
        }
    }
    protected void preUpdate() {};

    @Transient
    @JsonIgnore
    protected boolean isPostUpdate;
    @PostUpdate
    private void _postUpdate(){
        if (!this.isPostUpdate) { //avoid call twice on persist
            this.isPostUpdate = true;
            this.postUpdate();
        }
    }
    protected void postUpdate(){};

    @Transient
    @JsonIgnore
    protected boolean isPostPersisted;
    @PostPersist
    private void _postPersist(){
        if (!this.isPostPersisted) { //avoid call twice on persist
            this.isPostPersisted = true;
            this.postPersist();
        }
    }
    protected void postPersist() {};


    @Transient
    @JsonIgnore
    protected boolean isPreRemoved;
    @PreRemove
    private void _preRemove(){
        if (!this.isPreRemoved) { //avoid call twice on removed
            this.isPreRemoved = true;
            this.preRemove();
        }
    }

    protected void _validateBeforeRemove() {
        if (this instanceof Employee) {
           Employee employee = (Employee) this;
           if (employee.getCountBusses() > 0) {
               List<BussEmployee> bussEmployees = CommonUpdateService.getBussEmployeeRepository().findByEmployeeId(employee.getEmployeeId());
               ErrorCode.REMOVE_THIS_EMPLOYEE_FROM_BUSS_FIRST.throwNow(bussEmployees.get(0).getBuss().getBussLicense());
           }
           ErrorCode.CANNOT_REMOVE_EMPLOYEE_HAS_CONFIRMED_TICKET.throwIfNotNull(CommonUpdateService.getTripUserRepository().findFirstByConfirmedByEmployeeId(employee.getEmployeeId()));
        }

        if (this instanceof User) {
            User user = (User) this;
            ErrorCode.CANNOT_REMOVE_USER_HAS_TICKET.throwIfNotNull(CommonUpdateService.getTripUserRepository().findFirstByUserUserId(user.getUserId()));
            ErrorCode.CANNOT_REMOVE_USER_IS_EMPLOYEE.throwIfNotNull(user.getEmployee());
        }

        if (this instanceof Buss) {
           Buss buss = (Buss) this;
           ErrorCode.CANNOT_REMOVE_BUSS_HAS_EMPLOYEES.throwIf(buss.getTotalBussEmployees() > 0);
           ErrorCode.CANNOT_REMOVE_BUSS_HAS_SCHEDULE.throwIf(buss.getTotalSchedules() > 0);
        }

        if (this instanceof BussSchedule) {
            BussSchedule bussSchedule = (BussSchedule) this;
            ErrorCode.CANNOT_REMOVE_BUSS_SCHEDULE_HAS_BUSS_SCHEDULE_POINTS.throwIf(bussSchedule.getTotalBussSchedulePoints() > 0);
            ErrorCode.CANNOT_REMOVE_BUSS_SCHEDULE_HAS_TRIP.throwIfNotNull(CommonUpdateService.getTripRepository().findFirstByBussScheduleId(bussSchedule.getBussScheduleId()));
        }

        if (this instanceof Trip) {
            Trip trip = (Trip) this;
            ErrorCode.CANNOT_REMOVE_TRIP_HAS_TICKET.throwIf(trip.getTotalTripUsers() > 0);
        }

        if (this instanceof Path) {
            Path path = (Path) this;
            ErrorCode.CANNOT_REMOVE_PATH_HAS_PATH_POINT.throwIf(path.getTotalPathPoints() > 0);
            ErrorCode.CANNOT_REMOVE_PATH_HAS_BUSS_SCHEDULES.throwIf(CommonUpdateService.getBussScheduleRepository().countBussScheduleIdByPathPathId(path.getPathId()) > 0);
        }

        if (this instanceof PathPoint) {
            PathPoint pathPoint = (PathPoint) this;
            ErrorCode.CANNOT_REMOVE_PATH_POINT_HAS_BUSS_SCHEDULE_POINT.throwIf(pathPoint.getTotalBussSchedulePoints() > 0);
        }

    };

    protected void preRemove() {};

    @Transient
    @JsonIgnore
    protected boolean isPostRemoved;
    @PostRemove
    private void _postRemove(){
        if (!this.isPostRemoved) { //avoid call twice on removed
            this.isPostRemoved = true;
            this.postRemove();
        }
    }
    protected void postRemove() {}

    public void preSaveAction() {}
    public void preRemoveAction(){
        this._validateBeforeRemove();
    }
    public void preUpdateAction(){}
    public void preSetFieldAction(){}
    public void postSetFieldAction(){}

    public void setFieldByName(Map<String, String> data) {
        this.preSetFieldAction();
        this._setFieldByName(data);
        this.postSetFieldAction();
    }

    protected abstract void _setFieldByName(Map<String, String> data);

    @Transient
    @SuppressWarnings("rawtypes")
    protected JpaRepository getRepository() {
        return (JpaRepository) CommonUpdateService.repoMap.get(this.getClass());
    }

    @SuppressWarnings("unchecked")
    public void save() {
        this.getRepository().save(this);
    }
}
