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
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.community.CommunityDeleteReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.community.CommunityGetReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.community.CommunityPostReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.community.CommunityPutReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.response.community.CommunityRes;
import jp.kobe_u.eedept.es4.spring_app.service.CommunityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    // Create
    @PostMapping
    public ResponseEntity<CommunityRes> createCommunity(
            @Valid @RequestBody CommunityPostReq req,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(communityService.createCommunity(req));
    }

    // Read (Single)
    @GetMapping
    public ResponseEntity<CommunityRes> getCommunity(
            @Valid CommunityGetReq req,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(communityService.getCommunity(req));
    }

    // Read (All)
    @GetMapping("/all")
    public ResponseEntity<List<CommunityRes>> getAllCommunities() {
        return ResponseEntity.ok(communityService.getAllCommunities());
    }

    // Update
    @PutMapping
    public ResponseEntity<CommunityRes> updateCommunity(
            @Valid @RequestBody CommunityPutReq req,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(communityService.updateCommunity(req));
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<Void> deleteCommunity(
            @Valid @RequestBody CommunityDeleteReq req,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        communityService.deleteCommunity(req);
        return ResponseEntity.noContent().build();
    }
}
