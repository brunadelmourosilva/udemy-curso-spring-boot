package br.com.brunadelmouro.cursospringboot.resources;


import br.com.brunadelmouro.cursospringboot.security.JWTUtil;
import br.com.brunadelmouro.cursospringboot.security.UserSS;
import br.com.brunadelmouro.cursospringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();

        String token = jwtUtil.generateToken(user.getUsername());

        response.addHeader("Authorization", "Bearer " + token);

        return ResponseEntity.noContent().build();
    }
}
