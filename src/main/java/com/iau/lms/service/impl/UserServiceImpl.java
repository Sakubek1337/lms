package com.iau.lms.service.impl;

import com.iau.lms.enums.Role;
import com.iau.lms.models.dto.UserDto;
import com.iau.lms.models.entity.User;
import com.iau.lms.repository.UserRepository;
import com.iau.lms.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    @Override
    public UserDto getCurrentUser() throws Exception {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if (login == null){
            return null;
        }
        User user = getUserEntityByLogin(login);
        return new UserDto(user.getUsername(), null);
    }

    @Override
    public boolean register(UserDto userDto, HttpServletRequest request) {
        if(userRepository.existsByUsername(userDto.getUsername())){
            return false;
        }
        User user = User.builder()
                .username(userDto.getUsername())
                .password(encoder.encode(userDto.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return true;
    }

    private User getUserEntityByLogin(String login) throws Exception {
        return userRepository.findByUsername(login).orElseThrow(() -> new Exception());
    }

    private User getUserEntityById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception());
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new Exception("User not found"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }
}
