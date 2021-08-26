package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.BussType;
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
@IdClass(SeatGroup_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class SeatGroup_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long seatGroupId;

    protected Long getIncrementId() {
        return this.seatGroupId;
    }
    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussTypeId;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long seatGroupId;
        protected Long bussTypeId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long seatGroupIdLong = Long.parseLong(data.get("seatGroupId"));
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            if(NumberUtils.min(new long[]{seatGroupIdLong, bussTypeIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new SeatGroup_MAPPED.Pk(seatGroupIdLong, bussTypeIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new SeatGroup_MAPPED.Pk(0L, 0L);
    }

    protected SeatGroup_MAPPED(){}
    protected SeatGroup_MAPPED(BussType bussType) {
        this.setBussType(bussType);
    }
//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
        name = "bussTypeId",
        referencedColumnName = "bussTypeId",
        insertable = false,
        updatable = false)
    })
    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "bussTypeId")
    protected BussType bussType;

    public BussType getBussType(){
        if (this.bussType == null) {
            this.bussType = CommonUpdateService.getBussTypeRepository().findByBussTypeId(this.bussTypeId);
        }
        return this.bussType;
    }

    public void setBussType(BussType bussType) {
        this.bussType = bussType;
        if(bussType == null) {
            this.bussTypeId = null;
            return;
        }
        this.bussTypeId = bussType.getBussTypeId();
    }

//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//


    protected Integer seatGroupOrder = 0;
    @Size(max = 255)
    protected String seatGroupName;
    @Size(max = 255)
    protected String seatGroupDesc;

    protected Integer totalSeats = 0;

    protected Integer seatFrom = 0;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("seatGroupOrder")) {
                this.setSeatGroupOrder(Integer.valueOf(value));
                continue;
            }
            if (fieldName.equals("seatGroupName")) {
                this.setSeatGroupName(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("seatGroupDesc")) {
                this.setSeatGroupDesc(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("totalSeats")) {
                this.setTotalSeats(Integer.valueOf(value));
                continue;
            }
            if (fieldName.equals("seatFrom")) {
                this.setSeatFrom(Integer.valueOf(value));
                continue;
            }
            if (fieldName.equals("bussType")) {
                this.setBussType(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getBussTypeRepository().findByBussTypeId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("seatGroupId")) {
                this.seatGroupId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = Long.valueOf(value);
            }
        }
    }
}
