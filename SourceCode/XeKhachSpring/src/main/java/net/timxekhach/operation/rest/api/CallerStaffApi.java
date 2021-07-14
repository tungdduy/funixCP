package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.rest.service.CallerStaffService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/caller-staff"})
public class CallerStaffApi {

    private final CallerStaffService callerStaffService;

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
