package com.manchung.grouproom.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CognitoConfig {
    @Value("${aws.cognito.user-pool-id}")
    private String userPoolId;

    @Value("${aws.cognito.client-id}")
    private String clientId;

    @Value("${aws.cognito.region}")
    private String region;
}
