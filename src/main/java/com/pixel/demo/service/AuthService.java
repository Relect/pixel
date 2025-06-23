package com.pixel.demo.service;


import com.pixel.demo.dto.AuthReqRes;
import com.pixel.demo.repository.UserRepository;
import com.pixel.demo.security.CustomUserDetails;
import com.pixel.demo.security.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        String username = signinRequest.getUsername();
        String password = signinRequest.getPassword();
        response.setUsername(username);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Long id = customUserDetails.getUser().getId();
            String token = jwtUtils.generateToken(id);

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
