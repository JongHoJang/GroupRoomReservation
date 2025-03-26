package com.manchung.grouproom.function.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Integer userId;
    private String accessToken;
    private String refreshToken;
}
