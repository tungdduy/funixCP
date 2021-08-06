package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import net.timxekhach.operation.data.entity.BussType;
import lombok.*;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import org.apache.commons.lang3.math.NumberUtils;
import javax.persistence.*;
import java.util.Map;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import javax.validation.constraints.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(SeatType_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class SeatType_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussTypeId;

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long seatTypeId;

    protected Long getIncrementId() {
        return this.seatTypeId;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussTypeId;
        protected Long seatTypeId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            Long seatTypeIdLong = Long.parseLong(data.get("seatTypeId"));
            if(NumberUtils.min(new long[]{bussTypeIdLong, seatTypeIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new SeatType_MAPPED.Pk(bussTypeIdLong, seatTypeIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new SeatType_MAPPED.Pk(0L, 0L);
    }

    protected SeatType_MAPPED(){}
    protected SeatType_MAPPED(BussType bussType) {
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
        this.bussTypeId = bussType.getBussTypeId();
    }
//====================================================================//
//==================== END of PRIMARY MAP ENTITY =====================//
//====================================================================//

    @Size(max = 255)
    protected String name;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    public void setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("name")) {
                this.name = String.valueOf(value);
                continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = Long.valueOf(value);
                    continue;
            }
            if (fieldName.equals("seatTypeId")) {
                this.seatTypeId = Long.valueOf(value);
            }
        }
    }
}
