package jp.kobe_u.eedept.es4.spring_app.api.schema.response.company;

import lombok.Data;

@Data
public class CompanyRes {
    private Long companyId;
    private String communityId;
    private String companyName;
    private String jobType;
}
