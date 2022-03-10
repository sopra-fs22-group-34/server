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

  @PostMapping("/users/{UN}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO loginUser(@PathVariable String UN, @RequestBody UserPostDTO loginData) {
    // fetch user by specified username
    User user = userService.getUserByUsername(UN);

    // verify password
    User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(loginData);
    userService.matchingPassword(user, loginData.getPassword());

    // set logged_in to true
    userService.loginUser(user);

    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
  }

  @GetMapping("/users/{ID}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO getUserById(@PathVariable String ID) {
      User user = new User();

      // fetch user by specified ID
      try { user = userService.getUserById(Long.parseLong(ID)); }
      // if the path cannot be converted into an ID, it must be a username. Therefore, fetch by username
      catch (NumberFormatException notID){ user = userService.getUserByUsername(ID); }

      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
  }

  @PutMapping("/users/{ID}/logout")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO logoutUser(@PathVariable Long ID) {
    //set loggged_in to false
    User user = userService.getUserById(ID);
    userService.logoutUser(user);

    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

  }
  @PutMapping("/users/{ID}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO setUserById(@PathVariable Long ID, @RequestBody User updatedUser) {
      // check if new name already belongs to another user
      userService.checkIfTaken(ID,updatedUser.getUsername());

      // update the user
      User user = userService.updateUser(ID, updatedUser);

      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
  }

}
