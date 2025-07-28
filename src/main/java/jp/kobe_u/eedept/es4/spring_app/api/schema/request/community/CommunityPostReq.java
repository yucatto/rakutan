package jp.kobe_u.eedept.es4.spring_app.api.schema.request.community;

import lombok.Data;

@Data
public class CommunityPostReq {
    private String communityId;
    private String communityName;
}
