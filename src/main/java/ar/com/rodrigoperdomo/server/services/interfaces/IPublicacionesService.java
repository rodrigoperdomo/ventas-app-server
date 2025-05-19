package ar.com.rodrigoperdomo.server.services.interfaces;

import ar.com.rodrigoperdomo.server.dtos.ProductoDTO;
import ar.com.rodrigoperdomo.server.dtos.PublicacionesDTO;
import ar.com.rodrigoperdomo.server.dtos.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IPublicacionesService {
  ResponseDTO crearNuevoProducto(MultipartFile file, String productoDTO);

  ResponseDTO leerTodosProductos();

  ResponseDTO leerProducto(Long idProducto);

  ResponseDTO updatePublicacion(PublicacionesDTO publicacionesDTO);

  ResponseDTO deleteImagenPublicacion(Long idPublicacion);

  ResponseDTO asociarImagenPublicacion(String idPublicacion, MultipartFile file);

  ResponseDTO deletePublicaicon(Long idPublicacion);
}
