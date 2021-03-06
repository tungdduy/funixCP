package net.timxekhach.operation.data.mapped;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import net.timxekhach.operation.data.entity.SeatGroup;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.data.mapped.abstracts.XePk;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //


@MappedSuperclass @Getter @Setter
@IdClass(BussType_MAPPED.Pk.class)
@SuppressWarnings("unused")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class BussType_MAPPED extends XeEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PRIVATE)
    protected Long bussTypeId;

    protected Long getIncrementId() {
        return this.bussTypeId;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk extends XePk {
        protected Long bussTypeId;
    }

    public static Pk pk(Map<String, String> data) {
        try {
            Long bussTypeIdLong = Long.parseLong(data.get("bussTypeId"));
            if(NumberUtils.min(new long[]{bussTypeIdLong}) < 1) {
                ErrorCode.DATA_NOT_FOUND.throwNow();
            }
            return new BussType_MAPPED.Pk(bussTypeIdLong);
        } catch (Exception ex) {
            ErrorCode.DATA_NOT_FOUND.throwNow();
        }
        return new BussType_MAPPED.Pk(0L);
    }

//====================================================================//
//======================== END of PRIMARY KEY ========================//
//====================================================================//
    @OneToMany(
        mappedBy = "bussType",
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @OrderBy("seatGroupOrder DESC")
    protected List<SeatGroup> seatGroups = new ArrayList<>();
//====================================================================//
//==================== END of MAP COLUMN ENTITY ======================//
//====================================================================//
    public Integer getTotalBusses() {
        return CommonUpdateService.getBussRepository().countBussIdByBussTypeId(this.bussTypeId);
    }
//=====================================================================//
//==================== END of MAP COUNT ENTITIES ======================//
//====================================================================//

    @Size(max = 255)
    protected String bussTypeName;
    @Size(max = 255)
    protected String bussTypeDesc;
//====================================================================//
//====================== END of BASIC COLUMNS ========================//
//====================================================================//

    protected void _setFieldByName(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (fieldName.equals("bussTypeName")) {
                if(value == null) {this.setBussTypeName(null); continue;}
                if(value.equals(this.getBussTypeName())) continue;
                this.setBussTypeName(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("bussTypeDesc")) {
                if(value == null) {this.setBussTypeDesc(null); continue;}
                if(value.equals(this.getBussTypeDesc())) continue;
                this.setBussTypeDesc(String.valueOf(value));
                continue;
            }
            if (fieldName.equals("bussTypeId")) {
                this.bussTypeId = value == null ? null : Long.valueOf(value);
            }
        }
    }
}
