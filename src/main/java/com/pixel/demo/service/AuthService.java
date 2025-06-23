package com.pixel.demo.service;


import com.pixel.demo.dto.AuthReqRes;
import com.pixel.demo.repository.UserRepository;
import com.pixel.demo.security.JWTUtils;
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
    private final JWTUtils jwtUtils;

    public AuthReqRes signIn(AuthReqRes signinRequest) {
        AuthReqRes response = new AuthReqRes();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signinRequest.getUsername(), signinRequest.getPassword()));

            String username = signinRequest.getUsername();
            if (username.contains("@")) {
                userRepository.findByEmail(username).ifPresent(response::setUser);
            } else {
                userRepository.findByPhone(username).ifPresent(response::setUser);
            }
            String token = jwtUtils.generateToken(response.getUser().getId());
            response.setToken(token);
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
