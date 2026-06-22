package com.example.OtpGenaration.service;

import com.example.OtpGenaration.entity.Otp;
import com.example.OtpGenaration.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {
    @Autowired
    private OtpRepository otpRepository;

    public String generatedOtp(String email) {
        String otp=String.valueOf((int)(Math.random()*900000)+100000);
        Otp otp1=new Otp();
        otp1.setEmail(email);
        otp1.setOtp(otp);
        otp1.setVerified(false);
        otp1.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otp1);
        return otp;
    }
    public boolean verifyOtp(String email, String enterOtp) {
        Otp otp1=otpRepository.findTopByEmailOrderByIdDesc(email);
        if(otp1.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new RuntimeException("OTP expired");
        }else if(!otp1.getOtp().equals(enterOtp)){
            throw new RuntimeException("OTP invalid");
        } else if (otp1.getOtp().equals(enterOtp)) {
            otp1.setVerified(true);
            otpRepository.save(otp1);
            return true;
        }

        return false;
    }



}
