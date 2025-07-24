package org.example.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import org.example.entities.UserInfo;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class UserInfoDto extends UserInfo {
    private String userName; // user_name

    private String lastName;

    private Long phoneNumber;

    private String email;
}
