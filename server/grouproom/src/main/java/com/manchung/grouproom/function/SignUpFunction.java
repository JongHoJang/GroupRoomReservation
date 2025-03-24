package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.Community;
import com.manchung.grouproom.error.CustomException;
import com.manchung.grouproom.error.ErrorCode;
import com.manchung.grouproom.function.request.SignUpRequest;
import com.manchung.grouproom.function.response.SignUpResponse;
import com.manchung.grouproom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class SignUpFunction implements Function<SignUpRequest, SignUpResponse> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public SignUpResponse apply(SignUpRequest request) {
        // 1️⃣ 사용자 정보 확인 (기존 DB에서 사용자 존재 여부 체크)
        LocalDate birthday = LocalDate.parse(request.getBirthday());
        Community community = Community.fromCode(request.getCommunityCode());

        User user = userRepository.findByNameAndBirthdayAndChurchMemberIdAndCommunity(
                request.getName(), birthday, request.getChurchMemberId(), community
        ).orElseThrow(() -> new CustomException(ErrorCode.SIGNUP_WRONG_INFORMATION));

        // 2️⃣ 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPassword(encryptedPassword);
        user.setIsSignedUp(true);
        userRepository.save(user);

        return new SignUpResponse("회원가입이 완료되었습니다", true);
    }
}
