package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
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
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
