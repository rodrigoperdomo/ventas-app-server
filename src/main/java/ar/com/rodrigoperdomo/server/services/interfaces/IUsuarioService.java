package ar.com.rodrigoperdomo.server.services.interfaces;

import ar.com.rodrigoperdomo.server.dtos.LoginDTO;
import ar.com.rodrigoperdomo.server.dtos.RegisterDTO;
import ar.com.rodrigoperdomo.server.dtos.ResponseDTO;

public interface IUsuarioService {
  ResponseDTO register(RegisterDTO registerDTO);

  ResponseDTO login(LoginDTO loginDTO);
}
