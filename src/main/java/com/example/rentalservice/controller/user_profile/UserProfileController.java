package com.example.rentalservice.controller.user_profile;

import com.example.rentalservice.aop.Secured;
import com.example.rentalservice.model.user_profile.CompleteInformationReqDTO;
import com.example.rentalservice.model.user_profile.UpdateInformationReqDTO;
import com.example.rentalservice.model.user_profile.UserPaperReqDTO;
import com.example.rentalservice.service.user_profile.UserPaperService;
import com.example.rentalservice.service.user_profile.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user-profile")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;

    private final UserPaperService userPaperService;

    @Secured
    @PostMapping("/complete-information")
    public ResponseEntity<Object> completeInformation(@RequestBody CompleteInformationReqDTO req) {
        userProfileService.completeInformation(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @GetMapping("/get-information")
    public ResponseEntity<Object> getInformation() {
        return new ResponseEntity<>(userProfileService.getInformation(), HttpStatus.OK);
    }

    @Secured
    @PostMapping("/update-information")
    public ResponseEntity<Object> updateInformation(@RequestBody UpdateInformationReqDTO req) {
        userProfileService.updateInformation(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @GetMapping("/get-paper/{id}")
    public ResponseEntity<Object> getPaper(@PathVariable String id) {
        return new ResponseEntity<>(userPaperService.getPaperById(id), HttpStatus.OK);
    }

    @Secured
    @PostMapping("/upload-paper")
    public ResponseEntity<Object> uploadPaper(UserPaperReqDTO req) {
        userPaperService.uploadPaper(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured
    @PostMapping("/upload-avatar")
    public ResponseEntity<Object> uploadPaper(@RequestParam MultipartFile file) {
        log.info("/user-profile/upload-avatar");
        return new ResponseEntity<>(userProfileService.uploadAvatar(file), HttpStatus.OK);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<Object> getUser(@PathVariable String username) {
        return new ResponseEntity<>(userProfileService.getUser(username), HttpStatus.OK);
    }

    @GetMapping("/get-by-keyword")
    public ResponseEntity<Object> getUserByKeyword(@RequestParam String keyword, @RequestParam String role) {
        return new ResponseEntity<>(userProfileService.getUserV2(keyword, role), HttpStatus.OK);
    }

    @Secured
    @GetMapping("/get")
    public ResponseEntity<Object> getUserV2(@RequestParam String username, @RequestParam String role) {
        return new ResponseEntity<>(userProfileService.getUser(username, role), HttpStatus.OK);
    }
}
