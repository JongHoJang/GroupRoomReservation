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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class LoginFunction implements Function<LoginRequest, LoginResponse> {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse apply(LoginRequest request) {
        // 1️⃣ 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EMAIL));

        // 2️⃣ 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        // 3️⃣ AccessToken / RefreshToken 발급
        String accessToken = jwtProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserId());

        // 4️⃣ RefreshToken 저장
        RefreshToken tokenEntity = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(14))
                .build();

        refreshTokenRepository.save(tokenEntity);

        // 5️⃣ 응답
        return new LoginResponse(accessToken, refreshToken);
    }
}
