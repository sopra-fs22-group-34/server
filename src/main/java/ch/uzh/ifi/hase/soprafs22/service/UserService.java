package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.tokens.Token;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getUsers() {
    return this.userRepository.findAll();
  }

  public User getUserById(Long ID) {
      User userByID = userRepository.findUserById(ID);
      if (userByID == null) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with this ID exists.");
      }
      return userByID;
  }

  public void checkIfTaken(Long id, String un){
      User userByUN = userRepository.findByUsername(un);
      if (userByUN != null && userRepository.findUserById(id) != userByUN) {
          throw new ResponseStatusException(HttpStatus.CONFLICT, "This username is already taken.");
      }
  }

  public User getUserByUsername(String un) {
      User userByUN = userRepository.findByUsername(un);
      if (userByUN == null) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with this username exists.");
      }
      return userByUN;
  }

  public void logoutUser(User user){
      user.setLogged_in(false);
  }

  public void loginUser(User user){
      user.setLogged_in(true);
  }

  public User createUser(User newUser) {
    newUser.setToken(UUID.randomUUID().toString());
    newUser.setLogged_in(true);
    newUser.setCreation_date(new Date());

    checkIfUserExists(newUser);

    // saves the given entity but data is only persisted in the database once
    // flush() is called
    newUser = userRepository.save(newUser);
    userRepository.flush();

    log.debug("Created Information for User: {}", newUser);
    return newUser;
  }

  public User updateUser(Long ID, User updatedUser){

      User userToEdit = getUserById(ID);
      if (updatedUser.getBirthday() != null) { userToEdit.setBirthday(updatedUser.getBirthday()); }
      if (updatedUser.getUsername() != null) { userToEdit.setUsername(updatedUser.getUsername()); }

      return userToEdit;
  }

  /**
   * This is a helper method that will check the uniqueness criteria of the
   * username and the name
   * defined in the User entity. The method will do nothing if the input is unique
   * and throw an error otherwise.
   *
   * @param userToBeCreated
   * @throws org.springframework.web.server.ResponseStatusException
   * @see User
   */
  private void checkIfUserExists(User userToBeCreated) {
    User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
    if (userByUsername != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "A user with this username already exists.");
    }
  }

  /**
   * Checks if a given password applies to a given user.
   * @param user
   * @param password
   */
  public void matchingPassword(User user, String password){
      if (!user.getPassword().equals(password)){
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Incorrect password.");
      };
  }
}
