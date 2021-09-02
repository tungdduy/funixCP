package net.timxekhach.operation.rest.service;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.*;
import net.timxekhach.operation.data.repository.BussSchedulePointRepository;
import net.timxekhach.operation.data.repository.BussScheduleRepository;
import net.timxekhach.operation.data.repository.LocationRepository;
import net.timxekhach.operation.data.repository.TripRepository;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeDateUtils;
import net.timxekhach.utility.XeStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	private final TripRepository tripRepository;
	private final LocationRepository locationRepository;
	private final BussSchedulePointRepository bussSchedulePointRepository;
	private final BussScheduleRepository bussScheduleRepository;

	public List<Location> searchLocation (String searchString) {
		return locationRepository.findTop20BySearchTextContainsOrderByLocationIdDesc(searchString);
	}

	// search the first location
	public List<Location> findScheduledLocations(Long mark0ToFindStart, Long startLocationId, Long endLocationId, String searchString) {
		if(XeStringUtils.isBlank(searchString)) return new ArrayList<>();
		return this.findBussSchedulePointsContains(searchString)
				.stream()
				.filter(point -> {
					if(otherLocation(mark0ToFindStart, startLocationId, endLocationId) == 0) return true;
					List<Long> goThroughIds = point.getBussSchedule().getSortedBussSchedulePoints()
							.stream().map(BussSchedulePoint::getPathPoint)
							.map(PathPoint::getLocation)
							.map(Location::getLocationId)
							.collect(Collectors.toList());
					return goThroughIds.contains(otherLocation(mark0ToFindStart, startLocationId, endLocationId));
				}).map(BussSchedulePoint::getPathPoint)
				.map(pathPoint -> {
					Location location = pathPoint.getLocation();
					location.setDisplayName(pathPoint.getPointName());
					return location;
				})
				.distinct()
				.collect(Collectors.toList());
	}

	private Long otherLocation(Long mark0ToFindStart, Long startLocationId, Long endLocationId) {
		return mark0ToFindStart == 0 ? endLocationId : startLocationId;
	}

	private List<BussSchedulePoint> findBussSchedulePointsContains(String searchString) {
		return this.bussSchedulePointRepository.findTop20BySearchTextContains(searchString);
	}
	public List<BussSchedule> findBussSchedules (String launchDate, Long locationFromId, Long locationToId) {
		Date date = XeDateUtils.dateAppToApi(launchDate);
		return BussSchedule.findBussSchedules(locationFromId, locationToId, date);
	}

    public Trip getTripWithPreparedTripUser(Long tripId, Long tripUserId) {
		Trip trip = CommonUpdateService.getTripRepository().findByTripId(tripId);
		TripUser tripUser = CommonUpdateService.getTripUserRepository().findByTripUserId(tripUserId);
		ErrorCode.DATA_NOT_FOUND.throwIfAnyNull(trip, tripUser);
		trip.prepareTripUser(tripUser);
		return trip;
    }

    public List<TripUser> getTripUsers(Long userId, List<String> phoneList, List<String> emailList) {
		if(userId > 0) {
			return CommonUpdateService.getTripUserRepository().findByUserUserId(userId);
		} else if(!phoneList.isEmpty() || !emailList.isEmpty()) {
			return CommonUpdateService.getTripUserRepository().findByPhoneNumberInOrEmailIn(phoneList, emailList)
					.stream()
					.filter(tripUser -> tripUser.getUser() == null)
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
