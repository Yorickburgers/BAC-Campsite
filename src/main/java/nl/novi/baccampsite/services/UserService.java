package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.UserRequestDto;
import nl.novi.baccampsite.dtos.UserResponseDto;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.exceptions.UsernameNotFoundException;
import nl.novi.baccampsite.mappers.UserMapper;
import nl.novi.baccampsite.models.Authority;
import nl.novi.baccampsite.models.User;
import nl.novi.baccampsite.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDto> retrieveAllUsers() {
        List<UserResponseDto> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(UserMapper.toUserResponseDto(user)));
        return users;
    }

    public UserResponseDto retrieveUser(String username) {
        return UserMapper.toUserResponseDto(userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }

    public Boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = UserMapper.toUser(userRequestDto, passwordEncoder);
        return UserMapper.toUserResponseDto(userRepository.save(user));
    }

    public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {
        User currentUser = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        UserMapper.updateUserFromDto(userRequestDto, currentUser, passwordEncoder);
        return UserMapper.toUserResponseDto(userRepository.save(currentUser));
    }

    public String deleteUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        userRepository.delete(user);
        return "User " + user.getUsername() + " has been deleted!";
    }

    public Set<Authority> getAuthorities(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        UserResponseDto dto = UserMapper.toUserResponseDto(user);
        return dto.authorities;
    }

    public String addAuthority(String username, String authority) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
        return "User " + user.getUsername() + " has been given " + authority + " authority!";
    }

    public String removeAuthority(String username, String authority) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User " + username + " not found!"));
        user.removeAuthority(new Authority(username, authority));
        userRepository.save(user);
        return authority + " authority has been removed from user " + user.getUsername() + "!";
    }
}
