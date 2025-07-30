package jp.kobe_u.eedept.es4.spring_app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jp.kobe_u.eedept.es4.spring_app.api.schema.request.community.CommunityDeleteReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.community.CommunityGetReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.community.CommunityPostReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.community.CommunityPutReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.response.community.CommunityRes;
import jp.kobe_u.eedept.es4.spring_app.database.entities.Community;
import jp.kobe_u.eedept.es4.spring_app.database.repository.CommunityRepository;
import jp.kobe_u.eedept.es4.spring_app.exception.ConflictException;
import jp.kobe_u.eedept.es4.spring_app.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    // Create
    public CommunityRes createCommunity(CommunityPostReq req) {
        if (communityRepository.existsById(req.getCommunityId())) {
            throw new ConflictException("Community with this ID already exists");
        }
        Community community = new Community();
        community.setCommunityId(req.getCommunityId());
        community.setCommunityName(req.getCommunityName());
        community = communityRepository.save(community);
        return convertToRes(community);
    }

    // Read (Single)
    public CommunityRes getCommunity(CommunityGetReq req) {
        Community community = communityRepository.findById(req.getCommunityId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Community not found with ID: " + req.getCommunityId()));
        return convertToRes(community);
    }

    // Read (All)
    public List<CommunityRes> getAllCommunities() {
        List<Community> communities = communityRepository.findAll();
        return communities.stream()
                .map(this::convertToRes)
                .collect(Collectors.toList());
    }

    // Update
    public CommunityRes updateCommunity(CommunityPutReq req) {
        String communityId = req.getCommunityId();
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new ResourceNotFoundException("Community not found with ID: " + communityId));

        community.setCommunityName(req.getCommunityName());
        community = communityRepository.save(community);
        return convertToRes(community);
    }

    // Delete
    public void deleteCommunity(CommunityDeleteReq req) {
        String communityId = req.getCommunityId();
        if (!communityRepository.existsById(communityId)) {
            throw new ResourceNotFoundException("Community not found with ID: " + communityId);
        }
        communityRepository.deleteById(communityId);
    }

    // Helper method to convert Entity to Response Schema
    private CommunityRes convertToRes(Community community) {
        CommunityRes res = new CommunityRes();
        res.setCommunityId(community.getCommunityId());
        res.setCommunityName(community.getCommunityName());
        return res;
    }
}
