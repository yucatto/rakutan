package jp.kobe_u.eedept.es4.spring_app.api.schema.request.user;

import lombok.Data;

@Data
public class UserPostReq {
    
    private String userId;
    private String userName;
    private String email;
}
