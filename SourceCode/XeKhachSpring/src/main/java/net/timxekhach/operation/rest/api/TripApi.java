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
	@GetMapping("/search-location/{searchString}")
	public ResponseEntity<List<Location>> searchLocation(@PathVariable("searchString") String searchString) {
		return success(tripService.searchLocation(searchString));
	}
	@GetMapping("/find-scheduled-locations/{mark0ForStart}/{startLocationId}/{endLocationId}/{searchString}")
	public ResponseEntity<List<Location>> findScheduledLocations(
			@PathVariable("mark0ForStart") Long mark0ForStart,
			@PathVariable("searchString") String searchString,
			@PathVariable("startLocationId") Long startLocationId,
			@PathVariable("endLocationId") Long endLocationId
	) {
		return success(tripService.findScheduledLocations(mark0ForStart, startLocationId, endLocationId, searchString));
	}
	@GetMapping("/find-buss-schedules/{launchDate}/{locationFromId}/{locationToId}")
	public ResponseEntity<List<BussSchedule>> findBussSchedules (@PathVariable("launchDate") String launchDate, @PathVariable("locationFromId") Long locationFromId, @PathVariable("locationToId") Long locationToId) {
		return success(tripService.findBussSchedules(launchDate, locationFromId, locationToId));
	}
	@GetMapping("/get-trip-with-prepared-trip-user/{tripId}/{tripUserId}")
	public ResponseEntity<Trip> getTripWithPreparedTripUser(@PathVariable("tripId") Long tripId,
													@PathVariable("tripUserId") Long tripUserId) {
		return success(tripService.getTripWithPreparedTripUser(tripId, tripUserId));
	}

	@GetMapping("/get-trip-users")
	public ResponseEntity<List<TripUser>> getTripUsers(@RequestParam Long userId,
													   @RequestParam String phones,
													   @RequestParam String emails) {
		List<String> phoneList = Arrays.asList(phones.split(","));
		List<String> emailList = Arrays.asList(emails.split(","));
		return success(tripService.getTripUsers(userId, phoneList, emailList));
	}

	@GetMapping("/get-trip-by-company-id/{companyId}")
	public ResponseEntity<List<Trip>> getTripByCompanyId(@PathVariable Long companyId) {
		return success(CommonUpdateService.getTripRepository().findByCompanyIdOrderByLaunchDateDesc(companyId));
	}

	@GetMapping("/get-buss-schedules-by-company-id/{companyId}")
	public ResponseEntity<List<BussSchedule>> getBussSchedulesByCompanyId(@PathVariable Long companyId) {
		return success(CommonUpdateService.getBussScheduleRepository().findByCompanyIdOrderByLaunchTimeDesc(companyId));
	}

	@GetMapping("/find-pending-trip-users/{managerUserId}")
	public ResponseEntity<List<TripUser>> findPendingTripUsers(@PathVariable Long managerUserId) {
		return success(tripService.findPendingTripUsers(managerUserId));
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
