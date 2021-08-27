package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.SeatGroup_MAPPED;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Transient;
import net.timxekhach.utility.XeNumberUtils;
import net.timxekhach.utility.XeObjectUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class SeatGroup extends SeatGroup_MAPPED {

    public SeatGroup() {}
    public SeatGroup(BussType bussType) {
        super(bussType);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    @JsonIgnore
    public BussType getBussType() {
        return super.getBussType();
    }

    private boolean isValidSeatRange() {
        return this.seatGroupOrder != null && this.totalSeats != null && this.seatGroupOrder > 0 && this.totalSeats > 0;
    }

    public List<Integer> getSeats() {
        int from = this.seatFrom == null ? 0 : this.seatFrom;
        int to = from + (this.totalSeats == null ? 0 : this.totalSeats);
        return this.isValidSeatRange() ? IntStream.range(from, to).boxed().collect(Collectors.toList()) : new ArrayList<>();
    }

    @Override
    public void preSaveAction() {
        Set<Integer> allOrders = this.bussType.getSeatGroups()
                .stream()
                .map(SeatGroup_MAPPED::getSeatGroupOrder)
                .collect(Collectors.toSet());
        int max = allOrders.size() == 0 ? 0 : Collections.max(allOrders);
        if (allOrders.size() != this.getBussType().getSeatGroups().size()) {
            max = updateSetGroupOrder(max);
        }
        this.setSeatGroupOrder(max + 1);
        this.setSeatFrom(this.bussType.getTotalSeats() + 1);
    }

    private int updateSetGroupOrder(int max) {
        for (int i = 0; i < this.bussType.getSeatGroups().size(); i++) {
            SeatGroup group = this.bussType.getSeatGroups().get(i);
            if (group.getSeatGroupOrder() != i + 1) {
                group.setSeatGroupOrder(i + 1);
                group.save();
            }
            if (max < i) max = i + 1;
        }
        return max;
    }

    @Transient
    private Integer seatFromBefore;
    private Integer totalSeatsBefore;

    @Override
    public void preSetFieldAction() {
        this.seatFromBefore = this.seatFrom;
        this.totalSeatsBefore = this.totalSeats;
    }

    @Override
    public void postSetFieldAction() {
        if (XeObjectUtils.anyNull(this.seatFromBefore, this.seatFrom, this.totalSeatsBefore, this.totalSeats)
                || !this.seatFromBefore.equals(this.seatFrom)
                || !this.totalSeatsBefore.equals(this.totalSeats)) {
            updateSeatFrom();
        }
    }

    private void updateSeatFrom() {
        List<SeatGroup> sortedBySeatFrom = this.getBussType()
                .getSeatGroups().stream()
                .sorted((s1, s2) -> XeObjectUtils.anyNull(s2.seatFrom, s1.seatFrom) ? 0 : s1.seatFrom - s2.seatFrom)
                .collect(Collectors.toList());
        int seatFrom = 1;
        for (SeatGroup seatGroup : sortedBySeatFrom) {
            if (seatGroup.seatFrom == null || seatGroup.seatFrom != seatFrom) {
                seatGroup.setSeatFrom(seatFrom);
                seatGroup.save();
            }
            seatFrom += XeNumberUtils.zeroIfNull(seatGroup.totalSeats);
        }
        if (this.seatGroupId == null) {
            this.seatFrom = seatFrom;
        }
    }

    @Override
    public void preUpdateAction() {

    }

    @Override
    public void preRemoveAction() {
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

