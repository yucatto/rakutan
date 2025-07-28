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
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.user.UserDeleteReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.user.UserGetReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.user.UserPostReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.user.UserPutReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.response.user.UserRes;
import jp.kobe_u.eedept.es4.spring_app.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Create
    @PostMapping
    public ResponseEntity<UserRes> createUser(
            @Valid @RequestBody UserPostReq req,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.createUser(req));
    }

    // Read (Single)
    @GetMapping
    public ResponseEntity<UserRes> getUser(
            @Valid UserGetReq req,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.getUser(req));
    }

    // Read (All)
    @GetMapping("/all")
    public ResponseEntity<List<UserRes>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    // Update
    @PutMapping
    public ResponseEntity<UserRes> updateUser(
            @Valid @RequestBody UserPutReq req,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.updateUser(req));
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            @Valid @RequestBody UserDeleteReq req,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        userService.deleteUser(req);
        return ResponseEntity.noContent().build();
    }
}
