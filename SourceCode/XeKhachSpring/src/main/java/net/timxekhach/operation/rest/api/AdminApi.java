package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.rest.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static net.timxekhach.utility.XeResponseUtils.success;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/admin"})
public class AdminApi {

    private final AdminService adminService;

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	@GetMapping("/list/{id}")
	public ResponseEntity<Void> list (@PathVariable("id") Long id) {
		adminService.list(id);
		return success();
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
