package ar.com.rodrigoperdomo.server.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICloudinaryService {
  String uploadFile(MultipartFile file) throws IOException;

  void deleteFile(Long idPublicacion);

  void asociarImagenPublicacion(Long idPublicacion, MultipartFile file) throws IOException;
}
