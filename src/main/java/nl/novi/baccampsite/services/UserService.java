package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.UserRequestDto;
import nl.novi.baccampsite.dtos.UserResponseDto;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.mappers.UserMapper;
import nl.novi.baccampsite.models.User;
import nl.novi.baccampsite.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> retrieveAllUsers() {
        List<UserResponseDto> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(UserMapper.toUserResponseDto(user)));
        return users;
    }

    public UserResponseDto retrieveUser(Long id) {
        return UserMapper.toUserResponseDto(userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User " + id + " not found!")));
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = UserMapper.toUser(userRequestDto);
        return UserMapper.toUserResponseDto(userRepository.save(user));
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User currentUser = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User " + id + " not found!"));
        UserMapper.updateUserFromDto(userRequestDto, currentUser);
        return UserMapper.toUserResponseDto(userRepository.save(currentUser));
    }

    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User " + id + " not found!"));
        userRepository.delete(user);
        return "User " + user.getUsername() + " with id " + " has been deleted!";
    }
}
