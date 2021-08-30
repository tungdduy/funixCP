package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.timxekhach.operation.data.entity.BussType;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Map;
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
                if(value == null) {this.setSeatGroupOrder(null); continue;}
                if(value.equals(this.getSeatGroupOrder())) continue;
                this.setSeatGroupOrder(Integer.valueOf(value));
                continue;
            }
            if (fieldName.equals("seatGroupName")) {
                if(value == null) {this.setSeatGroupName(null); continue;}
                if(value.equals(this.getSeatGroupName())) continue;
                this.setSeatGroupName(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("seatGroupDesc")) {
                if(value == null) {this.setSeatGroupDesc(null); continue;}
                if(value.equals(this.getSeatGroupDesc())) continue;
                this.setSeatGroupDesc(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("totalSeats")) {
                if(value == null) {this.setTotalSeats(null); continue;}
                if(value.equals(this.getTotalSeats())) continue;
                this.setTotalSeats(Integer.valueOf(value));
                continue;
            }
            if (fieldName.equals("seatFrom")) {
                if(value == null) {this.setSeatFrom(null); continue;}
                if(value.equals(this.getSeatFrom())) continue;
                this.setSeatFrom(Integer.valueOf(value));
                continue;
            }
            if (fieldName.equals("bussType")) {
                if(value == null) {this.setBussType(null); continue;}
                this.setBussType(ErrorCode.DATA_NOT_FOUND.throwIfNull(CommonUpdateService.getBussTypeRepository().findByBussTypeId(Long.valueOf(value))));
                continue;
            }
            if (fieldName.equals("seatGroupId")) {
                this.seatGroupId = value == null ? null : Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = value == null ? null : Long.valueOf(value);
            }
        }
    }
}
