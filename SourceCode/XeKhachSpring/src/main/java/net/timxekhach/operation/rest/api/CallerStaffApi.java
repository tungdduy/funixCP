package net.timxekhach.operation.rest.api;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static net.timxekhach.utility.XeResponseUtils.success;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.rest.service.CallerStaffService;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/caller-staff"})
public class CallerStaffApi {

    private final CallerStaffService callerStaffService;

// ____________________ ::BODY_SEPARATOR:: ____________________ //



// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
