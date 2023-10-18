package com.passlocker.passlocker.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passlocker.passlocker.controllers.requests.UserUpdateRequest;
import com.passlocker.passlocker.controllers.responses.GenericPaginatedResponse;
import com.passlocker.passlocker.entities.UserEntity;
import com.passlocker.passlocker.entities.enums.UserType;
import com.passlocker.passlocker.exceptions.BadPermissionException;
import com.passlocker.passlocker.exceptions.BadRequestException;
import com.passlocker.passlocker.exceptions.NotFoundException;
import com.passlocker.passlocker.repositores.UserRepository;
import com.passlocker.passlocker.services.queries.GetAllUsersCount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,20}$";
    private final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
    private final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final GetAllUsersCount getAllUsersCount;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository, final EntityManager entityManager,
                       GetAllUsersCount getAllUsersCount, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
        this.getAllUsersCount = getAllUsersCount;
    }

    public GenericPaginatedResponse getUsers(int limit, int offset, HttpServletRequest request) {
        TypedQuery<UserEntity> query = this.entityManager.createNamedQuery("GET_USERS", UserEntity.class);
        Query countQuery = this.entityManager.createNamedQuery("COUNT_USERS");

        query.setMaxResults(limit);
        query.setFirstResult(offset);

        return new GenericPaginatedResponse()
                .setItems(query.getResultList())
                .setLimit(limit)
                .setOffset(offset)
                .setTotalItems((long) countQuery.getSingleResult())
                .build(request);
    }

    /**
     * FUNCTION TO CREATE A NEW USER
     * @param userEntity UserEntity to be created
     * @return  userEntity after saving to the database
     * @throws BadPermissionException   if any unauthorized user tries to create a user account
     */
    public UserEntity createUser(UserEntity userEntity) throws BadPermissionException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<String> authorities = new ArrayList<>();

        authentication.getAuthorities()
                .forEach(authority -> authorities.add(authority.getAuthority()));

        String authority = authorities.get(0);

        System.out.println("authority = " + authority + " username = " + authentication.getName());

        //  if a normal user tries to create an admin account
        if (!authority.equals("ADMIN") && userEntity.getUserType() != null && userEntity.getUserType().toString().equals("ADMIN")) {
            throw new BadPermissionException("Insufficient permission");
        }

        validateUserEntityRequest(userEntity);  //  validating the userEntity request body

        //  if any user tries to create a new user without passing the usertype in the request body
        //  default usertype will be USER
        if (userEntity.getUserType() == null || userEntity.getUserType().toString().isEmpty()) {
            userEntity.setUserType(UserType.USER);
        }

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        Date timeStamp = new Date(System.currentTimeMillis());
        userEntity.setCreatedAt(timeStamp);
        userEntity.setUpdatedAt(timeStamp);

        return this.userRepository
                .save(userEntity);
    }

    public UserEntity getUserByUsername(String username) {
        Optional<UserEntity> optionalUserEntity = Optional.ofNullable(this.userRepository.getUserEntityByUsername(username));
        if (optionalUserEntity.isEmpty())
            throw new NotFoundException("User with the username : " + username + " does not exist");

        return optionalUserEntity.get();
    }

    public UserEntity getUserByEmailAddress(String emailAddress) {
        Optional<UserEntity> optionalUserEntity = Optional.ofNullable(this.userRepository.getUserEntityByEmailAddress(emailAddress));
        if (optionalUserEntity.isEmpty())
            throw new RuntimeException("User with the email address : " + emailAddress + " does not exist");

        return optionalUserEntity.get();
    }

    public UserEntity getUserByUserId(String userId) {
        Optional<UserEntity> optionalUserEntity = Optional.ofNullable(this.userRepository.getUserEntityByUserId(userId));
        if (optionalUserEntity.isEmpty())
            throw new RuntimeException("User with the user id : " + userId + " does not exist");

        return optionalUserEntity.get();
    }

    /**
     * Function that returns true if a user exists and false otherwise
     *
     * @param userId userId of the user that needs to be checked if it exists
     * @param username username of the user
     * @param emailAddress email address of the user
     * @return true or false if a user exists based on the parameters
     */
    public boolean userExists(String userId, String username, String emailAddress) {
        return this.userRepository
                .existsByUserIdOrUsernameOrEmailAddress(userId, username, emailAddress);
    }

    public void validateUserEntityRequest(UserEntity userEntity) {
        String username = userEntity.getUsername();
        String emailAddress = userEntity.getEmailAddress();
        String password = userEntity.getPassword();

        if (!isValidEmailAddress(emailAddress))
            throw new BadRequestException("Invalid email address");

        if (!isValidUsername(username))
            throw new BadRequestException("Invalid username");

        if (password == null || password.length() <= 8)
            throw new BadRequestException("Password can not be empty or less than 8 characters");
    }

    /**
     * Function to validate username
     * @param username username to be validated
     * @return true if the username is valid otherwise false
     */
    public boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new BadRequestException("Username can not be null or empty");
        }

        Matcher matcher = usernamePattern.matcher(username);
        return matcher.matches();
    }

    /**
     * Function to validate email address
     * @param emailAddress to validated
     * @return true if the email address is valid otherwise false
     */
    public boolean isValidEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new BadRequestException("Email address can not be null or empty");
        }

        Matcher matcher = emailPattern.matcher(emailAddress);
        return matcher.matches();
    }

    /**
     * FUNCTION TO DO PATCH UPDATE FOR A USER ENTITY
     * @param userRequest data in key/value pair where key represents the property to be updated
     * @return  updated UserEntity
     * @throws BadPermissionException when an anonymous user tries to update any user
     */
    public UserEntity patchUserEntity(UserUpdateRequest userRequest) {
        if (this.userRepository.existsByUserIdOrUsernameOrEmailAddress(null, userRequest.getUsername(), userRequest.getEmailAddress())) {
            throw new BadRequestException("User with the given username or email address already exist");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity userEntity = this.getUserByUsername(username);

        if (isValidUsername(userRequest.getUsername()) &&
                isValidEmailAddress(userRequest.getEmailAddress())) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> userEntityMap = mapper.convertValue(userEntity, new TypeReference<Map<String, String>>() {});
            Map<String, String> userRequestMap = mapper.convertValue(userRequest, new TypeReference<Map<String, String>>() {});

            for (Map.Entry<String, String> entry : userRequestMap.entrySet()) {
                String fieldName = entry.getKey();
                String value = entry.getValue().trim();

                if (!value.isEmpty() && userEntityMap.containsKey(fieldName)) {
                    userEntityMap.put(fieldName, value);
                }
            }

            userEntity = mapper.convertValue(userEntityMap, UserEntity.class);
            return userEntity;
        }

        throw new BadRequestException("Enter a valid username and email address");
    }

    /**
     * Function to reset a user's password
     * @param newPassword   the new password for the user
     * @param confirmPassword   confirmation for the new password entered by the user
     */
    public void resetPassword(String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new BadRequestException("Passwords do not match");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity user = this.userRepository.getUserEntityByUsername(username);

        try {
            user.setPassword(this.passwordEncoder.encode(newPassword));
            this.userRepository.save(user); //  updates the user data in the database
        }catch (Exception exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }
}
