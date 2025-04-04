package com.manchung.grouproom.Security;

import com.manchung.grouproom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("[UserDetailsService] Attempting to load user by userId={}", userId);

        return userRepository.findById(Integer.parseInt(userId))
                .map(user -> {
                    log.info("[UserDetailsService] User found: userId={}", user.getUserId());
                    return new SecurityUserDetails(user);
                })
                .orElseThrow(() -> {
                    log.warn("[UserDetailsService] User not found: userId={}", userId);
                    return new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
                });
    }
}
