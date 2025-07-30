package jp.kobe_u.eedept.es4.spring_app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jp.kobe_u.eedept.es4.spring_app.api.schema.request.community.CommunityDeleteReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.community.CommunityPutReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.user.UserDeleteReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.user.UserGetReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.user.UserPostReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.user.UserPutReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.response.user.UserRes;
import jp.kobe_u.eedept.es4.spring_app.database.entities.User;
import jp.kobe_u.eedept.es4.spring_app.database.repository.UserRepository;
import jp.kobe_u.eedept.es4.spring_app.exception.ConflictException;
import jp.kobe_u.eedept.es4.spring_app.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // Create つくるよ
    public UserRes createUser(UserPostReq req) {
        if (userRepository.existsById(req.getUserId())) {
            throw new ConflictException("User with this ID already exists");
        }
        User user = new User();
        user.setUserId(req.getUserId());
        user.setEmail(req.getEmail());
        user.setUserName(req.getUserName());
        user.setCommunityId(req.getCommunityId());
        user = userRepository.save(user);
        return convertToRes(user);
    }

    // Read (Single)
    public UserRes getUser(UserGetReq req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found with ID: " + req.getUserId()));
        return convertToRes(user);
    }

    // Read (All)
    public List<UserRes> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToRes)
                .collect(Collectors.toList());
    }

    // Update
    public UserRes updateUser(UserPutReq req) {
        String userId = req.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        user.setUserName(req.getUserName());
        user = userRepository.save(user);
        return convertToRes(user);
    }

    // Delete
    public void deleteUser(UserDeleteReq req) {
        String userId = req.getUserId();
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("user not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    // Helper method to convert Entity to Response Schema
    private UserRes convertToRes(User user) {
        UserRes res = new UserRes();
        res.setUserId(user.getUserId());
        res.setUserName(user.getUserName());
        res.setEmail(user.getEmail());
        res.setCommunityId(user.getCommunityId());
        return res;
    }
}
