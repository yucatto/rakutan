package jp.kobe_u.eedept.es4.spring_app.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.company.CompanyDeleteReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.company.CompanyGetReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.company.CompanyPostReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.company.CompanyPutReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.response.company.CompanyRes;
import jp.kobe_u.eedept.es4.spring_app.service.CompanyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    // Create
    @PostMapping
    public ResponseEntity<CompanyRes> createCompany(
            @Valid @RequestBody CompanyPostReq req,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(companyService.createCompany(req));
    }

    // Read (Single)
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyRes> getCompany(@PathVariable Long companyId) {
        CompanyGetReq req = new CompanyGetReq();
        req.setCompanyId(companyId);
        return ResponseEntity.ok(companyService.getCompany(req));
    }

    // Read (All)
    @GetMapping("/all")
    public ResponseEntity<List<CompanyRes>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    // Read (by Community ID)
    @GetMapping("/community/{communityId}")
    public ResponseEntity<List<CompanyRes>> getCompaniesByCommunityId(@PathVariable String communityId) {
        return ResponseEntity.ok(companyService.getCompaniesByCommunityId(communityId));
    }

    // Update
    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyRes> updateCompany(
            @PathVariable Long companyId,
            @Valid @RequestBody CompanyPutReq req,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        req.setCompanyId(companyId);
        return ResponseEntity.ok(companyService.updateCompany(req));
    }

    // Delete
    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long companyId) {
        CompanyDeleteReq req = new CompanyDeleteReq();
        req.setCompanyId(companyId);
        companyService.deleteCompany(req);
        return ResponseEntity.noContent().build();
    }
}
