package com.sudheer.userauthenticationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudheer.userauthenticationservice.clients.KafkaClient;
import com.sudheer.userauthenticationservice.dtos.EmailDto;
import com.sudheer.userauthenticationservice.exceptions.*;
import com.sudheer.userauthenticationservice.exceptions.UserAlreadyExistsException;
import com.sudheer.userauthenticationservice.models.Role;
import com.sudheer.userauthenticationservice.models.Session;
import com.sudheer.userauthenticationservice.models.Status;
import com.sudheer.userauthenticationservice.models.User;
import com.sudheer.userauthenticationservice.repositories.RoleRepository;
import com.sudheer.userauthenticationservice.repositories.SessionRepository;
import com.sudheer.userauthenticationservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.util.*;

@Service
@Primary
public class UserAuthService implements iUserAuthService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    SecretKey secretKey;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private KafkaClient kafkaClient;

    @Override
    public Pair<User, MultiValueMap<String, String>> login(String username, String password) throws InvalidCredentialsException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            if(bCryptPasswordEncoder.matches(password, optionalUser.get().getPassword())){
                //return optionalUser.get();

//                String message = "{\n" +
//                "   \"email\": \"anurag@scaler.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"instructor\",\n" +
//                "      \"buddy\"\n" +
//                "   ],\n" +
//                "   \"expirationDate\": \"25thSept2024\"\n" +
//                "}";
//
//                String token = Jwts.builder().content(message).compact();
//
//                MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//                headers.add(HttpHeaders.SET_COOKIE, token);
//
//                Pair<User, MultiValueMap<String, String>> pairCookieNUser= new Pair<>(optionalUser.get(), headers);
//
//                return pairCookieNUser;

                Map<String, Object> claims = new HashMap<>();
                claims.put("username", optionalUser.get().getUsername());
                claims.put("Role", optionalUser.get().getRoles());
                claims.put("status", optionalUser.get().getStatus());
                claims.put("id", optionalUser.get().getId());
                Long timeinmillis = System.currentTimeMillis();
                claims.put("isa", timeinmillis);
                claims.put("expiration", timeinmillis+86400000);

                String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

                Session session = new Session();
                session.setToken(token);
                session.setUser(optionalUser.get());
                sessionRepository.save(session);

                MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
                headers.add(HttpHeaders.SET_COOKIE, token);

                Pair<User, MultiValueMap<String, String>> pairCookieNUser= new Pair<>(optionalUser.get(), headers);

                return pairCookieNUser;
            }
            else throw new InvalidCredentialsException("Invalid credentials passed");
        }
        return null;
    }

    public Boolean validateToken(String token, Long user_Id) throws SessionDoesNotExistsException {
        Optional<Session> optionalSession = sessionRepository.findByTokenAndUser_Id(token, user_Id);
        if(!optionalSession.isPresent()){
            throw new SessionDoesNotExistsException("Session does not exists with provided token and UserName");
        }

        Session session = optionalSession.get();
        String sessionToken = session.getToken();
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(sessionToken).getPayload();
        Long currentTimeinmillis = System.currentTimeMillis();
        Long expTimeinmillis = (Long)claims.get("expiration");

        if(expTimeinmillis < currentTimeinmillis){
            return false;
        }

        return true;

    }

    public User signUp(String userName, String password, Set<Role> roleSet) throws UserAlreadyExistsException, RoleDoesNotExists {
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException("User exists with the username" + userName);
        }
        User user = new User();
        user.setUsername(userName);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setStatus(Status.ACTIVE);
        Set<Role> roles = new HashSet<>();

        for (Role roleName : roleSet) {
            Optional<Role> optionalRole = roleRepository.findByRoleName(roleName.getRoleName()); // Ensure the role exists
            if (optionalRole != null) {
                Role role = optionalRole.get();
                roles.add(role);
            } else {
                throw new RoleDoesNotExists("Role does not exist with the name " + roleName.getRoleName());
            }
        }

        user.setRoles(roles);

        try{
            EmailDto emailDto = new EmailDto();

            emailDto.setFrom("anuragbatch@gmail.com");
            emailDto.setTo(userName);
            emailDto.setSubject("Sign up successful");
            emailDto.setBody("Thanks for signing up for Sudheer's E commerce site");
            String topic = "SignUp";
            String msg = objectMapper.writeValueAsString(emailDto);
            kafkaClient.sendMessage(topic, msg);
        }catch (JsonProcessingException exception){
            throw new RuntimeException(exception.getMessage());
        }

        return userRepository.save(user);
    }



    @Override
    public User logOut(String username, String password) {
        return null;
    }


}
