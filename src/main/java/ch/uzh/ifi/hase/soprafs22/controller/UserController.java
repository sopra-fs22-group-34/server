package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {

  private final UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<UserGetDTO> getAllUsers() {
    // fetch all users in the internal representation
    List<User> users = userService.getUsers();
    List<UserGetDTO> userGetDTOs = new ArrayList<>();

    // convert each user to the API representation
    for (User user : users) {
      userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
    }
    return userGetDTOs;
  }


  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
    // convert API user to internal representation
    User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // create user
    User createdUser = userService.createUser(userInput);

    // convert internal representation of user back to API
    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
  }

  @GetMapping("/users/{ID}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO getUserById(@PathVariable Long ID) {
      // fetch user by specified ID
      User user = userService.getUserById(ID);
      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
  }

  @PutMapping("/users/{ID}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO setUserById(@PathVariable Long ID, @RequestBody User updatedUser) {
      // don't create a copy; access and edit [userService.getUserById(ID)] directly

      // update all editable parameters
      userService.getUserById(ID).setBirthday(updatedUser.getBirthday());
      if (updatedUser.getUsername() != null) {userService.getUserById(ID).setUsername(updatedUser.getUsername());}
      userService.getUserById(ID).setName(updatedUser.getName());
      if (updatedUser.getPassword() != null) {userService.getUserById(ID).setPassword(updatedUser.getPassword());}

      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userService.getUserById(ID));
  }

  @PutMapping("/users/{ID}/birthday")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO setBirthdayById(@PathVariable Long ID, @RequestBody User updatedUser) {
      // don't create a copy; access and edit [userService.getUserById(ID)] directly
      userService.getUserById(ID).setBirthday(updatedUser.getBirthday());
      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userService.getUserById(ID));
  }
  @PutMapping("/users/{ID}/username")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO setUsernameById(@PathVariable Long ID, @RequestBody User updatedUser) {
      // don't create a copy; access and edit [userService.getUserById(ID)] directly
      if (updatedUser.getUsername() != null) {userService.getUserById(ID).setUsername(updatedUser.getUsername());}
      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userService.getUserById(ID));
  }
  @PutMapping("/users/{ID}/name")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO setNameById(@PathVariable Long ID, @RequestBody User updatedUser) {
      // don't create a copy; access and edit [userService.getUserById(ID)] directly
      userService.getUserById(ID).setName(updatedUser.getName());
      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userService.getUserById(ID));
  }
  @PutMapping("/users/{ID}/password")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO setPasswordById(@PathVariable Long ID, @RequestBody User updatedUser) {
      // don't create a copy; access and edit [userService.getUserById(ID)] directly
      if (updatedUser.getPassword() != null) {userService.getUserById(ID).setPassword(updatedUser.getPassword());}
      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userService.getUserById(ID));
  }
}
