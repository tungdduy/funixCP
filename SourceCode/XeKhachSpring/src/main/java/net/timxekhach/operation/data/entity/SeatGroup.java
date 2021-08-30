package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.data.mapped.SeatGroup_MAPPED;
import net.timxekhach.utility.XeNumberUtils;
import net.timxekhach.utility.XeObjectUtils;

import javax.persistence.Entity;
import javax.persistence.Transient;
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
        int currentMaxGroupOrder = validateThenSetGroupOrder(null);
        this.setSeatGroupOrder(currentMaxGroupOrder + 1);
        this.setSeatFrom(this.getBussType().getTotalSeats() + 1);
    }

    private int validateThenSetGroupOrder(List<Long> excludeSeatGroupIds) {
        if(excludeSeatGroupIds == null) excludeSeatGroupIds = new ArrayList<>();
        List<Long> finalExcludeSeatGroupIds = excludeSeatGroupIds;
        Set<Integer> allOrders = this.getBussType().getSeatGroups()
                .stream()
                .filter(group -> !finalExcludeSeatGroupIds.contains(group.seatGroupId))
                .map(SeatGroup_MAPPED::getSeatGroupOrder)
                .collect(Collectors.toSet());
        int max = allOrders.size() == 0 ? 0 : Collections.max(allOrders);
        if (allOrders.size() != this.getBussType().getSeatGroups().size()) {
            max = updateSetGroupOrder(max, finalExcludeSeatGroupIds);
        }
        return max;
    }

    private int updateSetGroupOrder(int max, List<Long> excludeSeatGroupIds) {
        for (int i = 0; i < this.bussType.getSeatGroups().size(); i++) {
            SeatGroup group = this.bussType.getSeatGroups().get(i);
            if (group.getSeatGroupOrder() != i + 1) {
                group.setSeatGroupOrder(i + 1);
                group.updateSeatFrom(excludeSeatGroupIds);
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
            updateSeatFrom(null);
        }
    }

    private void updateSeatFrom(List<Long> excludeSeatGroupIds) {
        if(excludeSeatGroupIds == null) excludeSeatGroupIds = new ArrayList<>();
        List<Long> finalExcludeSeatGroupIds = excludeSeatGroupIds;
        List<SeatGroup> sortedBySeatFrom = this.getBussType()
                .getSeatGroups().stream()
                .filter(group -> !finalExcludeSeatGroupIds.contains(group.getSeatGroupId()))
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
    protected void preRemove() {
        validateThenSetGroupOrder(Collections.singletonList(this.getSeatGroupId()));
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

