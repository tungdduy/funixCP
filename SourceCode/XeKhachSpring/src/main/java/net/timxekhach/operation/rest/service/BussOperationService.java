package net.timxekhach.operation.rest.service;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.SeatType;
import net.timxekhach.operation.data.entity.TripUser;
import net.timxekhach.operation.rest.service.busspoint.BussPointAbstractService;
import net.timxekhach.operation.rest.service.trip.SeatAbstractService;
import net.timxekhach.operation.rest.service.trip.TripAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Service
@Transactional
@RequiredArgsConstructor
public class BussOperationService {

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	private BussPointAbstractService 	bussPointAbstractService;
	private TripAbstractService tripAbstractService;
	private SeatAbstractService seatAbstractService;

	@Autowired
	@Qualifier("userBussPointService")
	public void setBussPointAbstractService(BussPointAbstractService bussPointAbstractService) {
		this.bussPointAbstractService = bussPointAbstractService;
	}

	@Autowired
	@Qualifier("userTripService")
	public void setTripAbstractService(TripAbstractService tripAbstractService) {
		this.tripAbstractService = tripAbstractService;
	}

	@Autowired
	@Qualifier("userSeatService")
	public void setSeatAbstractService(SeatAbstractService seatAbstractService) {
		this.seatAbstractService = seatAbstractService;
	}

	public List findBussPoint (String desc) {
		bussPointAbstractService.setDesc(desc);
		return bussPointAbstractService.getBusPointList();
	}

	public List findBuss (Long departureTime, Long endPoint, Long startPoint) {
		tripAbstractService.setStartTime(departureTime);
		tripAbstractService.setStartPoint(startPoint);
		tripAbstractService.setEndPoint(endPoint);

		return tripAbstractService.getTripList();
	}
	public Integer countAvailableSeat (Long tripId) {
		List<SeatType> seatTypeList = seatAbstractService.getAvailableSeats();
		return seatTypeList.size();
	}

	public List getAvailableSeat (Long tripId) {
		return seatAbstractService.getAvailableSeats();
	}
	public TripUser bookTicket (List seatIDs, Long tripID, Long userID) {
		// TODO : service bookTicket method
		return null;
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
