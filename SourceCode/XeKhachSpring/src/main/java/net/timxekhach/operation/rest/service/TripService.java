package net.timxekhach.operation.rest.service;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import java.util.List;
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.entity.BussTrip;
import net.timxekhach.operation.data.entity.SeatType;
import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.repository.BussPointRepository;
import net.timxekhach.operation.data.repository.BussTripRepository;
import net.timxekhach.operation.data.repository.TripRepository;
import net.timxekhach.utility.XeDateUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import net.timxekhach.operation.data.entity.SeatType;
import net.timxekhach.operation.data.repository.TripRepository;
import lombok.RequiredArgsConstructor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

// ____________________ ::BODY_SEPARATOR:: ____________________ //

	private final TripRepository tripRepository;
	private final BussTripRepository bussTripRepository;
	private final BussPointRepository bussPointRepository;

	public List<SeatType> availableSeats (Map<String, String> data) {
		Trip trip = ErrorCode.TRIP_NOT_FOUND.throwIfNull(tripRepository.getById(Trip.pk(data)));
		return trip.availableSeats();
	}
	public List<BussTrip> availableTrips (Map<String, String> data) {
		String departureDateStr = data.get("departureDate");
		Long departurePoint = NumberUtils.toLong(data.get("departurePoint"), 0L);
		Long destinationPoint = NumberUtils.toLong(data.get("destinationPoint"), 0L);

		List<BussTrip> availableTrips = new ArrayList<>();

		try {
			Date departureDate = DateUtils.parseDate(departureDateStr);

			int weekDay = XeDateUtils.getDayOfWeek(departureDate);

			BussPoint startPoint = bussPointRepository.findByBussPointId(departurePoint);
			BussPoint endPoint = bussPointRepository.findByBussPointId(destinationPoint);

			switch (weekDay){
				case 1:
					availableTrips = bussTripRepository
							.findByStartPointAndEndPointAndEffectiveDateFromGreaterThanEqualAndSundayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 2:
					availableTrips = bussTripRepository
							.findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndMondayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 3:
					availableTrips = bussTripRepository
							.findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndTuesdayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 4:
					availableTrips = bussTripRepository
							.findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndWednesdayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 5:
					availableTrips = bussTripRepository
							.findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndThursdayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 6:
					availableTrips = bussTripRepository
							.findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndFridayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 7:
					availableTrips = bussTripRepository
							.findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndSaturdayIsTrue(startPoint, endPoint, departureDate);
					break;
			}
		} catch (ParseException e) {
			ErrorCode.VALIDATOR_PATTERN_INVALID.throwNow("departureDate");
		}
		return availableTrips;
	}

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
