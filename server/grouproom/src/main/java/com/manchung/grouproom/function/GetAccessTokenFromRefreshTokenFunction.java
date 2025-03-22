package com.manchung.grouproom.function;

import com.manchung.grouproom.Security.JwtProvider;
import com.manchung.grouproom.entity.RefreshToken;
import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.error.CustomException;
import com.manchung.grouproom.error.ErrorCode;
import com.manchung.grouproom.function.request.RefreshTokenRequest;
import com.manchung.grouproom.function.response.RefreshTokenResponse;
import com.manchung.grouproom.repository.RefreshTokenRepository;
import com.manchung.grouproom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class GetAccessTokenFromRefreshTokenFunction implements Function<RefreshTokenRequest, RefreshTokenResponse> {
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshTokenResponse apply(RefreshTokenRequest refreshTokenRequest) {
        if (!jwtProvider.validateToken(refreshTokenRequest.getRefreshToken())) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken())
                .orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (storedToken.isExpired()) {
            refreshTokenRepository.delete(storedToken);
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        User user = storedToken.getUser();
        String newAccessToken = jwtProvider.createAccessToken(user.getUserId());

        return new RefreshTokenResponse(newAccessToken, refreshTokenRequest.getRefreshToken());
    }
}
