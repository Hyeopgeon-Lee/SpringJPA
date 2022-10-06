package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserInfoDTO implements Serializable {

    private String userId;
    private String userName;
    private String password;
    private String email;
    private String addr1;
    private String addr2;
    private String regId;
    private String regDt;
    private String chgId;
    private String chgDt;
    private String roles;
}

