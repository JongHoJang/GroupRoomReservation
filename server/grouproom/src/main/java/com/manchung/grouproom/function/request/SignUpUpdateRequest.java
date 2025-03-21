package com.manchung.grouproom.function.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpUpdateRequest {
    private String name;
    private String birthday; // "YYYY-MM-DD" 형식
    private Integer churchMemberId;
    private String email;
    private String password;
    private Integer communityCode; // 숫자로 전달
}
