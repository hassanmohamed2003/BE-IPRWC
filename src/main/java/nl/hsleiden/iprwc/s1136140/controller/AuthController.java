package nl.hsleiden.iprwc.s1136140.controller;

import nl.hsleiden.iprwc.s1136140.DAO.RoleDAO;
import nl.hsleiden.iprwc.s1136140.model.database.Role;
import nl.hsleiden.iprwc.s1136140.model.database.User;
import nl.hsleiden.iprwc.s1136140.model.http.AuthRequest;
import nl.hsleiden.iprwc.s1136140.model.http.AuthResponse;
import nl.hsleiden.iprwc.s1136140.model.http.SignUpRequest;
import nl.hsleiden.iprwc.s1136140.services.UserService;
import nl.hsleiden.iprwc.s1136140.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final DaoAuthenticationProvider authManager;
    private final UserService userService;
    private final JwtTokenUtil jwtUtil;
    private final RoleDAO roleDAO;

    public AuthController(DaoAuthenticationProvider authManager, UserService userService, JwtTokenUtil jwtUtil, RoleDAO roleDAO) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.roleDAO = roleDAO;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), accessToken, user.getRole().getName().equals("ADMIN"), user.getId());

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequest request) {
        try {

            User user = new User(request.getEmail(), request.getPassword(), request.getFirstName(), request.getPreposition(), request.getLastName());
            Optional<Role> role = roleDAO.getByName("USER");

            if(role.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found");
            }

            user.setRole(role.get());
            try {
                userService.save(user);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
