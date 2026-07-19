package com.agent_ai_users.web.controllers;


import com.agent_ai_users.account.application.AuthenticationService;
import com.agent_ai_users.account.application.UserService;
import com.agent_ai_users.account.domain.entities.UserData;
import com.agent_ai_users.shared.utils.StringUtils;
import com.agent_ai_users.web.mappers.UserDataMapper;
import com.agent_ai_users.web.models.UserDataDTO;
import com.agent_ai_users.web.models.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userAuthService")
@SessionAttributes("userAuthService")
@RequiredArgsConstructor
public class UserAuthController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserDataMapper userDataMapper;

    @PostMapping(value = "/register")
    public ResponseEntity<UserDataDTO> register(@RequestBody @NonNull UserLoginDTO userLoginDTO) throws BadRequestException {
        if (StringUtils.isEmpty(userLoginDTO.getEmail()) || StringUtils.isEmpty(userLoginDTO.getPassword())) {
            throw new BadRequestException("error.emailAndPasswordHasToBeFilled");
        }
        if (StringUtils.isEmpty(userLoginDTO.getUsername())) {
            userLoginDTO.setUsername(userLoginDTO.getEmail());
        }
        if (authenticationService.isDuplicateEmail(userLoginDTO.getEmail())) {
            throw new BadRequestException("error.userWithEmailAlreadyExists");
        }
        UserDetails userDetails = authenticationService.loadUserByUsername(userLoginDTO.getUsername());
        if (userDetails != null) {
            throw new BadRequestException("error.userWithUsernameAlreadyExists");
        }
        UserData userData = authenticationService.registerUser(
                UserData.builder()
                        .email(userLoginDTO.getEmail())
                        .username(userLoginDTO.getUsername())
                        .password(userLoginDTO.getPassword())
                        .build()
        );
        return ResponseEntity.accepted().body(userDataMapper.toDto(userData));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody @NonNull UserLoginDTO userLoginDTO) throws BadRequestException {
        if (StringUtils.isEmpty(userLoginDTO.getUsername()) ||
                StringUtils.isEmpty(userLoginDTO.getPassword()
        )) {
            throw new BadRequestException("error.emailAndPasswordHasToBeFilled");
        }
        String token = authenticationService.authenticate(userLoginDTO.getUsername(), userLoginDTO.getPassword(), authenticationManager);
        return ResponseEntity.ok(token);
    }

    @GetMapping(value = "/getUserData")
    public ResponseEntity<UserDataDTO> getUserData(@RequestParam String username) {
        UserData userData = userService.getUserDataByUsername(username);
        if  (userData == null) {
            throw new UsernameNotFoundException(username);
        }
        return ResponseEntity.ok(userDataMapper.toDto(userData));
    }
}
