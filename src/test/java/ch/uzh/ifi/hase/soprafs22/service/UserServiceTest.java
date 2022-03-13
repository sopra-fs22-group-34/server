package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User testUser;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    testUser = new User();
    testUser.setId(1L);
    testUser.setName("testName");
    testUser.setUsername("testUsername");
    testUser.setPassword("testPassword");

    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
  }

  @Test
  public void createUser_validInputs_success() {
    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    User createdUser = userService.createUser(testUser);

    // then
    Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

    assertEquals(testUser.getId(), createdUser.getId());
    assertEquals(testUser.getName(), createdUser.getName());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertEquals(testUser.getPassword(), createdUser.getPassword());
    assertNotNull(createdUser.getToken());
    assertNotNull(createdUser.getCreation_date());
    assertNull(createdUser.getBirthday());
    assertTrue(createdUser.getLogged_in());
  }

  @Test
  public void createUser_duplicateName_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    Mockito.when(userRepository.findByName(Mockito.any())).thenReturn(testUser);
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }

  @Test
  public void createUser_duplicateInputs_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    Mockito.when(userRepository.findByName(Mockito.any())).thenReturn(testUser);
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);
    Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }

  @Test
  public void getUserByIdTest() {
      userService.createUser(testUser);
      // throw error for nonexistent user
      assertThrows(ResponseStatusException.class, () -> userService.getUserById(2L));
      // find given user
      Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(testUser);
      assertEquals(userService.getUserById(1L), testUser);
  }

  @Test
  public void getUserByUsernameTest() {
      userService.createUser(testUser);
      // throw error for nonexistent user
      assertThrows(ResponseStatusException.class, () -> userService.getUserByUsername("fakeUser"));
      // find given user
      Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);
      assertEquals(userService.getUserByUsername("testUsername"), testUser);
  }

  @Test
  public void checkIfTakenTest() {
      userService.createUser(testUser);
      Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);
      // throw error for a different user wanting the same UN
      assertThrows(ResponseStatusException.class, () -> userService.checkIfTaken(2L, "testUsername"));
      // accept name change if it's the same user
      Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(testUser);
      userService.checkIfTaken(1L, "testUsername");
  }

  @Test
  public void logoutUserTest() {
      userService.createUser(testUser);
      userService.logoutUser(testUser);
      assertFalse(testUser.getLogged_in());
  }

  @Test
  public void loginUserTest() {
      userService.createUser(testUser);
      testUser.setLogged_in(false);
      userService.loginUser(testUser);
      assertTrue(testUser.getLogged_in());
  }

  @Test
  public void updateUserTest() {
      userService.createUser(testUser);
      Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(testUser);
      //create new data
      User updatedInfo = new User();
      updatedInfo.setBirthday(new Date());
      updatedInfo.setUsername("newUsername");

      assertNotEquals(testUser.getUsername(), updatedInfo.getUsername());
      assertNotEquals(testUser.getBirthday(), updatedInfo.getBirthday());

      userService.updateUser(1L, updatedInfo);

      assertEquals(testUser.getUsername(), updatedInfo.getUsername());
      assertEquals(testUser.getBirthday(), updatedInfo.getBirthday());
  }

  @Test
  public void matchingPasswordTest() {
      userService.createUser(testUser);
      // check if nothing happens when correct password is given
      userService.matchingPassword(testUser,"testPassword");
      // check that error is thrown with wrong password
      assertThrows(ResponseStatusException.class, () -> userService.matchingPassword(testUser,"wrongPassword"));
  }

}
