package com.example.OtpGenaration.repository;

import com.example.OtpGenaration.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Long> {
//    Otp findByEmailOrderByIdDesc(String email);

    Otp findTopByEmailOrderByIdDesc(String email);
}
