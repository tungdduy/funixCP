package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.operation.data.entity.Location;
import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.entity.TripUser;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.operation.rest.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static net.timxekhach.utility.XeResponseUtils.success;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/trip"})
public class TripApi {

    private final TripService tripService;

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	@GetMapping("/searchLocation/{searchString}")
	public ResponseEntity<List<Location>> searchLocation(@PathVariable("searchString") String searchString) {
		return success(tripService.searchLocation(searchString));
	}
	@GetMapping("/findScheduledLocations/{mark0ForStart}/{startLocationId}/{endLocationId}/{searchString}")
	public ResponseEntity<List<Location>> findScheduledLocations(
			@PathVariable("mark0ForStart") Long mark0ForStart,
			@PathVariable("searchString") String searchString,
			@PathVariable("startLocationId") Long startLocationId,
			@PathVariable("endLocationId") Long endLocationId
	) {
		return success(tripService.findScheduledLocations(mark0ForStart, startLocationId, endLocationId, searchString));
	}
	@GetMapping("/findBussSchedules/{launchDate}/{locationFromId}/{locationToId}")
	public ResponseEntity<List<BussSchedule>> findBussSchedules (@PathVariable("launchDate") String launchDate, @PathVariable("locationFromId") Long locationFromId, @PathVariable("locationToId") Long locationToId) {
		return success(tripService.findBussSchedules(launchDate, locationFromId, locationToId));
	}
	@GetMapping("/getTripWithPreparedTripUser/{tripId}/{tripUserId}")
	public ResponseEntity<Trip> getTripWithPreparedTripUser(@PathVariable("tripId") Long tripId,
													@PathVariable("tripUserId") Long tripUserId) {
		return success(tripService.getTripWithPreparedTripUser(tripId, tripUserId));
	}

	@GetMapping("/getTripUsers")
	public ResponseEntity<List<TripUser>> getTripUsers(@RequestParam Long userId,
													   @RequestParam String phones,
													   @RequestParam String emails) {
		List<String> phoneList = Arrays.asList(phones.split(","));
		List<String> emailList = Arrays.asList(emails.split(","));
		return success(tripService.getTripUsers(userId, phoneList, emailList));
	}

	@GetMapping("/getTripByCompanyId/{companyId}")
	public ResponseEntity<List<Trip>> getTripByCompanyId(@PathVariable Long companyId) {
		return success(CommonUpdateService.getTripRepository().findByCompanyIdOrderByLaunchDateDesc(companyId));
	}

	@GetMapping("/getBussSchedulesByCompanyId/{companyId}")
	public ResponseEntity<List<BussSchedule>> getBussSchedulesByCompanyId(@PathVariable Long companyId) {
		return success(CommonUpdateService.getBussScheduleRepository().findByCompanyIdOrderByLaunchTimeDesc(companyId));
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
