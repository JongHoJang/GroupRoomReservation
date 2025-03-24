package com.manchung.grouproom.repository;

import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.Community;
import com.manchung.grouproom.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // ✅ email로 사용자 찾기
    Optional<User> findByEmail(String email);

    // ✅ 이름, 생년월일, 교번, 공동체가 모두 일치하는 사용자 찾기
    Optional<User> findByNameAndBirthdayAndChurchMemberIdAndCommunity(
            String name, LocalDate birthday, Integer churchMemberId, Community community);

}
