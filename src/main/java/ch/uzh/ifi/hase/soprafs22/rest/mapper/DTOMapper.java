package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

  DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "password", target = "password")
  User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "creation_date", target = "creation_date")
  @Mapping(source = "logged_in", target = "logged_in")
  @Mapping(source = "lobby", target = "lobby")
  UserGetDTO convertEntityToUserGetDTO(User user);

  @Mapping(source = "host_id", target = "host_id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "is_public", target = "is_public")
  @Mapping(source = "total_players", target = "total_players")
  @Mapping(source = "timer", target = "timer")
  Lobby convertLobbyPostDTOtoEntity(LobbyPostDTO lobbyPostDTO);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "host_id", target = "host_id")
  @Mapping(source = "host_name", target = "host_name")
  @Mapping(source = "current_players", target = "current_players")
  @Mapping(source = "total_players", target = "total_players")
  @Mapping(source = "is_open", target = "is_open")
  @Mapping(source = "is_public", target = "is_public")
  @Mapping(source = "players", target = "players")
  @Mapping(source = "timer", target = "timer")
  LobbyGetDTO convertEntityToLobbyGetDTO(Lobby lobby);
}
