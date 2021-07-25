package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.TripUser;
import org.springframework.web.bind.annotation.*;
import net.timxekhach.operation.rest.service.BussOperationService;
import static net.timxekhach.utility.XeResponseUtils.success;
import org.springframework.http.ResponseEntity;

import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/buss-operation"})
public class BussOperationApi {

    private final BussOperationService bussOperationService;

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	@PostMapping("/findBussPoint")
	public ResponseEntity<List> findBussPoint (@RequestParam("desc") String desc) {
		return success(bussOperationService.findBussPoint(desc));
	}

	@PostMapping("/findBuss")
	public ResponseEntity<List> findBuss (@RequestParam("departureTime") Long departureTime, @RequestParam("endPoint") Long endPoint, @RequestParam("startPoint") Long startPoint) {
		return success(bussOperationService.findBuss(departureTime, endPoint, startPoint));
	}
	@PostMapping("/countAvailableSeat")
	public ResponseEntity<Integer> countAvailableSeat (@RequestBody Long tripId) {
		return success(bussOperationService.countAvailableSeat(tripId));
	}

	@PostMapping("/getAvailableSeat")
	public ResponseEntity<List> getAvailableSeat (@RequestBody Long tripId) {
		return success(bussOperationService.getAvailableSeat(tripId));
	}
	@PostMapping("/bookTicket")
	public ResponseEntity<TripUser> bookTicket (@RequestBody List seatIDs, @RequestBody Long tripID, @RequestBody Long userID) {
		return success(bussOperationService.bookTicket(seatIDs, tripID, userID));
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
