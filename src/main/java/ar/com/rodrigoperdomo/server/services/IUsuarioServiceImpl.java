package ar.com.rodrigoperdomo.server.services;

import ar.com.rodrigoperdomo.server.dtos.LoginDTO;
import ar.com.rodrigoperdomo.server.dtos.RegisterDTO;
import ar.com.rodrigoperdomo.server.dtos.ResponseDTO;
import ar.com.rodrigoperdomo.server.dtos.TokenDTO;
import ar.com.rodrigoperdomo.server.entities.Usuarios;
import ar.com.rodrigoperdomo.server.repositories.IUsuarioRepository;
import ar.com.rodrigoperdomo.server.services.interfaces.IUsuarioService;
import ar.com.rodrigoperdomo.server.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static ar.com.rodrigoperdomo.server.utils.Constantes.*;

@Service
public class IUsuarioServiceImpl implements IUsuarioService {

  @Autowired private IUsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtUtils;

  public IUsuarioServiceImpl(PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
    this.passwordEncoder = passwordEncoder;
    this.jwtUtils = jwtUtils;
  }

  /**
   * Método para guardar un usuario o registrar en el sistema
   *
   * @param registerDTO
   * @return ResponseDTO con el resultado de la accion
   */
  @Override
  public ResponseDTO register(RegisterDTO registerDTO) {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      String hashpassword = passwordEncoder.encode(registerDTO.getPassword());
      Usuarios newUsuario = new Usuarios();
      newUsuario.setUsername(registerDTO.getUsername());
      newUsuario.setEmail(registerDTO.getEmail());
      newUsuario.setPassword(hashpassword);
      usuarioRepository.save(newUsuario);
      responseDTO.setStatus(HttpStatus.CREATED.value());
      responseDTO.setMessage(USER_CREATED_SUCCESS);
      responseDTO.setBody(null);
    } catch (Exception e) {
      responseDTO.setBody(null);
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setMessage(e.getMessage());
    }
    return responseDTO;
  }

  /**
   * Metodo para iniciar sesión
   *
   * @param loginDTO
   * @return ResponseDTO con resultado de la acción
   */
  @Override
  public ResponseDTO login(LoginDTO loginDTO) {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      Usuarios userByUsername = usuarioRepository.getUsuarioByUsername(loginDTO.getUsername());
      if (userByUsername != null) {
        if (passwordEncoder.matches(loginDTO.getPassword(), userByUsername.getPassword())) {
          TokenDTO tokenDTO = new TokenDTO();
          tokenDTO.setToken(
              jwtUtils.generateToken(userByUsername.getUsername(), userByUsername.getEmail()));
          responseDTO.setStatus(HttpStatus.OK.value());
          responseDTO.setMessage(LOGIN_SUCCESS);
          responseDTO.setBody(tokenDTO);
        } else {
          responseDTO.setStatus(HttpStatus.UNAUTHORIZED.value());
          responseDTO.setBody(null);
          responseDTO.setMessage(PASSWORD_INCORRECT);
        }
      } else {
        responseDTO.setStatus(HttpStatus.NOT_FOUND.value());
        responseDTO.setBody(null);
        responseDTO.setMessage(USER_NOT_FOUND);
      }
    } catch (Exception e) {
      responseDTO.setBody(null);
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setMessage(e.getMessage());
    }
    return responseDTO;
  }
}
