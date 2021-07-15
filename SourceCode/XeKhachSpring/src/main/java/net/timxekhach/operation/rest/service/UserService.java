package net.timxekhach.operation.rest.service;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.operation.data.entity.Seat;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.data.repository.BussRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Map;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

// ____________________ ::BODY_SEPARATOR:: ____________________ //
	private final EntityManagerFactory entityManagerFactory;

	@Transactional
	public User login (Map<String, String> info) {
		// TODO: service login method
		EntityManager em = entityManagerFactory.createEntityManager();
		em.joinTransaction();
		Buss b = new Buss();
		b.setBussDesc("dhashd");
		em.persist(b);
		em.flush();
		Seat s = new Seat();
		s.setBussId(b.getBussId());
		em.persist(s);
		em.flush();
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
