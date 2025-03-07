package com.manchung.grouproom.repository.mock;
import com.manchung.grouproom.entity.User;
import com.manchung.grouproom.entity.enums.Community;
import com.manchung.grouproom.entity.enums.UserRole;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MockUserRepository {

    private final List<User> users = new ArrayList<>();

    public MockUserRepository() {
        // ✅ 초기 유저 데이터 추가
        users.add(new User(1, UserRole.ADMIN, "관리자", LocalDate.of(1990, 1, 1), 1001, "admin", "password", Community.JOSEPH, true));
        users.add(new User(2, UserRole.USER, "홍길동", LocalDate.of(1995, 5, 10), 1002, "hong", "password", Community.DAVID, true));
        users.add(new User(3, UserRole.USER, "이순신", LocalDate.of(1988, 3, 15), 1003, "lee", "password", Community.ESTHER, true));
        users.add(new User(4, UserRole.USER, "김유신", LocalDate.of(1992, 7, 21), 1004, "kim", "password", Community.JOSHUA, true));
        users.add(new User(5, UserRole.USER, "강감찬", LocalDate.of(1997, 9, 30), 1005, "kang", "password", Community.DANIEL, true));
        users.add(new User(6, UserRole.USER, "정약용", LocalDate.of(1985, 12, 11), 1006, "jung", "password", Community.MOSES, true));
        users.add(new User(7, UserRole.USER, "세종대왕", LocalDate.of(1975, 4, 23), 1007, "sejong", "password", Community.PRISCILLA, true));
        users.add(new User(8, UserRole.USER, "장보고", LocalDate.of(1991, 6, 14), 1008, "jang", "password", Community.ESTHER, true));
        users.add(new User(9, UserRole.USER, "유관순", LocalDate.of(2000, 10, 5), 1009, "yu", "password", Community.DAVID, true));
        users.add(new User(10, UserRole.USER, "안중근", LocalDate.of(1998, 8, 20), 1010, "ahn", "password", Community.JOSHUA, true));
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findByLoginId(String loginId) {
        return users.stream().filter(user -> user.getLoginId().equals(loginId)).findFirst();
    }

    public Optional<User> findById(Integer userId) {
        return users.stream().filter(user -> user.getUserId().equals(userId)).findFirst();
    }
}
