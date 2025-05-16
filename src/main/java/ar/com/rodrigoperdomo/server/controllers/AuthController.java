package ar.com.rodrigoperdomo.server.controllers;

import ar.com.rodrigoperdomo.server.dtos.LoginDTO;
import ar.com.rodrigoperdomo.server.dtos.RegisterDTO;
import ar.com.rodrigoperdomo.server.dtos.ResponseDTO;
import ar.com.rodrigoperdomo.server.services.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired IUsuarioService usuarioService;

  /**
   * Endpoint para registrar un usuario al sistema
   *
   * @param registerDTO
   * @return ResponseDTO con el resultado de la acción
   */
  @PostMapping("/register")
  public ResponseDTO register(@RequestBody RegisterDTO registerDTO) {
    try {
      return usuarioService.register(registerDTO);
    } catch (Exception e) {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setMessage(e.getMessage());
      responseDTO.setBody(null);
      return responseDTO;
    }
  }

  /**
   * Endpoint para inicio de sesión de los usuarios al sistema
   *
   * @param loginDTO
   * @return ResponseDTO con el resultado de la acción
   */
  @PostMapping("/login")
  public ResponseDTO login(@RequestBody LoginDTO loginDTO) {
    try {
      return usuarioService.login(loginDTO);
    } catch (Exception e) {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setMessage(e.getMessage());
      responseDTO.setBody(null);
      return responseDTO;
    }
  }
}
