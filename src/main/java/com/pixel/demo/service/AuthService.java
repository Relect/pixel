package com.pixel.demo.service;


import com.pixel.demo.dto.ReqRes;
import com.pixel.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public ReqRes signIn(ReqRes signinRequest) {
        ReqRes response = new ReqRes();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signinRequest.getUsername(), signinRequest.getPassword()));

            String username = signinRequest.getUsername();
            if (username.contains("@")) {
                userRepository.findByEmail(username).ifPresent(response::setUser);
            } else {
                userRepository.findByPhone(username).ifPresent(response::setUser);
            }
            response.setStatusCode(200);
            response.setMessage("Successfully Signed In");

        } catch (AuthenticationException e) {
            response.setStatusCode(403);
            response.setError(e.getMessage());

        } catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

}
