package com.example.OtpGenaration.controller;


import com.example.OtpGenaration.dto.AuthResponse;
import com.example.OtpGenaration.dto.SendOtpRequest;
import com.example.OtpGenaration.dto.VerifyOtpRequest;
import com.example.OtpGenaration.entity.User;
import com.example.OtpGenaration.security.JwtUtil;
import com.example.OtpGenaration.service.EmailService;
import com.example.OtpGenaration.service.OtpService;
import com.example.OtpGenaration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/send-otp")
    public ResponseEntity<String> generatedOtp(
            @RequestBody SendOtpRequest request) {

        String otp =
                otpService.generatedOtp(
                        request.getEmail());

        emailService.sendOtpEmail(
                request.getEmail(),
                otp);

        return ResponseEntity.ok(
                "OTP Sent Successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(
            @RequestBody VerifyOtpRequest request) {

        boolean verified =
                otpService.verifyOtp(
                        request.getEmail(),
                        request.getOtp());

        if (!verified) {
            throw new RuntimeException(
                    "Invalid OTP");
        }

        User user =
                userService.getUserByEmail(
                        request.getEmail());

        if (user == null) {

            userService.createUser(
                    request.getEmail()
            );
        }

        String token =
                jwtUtil.generateAccessToken(
                        request.getEmail());
//
        return ResponseEntity.ok(new AuthResponse(token,"Otp verified successfully"));
}

}
