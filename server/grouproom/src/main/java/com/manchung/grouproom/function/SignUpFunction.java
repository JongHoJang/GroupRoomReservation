package com.manchung.grouproom.function;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.Community;
import com.manchung.grouproom.function.request.SignUpUpdateRequest;
import com.manchung.grouproom.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class SignUpFunction implements Function<SignUpUpdateRequest, String> {
    private final AWSCognitoIdentityProvider cognitoClient;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${aws.cognito.client-id}")
    private String clientId;

    @Override
    public String apply(SignUpUpdateRequest request) {
        // 1️⃣ 사용자 정보 확인 (기존 DB에서 사용자 존재 여부 체크)
        LocalDate birthday = LocalDate.parse(request.getBirthday());
        Community community = Community.fromCode(request.getCommunityCode());

        User user = userRepository.findByNameAndBirthdayAndChurchMemberIdAndCommunity(
                request.getName(), birthday, request.getChurchMemberId(), community
        ).orElseThrow(() -> new IllegalArgumentException("입력하신 정보가 올바르지 않습니다."));

        // 2️⃣ 비밀번호 암호화

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPassword(encryptedPassword);
        user.setIsSignedUp(true);
        userRepository.save(user);

        // 3️⃣ Cognito에 사용자 등록
        SignUpRequest cognitoRequest = new SignUpRequest()
                .withClientId(clientId)
                .withUsername(request.getEmail())
                .withPassword(request.getPassword()) // 🔹 Cognito는 비밀번호 암호화를 자동 처리함
                .withUserAttributes(
                        (com.amazonaws.services.cognitoidp.model.AttributeType) List.of(AttributeType.builder().name("email").value(request.getEmail()),
                                AttributeType.builder().name("name").value(request.getName()))
                );

        cognitoClient.signUp(cognitoRequest);

        return "회원가입이 완료되었습니다!";
    }
}
