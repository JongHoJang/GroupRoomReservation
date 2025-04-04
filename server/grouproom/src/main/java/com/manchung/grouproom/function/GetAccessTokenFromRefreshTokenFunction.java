package com.manchung.grouproom.function;

import com.manchung.grouproom.Security.JwtProvider;
import com.manchung.grouproom.entity.RefreshToken;
import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.error.CustomException;
import com.manchung.grouproom.error.ErrorCode;
import com.manchung.grouproom.function.request.RefreshTokenRequest;
import com.manchung.grouproom.function.response.RefreshTokenResponse;
import com.manchung.grouproom.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetAccessTokenFromRefreshTokenFunction implements Function<RefreshTokenRequest, RefreshTokenResponse> {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshTokenResponse apply(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        log.info("[AccessToken Reissue] Received refresh token: {}", refreshToken);

        if (!jwtProvider.validateToken(refreshToken)) {
            log.warn("[AccessToken Reissue] Refresh token is expired or invalid: {}", refreshToken);
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> {
                    log.warn("[AccessToken Reissue] Refresh token not found in DB: {}", refreshToken);
                    return new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
                });

        User user = storedToken.getUser();
        String newAccessToken = jwtProvider.createAccessToken(user.getUserId());
        log.info("[AccessToken Reissue] New access token issued for userId={}", user.getUserId());

        return new RefreshTokenResponse(newAccessToken, refreshToken);
    }
}