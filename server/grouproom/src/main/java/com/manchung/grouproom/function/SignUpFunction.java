package com.manchung.grouproom.function;

import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.Community;
import com.manchung.grouproom.function.request.SignUpRequest;
import com.manchung.grouproom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class SignUpFunction implements Function<SignUpRequest, String> {
    private final UserRepository userRepository;

    @Override
    public String apply(SignUpRequest request) {
        LocalDate birthday = LocalDate.parse(request.getBirthday());
        Community community = Community.fromCode(request.getCommunityCode());

        User user = userRepository.findByNameAndBirthdayAndChurchMemberIdAndCommunity(
                request.getName(), birthday, request.getChurchMemberId(), community
        ).orElseThrow(() -> new IllegalArgumentException("입력하신 정보가 올바르지 않습니다."));

        user.setLoginId(request.getLoginId());
        user.setPassword(request.getPassword());
        user.setIsSignedUp(true);

        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }
}
