package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.Location;
import net.timxekhach.operation.data.entity.Path;
import javax.validation.constraints.*;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import javax.persistence.*;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import java.util.Map;
import net.timxekhach.operation.response.ErrorCode;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(PathPoint_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class PathPoint_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long pathPointId;

    protected Long getIncrementId() {
        return this.pathPointId;
    }
    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long pathId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long locationId;

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long companyId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long pathPointId;
        protected Long pathId;
        protected Long locationId;
        protected Long companyId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long pathPointIdLong = Long.parseLong(data.get("pathPointId"));
            Long pathIdLong = Long.parseLong(data.get("pathId"));
            Long locationIdLong = Long.parseLong(data.get("locationId"));
            Long companyIdLong = Long.parseLong(data.get("companyId"));
            if(NumberUtils.min(new long[]{pathPointIdLong, pathIdLong, locationIdLong, companyIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new PathPoint_MAPPED.Pk(pathPointIdLong, pathIdLong, locationIdLong, companyIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new PathPoint_MAPPED.Pk(0L, 0L, 0L, 0L);
    }

    protected PathPoint_MAPPED(){}
    protected PathPoint_MAPPED(Location location, Path path) {
        this.setLocation(location);
        this.setPath(path);
    }
//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "locationId",
        referencedColumnName = "locationId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "locationId")
    protected Location location;

    public Location getLocation(){
        if (this.location == null) {
            this.location = CommonUpdateService.getLocationRepository().findByLocationId(this.locationId);
        }
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
        if(location == null) {
            this.locationId = null;
            return;
        }
        this.locationId = location.getLocationId();
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "companyId",
        referencedColumnName = "companyId",
        insertable = false,
        updatable = false), 
        @JoinColumn(
        name = "pathId",
        referencedColumnName = "pathId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "pathId")
    protected Path path;

    public Path getPath(){
        if (this.path == null) {
            this.path = CommonUpdateService.getPathRepository().findByPathId(this.pathId);
        }
        return this.path;
    }

    public void setPath(Path path) {
        this.path = path;
        if(path == null) {
            this.companyId = null;
            this.pathId = null;
            return;
        }
        this.companyId = path.getCompanyId();
        this.pathId = path.getPathId();
    }

//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//

    @Size(max = 255)
    protected String pointName;
    @Size(max = 255)
    protected String pointDesc;

    protected Integer pointOrder = 0;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("pointName")) {
                this.setPointName(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("pointDesc")) {
                this.setPointDesc(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("pointOrder")) {
                this.setPointOrder(Integer.valueOf(value));
                continue;
            }
            if (fieldName.equals("location")) {
                this.setLocation(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getLocationRepository().findByLocationId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("path")) {
                this.setPath(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getPathRepository().findByPathId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("pathPointId")) {
                this.pathPointId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("pathId")) {
                this.pathId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("locationId")) {
                this.locationId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("companyId")) {
                this.companyId = Long.valueOf(value);
            }
        }
    }
}
