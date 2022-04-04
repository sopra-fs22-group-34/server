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
      lobbyPostDTO.setHost_id(4L);
      lobbyPostDTO.setName("1337Lobby");
      lobbyPostDTO.setIs_public(true);
      lobbyPostDTO.setTotal_players(4L);

      // MAP -> Create Lobby
      Lobby lobby = DTOMapper.INSTANCE.convertLobbyPostDTOtoEntity(lobbyPostDTO);

      // check content
      assertEquals(lobby.getHost_id(),lobbyPostDTO.getHost_id());
      assertEquals(lobby.getName(),lobbyPostDTO.getName());
      assertEquals(lobby.getIs_public(),lobbyPostDTO.getIs_public());
      assertEquals(lobby.getTotal_players(), lobbyPostDTO.getTotal_players());
  }

    @Test
    public void testGetLobby_fromLobby_toLobbyGetDTO_success() {
        // create User
        Lobby lobby = new Lobby();
        lobby.setName("ToxicMW2Lobby");
        lobby.setHost_id(7L);
        lobby.setIs_open(true);
        lobby.setIs_public(false);

        // MAP -> Create UserGetDTO
        LobbyGetDTO lobbyGetDTO = DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby);

        // check content
        assertEquals(lobby.getHost_id(), lobbyGetDTO.getHost_id());
        assertEquals(lobby.getName(), lobbyGetDTO.getName());
        assertEquals(lobby.getIs_open(), lobbyGetDTO.getIs_open());
        assertEquals(lobby.getIs_public(), lobbyGetDTO.getIs_public());
    }
}
