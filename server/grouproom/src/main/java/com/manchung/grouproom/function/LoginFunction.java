package com.manchung.grouproom.function;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.manchung.grouproom.function.request.LoginRequest;
import com.manchung.grouproom.function.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class LoginFunction implements Function<LoginRequest, LoginResponse> {
    private final AWSCognitoIdentityProvider cognitoClient;

    @Value("${aws.cognito.client-id}")
    private String clientId;

    @Autowired
    public LoginFunction(AWSCognitoIdentityProvider cognitoClient) {
        this.cognitoClient = cognitoClient;
    }

    @Override
    public LoginResponse apply(LoginRequest request) {
        try {
            AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                    .withClientId(clientId)
                    .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                    .withAuthParameters(Map.of(
                            "EMAIL", request.getEmail(),
                            "PASSWORD", request.getPassword()
                    ));

            AdminInitiateAuthResult authResult = cognitoClient.adminInitiateAuth(authRequest);

            return new LoginResponse(
                    authResult.getAuthenticationResult().getAccessToken(),
                    authResult.getAuthenticationResult().getRefreshToken(),
                    authResult.getAuthenticationResult().getIdToken()
            );

        } catch (Exception e) {
            return new LoginResponse(null, null, "Login failed: " + e.getMessage());
        }
    }
}
