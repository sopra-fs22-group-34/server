package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void findByUsername_success() {
    // given
    User user = new User();
    user.setUsername("firstname@lastname");
    user.setPassword("testPassword");
    user.setLogged_in(false);
    user.setToken("1");
    user.setCreation_date(new Date());

    entityManager.persist(user);
    entityManager.flush();

    // when
    User found = userRepository.findByUsername(user.getUsername());

    // then
    assertNotNull(found.getId());
    assertEquals(found.getUsername(), user.getUsername());
    assertEquals(found.getToken(), user.getToken());
    assertEquals(found.getPassword(), user.getPassword());
    assertEquals(found.getCreation_date(), user.getCreation_date());
    assertEquals(found.getLogged_in(), user.getLogged_in());
  }
    @Test
    public void findByID_success() {
        // given
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setPassword("testPassword");
        user.setLogged_in(false);
        user.setToken("1");
        user.setCreation_date(new Date());

        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findUserById(user.getId());

        // then
        assertNotNull(found.getId());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getToken(), user.getToken());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getCreation_date(), user.getCreation_date());
        assertEquals(found.getLogged_in(), user.getLogged_in());
    }
}
