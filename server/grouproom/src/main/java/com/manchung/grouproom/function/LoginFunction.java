package com.manchung.grouproom.function;

import com.manchung.grouproom.Security.JwtProvider;
import com.manchung.grouproom.entity.RefreshToken;
import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.error.CustomException;
import com.manchung.grouproom.error.ErrorCode;
import com.manchung.grouproom.function.request.LoginRequest;
import com.manchung.grouproom.function.response.LoginResponse;
import com.manchung.grouproom.repository.RefreshTokenRepository;
import com.manchung.grouproom.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
@AllArgsConstructor
public class LoginFunction implements Function<LoginRequest, LoginResponse> {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    private static final long TOKEN_EXPIRE_MINUTE = 5;

    @Transactional
    @Override
    public LoginResponse apply(LoginRequest request) {
        log.info("[Login] Login attempt with email={}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("[Login] No user found with email={}", request.getEmail());
                    return new CustomException(ErrorCode.INVALID_EMAIL);
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("[Login] Password mismatch for userId={}", user.getUserId());
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        String accessToken = jwtProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserId());
        log.info("[Login] Tokens issued for userId={}", user.getUserId());

        Optional<RefreshToken> existing = refreshTokenRepository.findByUser(user);
        if (existing.isPresent()) {
            RefreshToken existingToken = existing.get();
            existingToken.setToken(refreshToken);
            existingToken.setCreatedAt(LocalDateTime.now());
            existingToken.setExpiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRE_MINUTE));
            refreshTokenRepository.save(existingToken);
            log.info("[Login] Existing refresh token updated for userId={}", user.getUserId());
        } else {
            RefreshToken tokenEntity = RefreshToken.builder()
                    .user(user)
                    .token(refreshToken)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRE_MINUTE))
                    .build();
            refreshTokenRepository.save(tokenEntity);
            log.info("[Login] New refresh token saved for userId={}", user.getUserId());
        }

        log.info("[Login] Login successful for userId={}", user.getUserId());
        return new LoginResponse(user.getUserId(), accessToken, refreshToken);
    }
}