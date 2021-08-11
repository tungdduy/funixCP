package net.timxekhach.operation.rest.service;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.operation.data.repository.BussPointRepository;
import net.timxekhach.operation.data.repository.BussScheduleRepository;
import net.timxekhach.operation.data.repository.TripRepository;
import net.timxekhach.utility.XeDateUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
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
	private final BussScheduleRepository bussScheduleRepository;
	private final BussPointRepository bussPointRepository;

//	public List<SeatType> availableSeats (Map<String, String> data) {
//		Trip trip = ErrorCode.TRIP_NOT_FOUND.throwIfNull(tripRepository.getById(Trip.pk(data)));
//		return trip.availableSeats();
//	}
	public List<BussSchedule> availableTrips (Map<String, String> data) {
		String departureDateStr = data.get("departureDate");
		Long departurePoint = NumberUtils.toLong(data.get("departurePoint"), 0L);
		Long destinationPoint = NumberUtils.toLong(data.get("destinationPoint"), 0L);

		List<BussSchedule> availableTrips = new ArrayList<>();

		try {
			Date departureDate = DateUtils.parseDate(departureDateStr);

			int weekDay = XeDateUtils.getDayOfWeek(departureDate);

			BussPoint startPoint = bussPointRepository.findByBussPointId(departurePoint);
			BussPoint endPoint = bussPointRepository.findByBussPointId(destinationPoint);

			switch (weekDay){
				case 1:
					availableTrips = bussScheduleRepository
							.findByStartPointAndEndPointAndEffectiveDateFromLessThanEqualAndSundayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 2:
					availableTrips = bussScheduleRepository
							.findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndMondayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 3:
					availableTrips = bussScheduleRepository
							.findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndTuesdayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 4:
					availableTrips = bussScheduleRepository
							.findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndWednesdayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 5:
					availableTrips = bussScheduleRepository
							.findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndThursdayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 6:
					availableTrips = bussScheduleRepository
							.findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndFridayIsTrue(startPoint, endPoint, departureDate);
					break;
				case 7:
					availableTrips = bussScheduleRepository
							.findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndSaturdayIsTrue(startPoint, endPoint, departureDate);
					break;
			}
		} catch (ParseException e) {
			ErrorCode.VALIDATOR_PATTERN_INVALID.throwNow("departureDate");
		}
		return availableTrips;
	}

	/**
	 * Searching buss schedule by description that appear in either
	 * start point or end point and effective date is less than today (mean in running)
	 * @param description
	 * @return
	 */
	public List searchBuss (String description) {
		return bussScheduleRepository.findBussScheduleByBussPointDesc(description);
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
