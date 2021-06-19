package net.timxekhach.security;

import net.timxekhach.operation.entity.User;
import net.timxekhach.operation.repository.UserRepository;
import net.timxekhach.operation.response.ErrorCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    public UserDetailsServiceImpl(UserRepository userRepository, LoginAttemptService loginAttemptService) {
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            ErrorCode.NO_USER_FOUND_BY_USERNAME.throwNow(username);
        }
        validateLoginAttempt(user);
        return new UserDetailsImpl(user);
    }

    private void validateLoginAttempt(User user) {
        user.setNonLocked(!loginAttemptService.hasExceededMaxAttempts(user.getUsername()));
    }
}
