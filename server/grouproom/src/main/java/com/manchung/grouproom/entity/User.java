package com.manchung.grouproom.entity;

import com.manchung.grouproom.entity.enums.Community;
import com.manchung.grouproom.entity.enums.UserRole;
import com.manchung.grouproom.entity.enums.converter.CommunityConverter;
import com.manchung.grouproom.entity.enums.converter.UserRoleConverter;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Convert(converter = UserRoleConverter.class)
    @Column(name = "role", nullable = false)
    private UserRole userRole;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "church_member_id", nullable = false)
    private Integer churchMemberId;

    @Column(name = "login_id", length = 50, nullable = false, unique = true)
    private String loginId;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Convert(converter = CommunityConverter.class)
    @Column(name = "community", nullable = false)
    private Community community;

    @Column(name = "is_signed_up", nullable = false)
    private Boolean isSignedUp;
}