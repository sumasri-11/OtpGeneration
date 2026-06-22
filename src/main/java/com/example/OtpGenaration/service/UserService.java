package com.example.OtpGenaration.service;

import com.example.OtpGenaration.entity.User;
import com.example.OtpGenaration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setVerified(false);
        return userRepository.save(user);
    }

    public User verifyUser(String email){
      User user=getUserByEmail(email);
      user.setVerified(true);
        return userRepository.findByEmail(email);

    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);

    }

    public User findUserById(Long id){
        return userRepository.findById(id).get();
    }
    public User updateUser(User user){
       User user1 = userRepository.findById(user.getId())
               .orElseThrow(()->new RuntimeException("user not found"));
       user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        return userRepository.save(user1);
    }

    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        userRepository.delete(user);
    }

}
