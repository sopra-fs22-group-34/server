package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class DTOMapperTest {
  @Test
  public void testCreateUser_fromUserPostDTO_toUser_success() {
    // create UserPostDTO
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setName("name");
    userPostDTO.setUsername("username");
    userPostDTO.setPassword("password");

    // MAP -> Create user
    User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // check content
    assertEquals(userPostDTO.getName(), user.getName());
    assertEquals(userPostDTO.getUsername(), user.getUsername());
    assertEquals(userPostDTO.getPassword(), user.getPassword());
  }

  @Test
  public void testGetUser_fromUser_toUserGetDTO_success() {
    // create User
    User user = new User();
    user.setUsername("firstname@lastname");
    user.setPassword("testPassword");
    user.setLogged_in(false);
    user.setToken("1");
    user.setCreation_date(new Date());

    // MAP -> Create UserGetDTO
    UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

    // check content
    assertEquals(user.getId(), userGetDTO.getId());
    assertEquals(user.getUsername(), userGetDTO.getUsername());
    assertEquals(user.getLogged_in(), userGetDTO.getLogged_in());
    assertEquals(user.getCreation_date(), userGetDTO.getCreation_date());
    assertEquals(user.getBirthday(), userGetDTO.getBirthday());
  }


  @Test
  public void testCreateLobby_fromLobbyPostDTO_toLobby_success() {
      //create LobbyPostDTO
      LobbyPostDTO lobbyPostDTO = new LobbyPostDTO();
      lobbyPostDTO.setHostId(4L);
      lobbyPostDTO.setLobbyName("1337Lobby");
      lobbyPostDTO.setisPublic(true);
      lobbyPostDTO.setisPrivate(false);

      // MAP -> Create Lobby
      Lobby lobby = DTOMapper.INSTANCE.convertLobbyPostDTOtoEntity(lobbyPostDTO);

      // check content
      assertEquals(lobby.getHostId(),lobbyPostDTO.getHostId());
      assertEquals(lobby.getLobbyName(),lobbyPostDTO.getLobbyName());
      assertEquals(lobby.getisPublic(),lobbyPostDTO.getisPublic());
      assertEquals(lobby.getisPrivate(), lobbyPostDTO.getisPrivate());
  }

    @Test
    public void testGetLobby_fromLobby_toLobbyGetDTO_success() {
        // create User
        Lobby lobby = new Lobby();
        lobby.setLobbyName("ToxicMW2Lobby");
        lobby.setHostId(7L);
        lobby.setisPrivate(true);
        lobby.setisPublic(false);

        // MAP -> Create UserGetDTO
        LobbyGetDTO lobbyGetDTO = DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby);

        // check content
        assertEquals(lobby.getHostId(), lobbyGetDTO.getHostId());
        assertEquals(lobby.getLobbyName(), lobbyGetDTO.getLobbyName());
        assertEquals(lobby.getisPrivate(), lobbyGetDTO.getisPrivate());
        assertEquals(lobby.getisPublic(), lobbyGetDTO.getisPublic());
    }
}
