package net.timxekhach.operation.rest.service;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	public User login (Map<String, String> info) {
		// TODO: service login method
		return null;
	}

	public User register (User user) {
		// TODO: service register method
		return null;
	}

	public void forgotPassword (String email) {
		// TODO: service forgotPassword method
	}
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
