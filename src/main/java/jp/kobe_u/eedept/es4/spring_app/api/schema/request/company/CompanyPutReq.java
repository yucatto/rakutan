package jp.kobe_u.eedept.es4.spring_app.api.schema.request.company;

import lombok.Data;

@Data
public class CompanyPutReq {
    private Long companyId;
    private String communityId;
    private String companyName;
    private String jobType;
}
