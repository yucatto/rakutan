package jp.kobe_u.eedept.es4.spring_app.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.kobe_u.eedept.es4.spring_app.database.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByCommunityIdAndCompanyName(String communityId, String companyName);
}
