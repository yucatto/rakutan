package jp.kobe_u.eedept.es4.spring_app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jp.kobe_u.eedept.es4.spring_app.api.schema.request.company.CompanyDeleteReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.company.CompanyGetReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.company.CompanyPostReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.company.CompanyPutReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.response.company.CompanyRes;
import jp.kobe_u.eedept.es4.spring_app.database.entities.Company;
import jp.kobe_u.eedept.es4.spring_app.database.repository.CompanyRepository;
import jp.kobe_u.eedept.es4.spring_app.exception.ConflictException;
import jp.kobe_u.eedept.es4.spring_app.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    // Create
    public CompanyRes createCompany(CompanyPostReq req) {
        if (checkCompanyExists(req.getCommunityId(), req.getCompanyName())) {
            throw new ConflictException("Company with this name already exists in the community");
        }
        Company company = new Company();
        company.setCommunityId(req.getCommunityId());
        company.setCompanyName(req.getCompanyName());
        company.setJobType(req.getJobType());
        company = companyRepository.save(company);
        return convertToRes(company);
    }

    // Read (Single)
    public CompanyRes getCompany(CompanyGetReq req) {
        Company company = companyRepository.findById(req.getCompanyId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Company not found with ID: " + req.getCompanyId()));
        return convertToRes(company);
    }

    // Read (All)
    public List<CompanyRes> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(this::convertToRes)
                .collect(Collectors.toList());
    }

    // Read (by Community ID)
    public List<CompanyRes> getCompaniesByCommunityId(String communityId) {
        List<Company> companies = companyRepository.findByCommunityId(communityId);
        return companies.stream()
                .map(this::convertToRes)
                .collect(Collectors.toList());
    }

    // Update
    public CompanyRes updateCompany(CompanyPutReq req) {
        Long companyId = req.getCompanyId();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + companyId));

        company.setCommunityId(req.getCommunityId());
        company.setCompanyName(req.getCompanyName());
        company.setJobType(req.getJobType());
        company = companyRepository.save(company);
        return convertToRes(company);
    }

    // Delete
    public void deleteCompany(CompanyDeleteReq req) {
        Long companyId = req.getCompanyId();
        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException("Company not found with ID: " + companyId);
        }
        companyRepository.deleteById(companyId);
    }

    // Helper method to convert Entity to Response Schema
    private CompanyRes convertToRes(Company company) {
        CompanyRes res = new CompanyRes();
        res.setCompanyId(company.getCompanyId());
        res.setCommunityId(company.getCommunityId());
        res.setCompanyName(company.getCompanyName());
        res.setJobType(company.getJobType());
        return res;
    }

    // コミュニティIDと企業名が一致する企業が既にあるかを返す
    public boolean checkCompanyExists(String communityId, String companyName) {
        return companyRepository.existsByCommunityIdAndCompanyName(communityId, companyName);
    }
}
