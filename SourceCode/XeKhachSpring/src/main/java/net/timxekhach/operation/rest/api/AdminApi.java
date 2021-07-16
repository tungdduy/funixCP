package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import net.timxekhach.operation.rest.service.AdminService;
import static net.timxekhach.utility.XeResponseUtils.success;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import net.timxekhach.operation.data.entity.User;
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
