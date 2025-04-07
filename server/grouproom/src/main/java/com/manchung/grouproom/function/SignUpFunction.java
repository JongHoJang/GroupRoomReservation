package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.Community;
import com.manchung.grouproom.entity.enums.UserRole;
import com.manchung.grouproom.error.CustomException;
import com.manchung.grouproom.error.ErrorCode;
import com.manchung.grouproom.function.request.SignUpRequest;
import com.manchung.grouproom.function.response.SignUpResponse;
import com.manchung.grouproom.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.function.Function;

@Slf4j
@Component
@AllArgsConstructor
public class SignUpFunction implements Function<SignUpRequest, SignUpResponse> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public SignUpResponse apply(SignUpRequest request) {
        log.info("[SignUp] Sign-up attempt: name={}, birthday={}, churchMemberId={}, communityCode={}",
                request.getName(), request.getBirthday(), request.getChurchMemberId(), request.getCommunityCode());

        LocalDate birthday = LocalDate.parse(request.getBirthday());
        Community community = Community.fromCode(request.getCommunityCode());

        User user = userRepository.findByNameAndBirthdayAndChurchMemberIdAndCommunity(
                request.getName(), birthday, request.getChurchMemberId(), community
        ).orElseThrow(() -> {
            log.warn("[SignUp] User info mismatch - no matching user found for sign-up");
            return new CustomException(ErrorCode.SIGNUP_WRONG_INFORMATION);
        });

        if (user.getUserRole().equals(UserRole.USER_CANNOT_SIGNUP)) {
            log.warn("[SignUp] Cannot Signup - user role is USER_CANNOT_SIGNUP");
            throw new CustomException(ErrorCode.CANNOT_SIGNUP);
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPassword(encryptedPassword);
        user.setIsSignedUp(true);
        userRepository.save(user);

        log.info("[SignUp] Sign-up completed: userId={}, email={}", user.getUserId(), user.getEmail());
        return new SignUpResponse("회원가입이 완료되었습니다", true);
    }
}
