package ar.com.rodrigoperdomo.server.services;

import static ar.com.rodrigoperdomo.server.utils.Constantes.*;

import ar.com.rodrigoperdomo.server.dtos.ProductoDTO;
import ar.com.rodrigoperdomo.server.dtos.PublicacionesDTO;
import ar.com.rodrigoperdomo.server.dtos.ResponseDTO;
import ar.com.rodrigoperdomo.server.entities.Publicaciones;
import ar.com.rodrigoperdomo.server.entities.Usuarios;
import ar.com.rodrigoperdomo.server.repositories.IPublicacionesRepository;
import ar.com.rodrigoperdomo.server.repositories.IUsuarioRepository;
import ar.com.rodrigoperdomo.server.services.interfaces.ICloudinaryService;
import ar.com.rodrigoperdomo.server.services.interfaces.IPublicacionesService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class IPublicacionesServiceImpl implements IPublicacionesService {

  @Autowired ICloudinaryService cloudinaryService;
  @Autowired IUsuarioRepository usuarioRepository;
  @Autowired IPublicacionesRepository publicacionesRepository;

  /**
   * Crea una nueva publicación en el sistema
   *
   * @param file
   * @param productoDTO
   * @return ResponseDTO con el resultado de la acción
   */
  @Override
  public ResponseDTO crearNuevoProducto(MultipartFile file, ProductoDTO productoDTO) {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      Publicaciones newPublicaciones = new Publicaciones();
      newPublicaciones.setNombreProducto(productoDTO.getNombreProducto());
      newPublicaciones.setDescripcionProducto(productoDTO.getDescripcion());
      newPublicaciones.setPrecioProducto(productoDTO.getPrecio());
      newPublicaciones.setCantidadProducto(productoDTO.getCantidadProducto());
      try {
        String urlImagen = cloudinaryService.uploadFile(file);
        newPublicaciones.setImagenUrl(urlImagen);
        Usuarios userByRequest =
            usuarioRepository
                .findById(productoDTO.getIdUsuario())
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
        newPublicaciones.setUsuarios(userByRequest);
        publicacionesRepository.save(newPublicaciones);
        responseDTO.setStatus(HttpStatus.CREATED.value());
        responseDTO.setBody(null);
        responseDTO.setMessage(PRODUCT_CREATED_SUCCESS);
      } catch (Exception e) {
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setBody(null);
        responseDTO.setMessage(e.getMessage());
      }
    } catch (Exception e) {
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
    }
    return responseDTO;
  }

  /**
   * Metodo que retorna todas las publicaciones desde la db
   *
   * @return ResponseDTO con el listado de publicaciones
   */
  @Override
  public ResponseDTO leerTodosProductos() {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      List<Publicaciones> publicacionesList = publicacionesRepository.findAll();
      List<PublicacionesDTO> publicacionesDTOList =
          publicacionesList.stream()
              .map(
                  item -> {
                    PublicacionesDTO publicacionesDTO = new PublicacionesDTO();
                    publicacionesDTO.setIdProducto(item.getIdProducto());
                    publicacionesDTO.setNombreProducto(item.getNombreProducto());
                    publicacionesDTO.setDescripcionProducto(item.getDescripcionProducto());
                    publicacionesDTO.setPrecioProducto(item.getPrecioProducto());
                    publicacionesDTO.setCantidadProducto(item.getCantidadProducto());
                    publicacionesDTO.setImagenUrl(item.getImagenUrl());
                    publicacionesDTO.setIdUsuario(item.getUsuarios().getIdUsuario());
                    return publicacionesDTO;
                  })
              .toList();
      responseDTO.setMessage(OK_MESSAGE);
      responseDTO.setStatus(HttpStatus.OK.value());
      responseDTO.setBody(publicacionesDTOList);
    } catch (Exception e) {
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
    }
    return responseDTO;
  }

  /**
   * Metodo para buscar una publicación en la base de datos por medio de la request
   *
   * @param idProducto
   * @return
   */
  @Override
  public ResponseDTO leerProducto(Long idProducto) {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      Publicaciones publicacionByRequest =
          publicacionesRepository
              .findById(idProducto)
              .orElseThrow(() -> new NoSuchElementException(PUBLICACION_NOT_FOUND));
      PublicacionesDTO publicacionesDTO =
          new PublicacionesDTO(
              publicacionByRequest.getIdProducto(),
              publicacionByRequest.getNombreProducto(),
              publicacionByRequest.getDescripcionProducto(),
              publicacionByRequest.getPrecioProducto(),
              publicacionByRequest.getCantidadProducto(),
              publicacionByRequest.getImagenUrl(),
              publicacionByRequest.getUsuarios().getIdUsuario());
      responseDTO.setStatus(HttpStatus.OK.value());
      responseDTO.setMessage(OK_MESSAGE);
      responseDTO.setBody(publicacionesDTO);
    } catch (Exception e) {
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
    }
    return responseDTO;
  }

  /**
   * Método para actualizar algunos campos de la publicacion dentro de la db, que me llega por
   * request
   *
   * @param publicacionesDTO
   * @return ResponseDTO con el resultado de la acción
   */
  @Override
  public ResponseDTO updatePublicacion(PublicacionesDTO publicacionesDTO) {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      Publicaciones publicacionByRequest =
          publicacionesRepository
              .findById(publicacionesDTO.getIdProducto())
              .orElseThrow(() -> new NoSuchElementException(PUBLICACION_NOT_FOUND));
      publicacionByRequest.setNombreProducto(publicacionesDTO.getNombreProducto());
      publicacionByRequest.setDescripcionProducto(publicacionesDTO.getDescripcionProducto());
      publicacionByRequest.setPrecioProducto(publicacionesDTO.getPrecioProducto());
      publicacionByRequest.setCantidadProducto(publicacionesDTO.getCantidadProducto());
      publicacionesRepository.save(publicacionByRequest);
      responseDTO.setStatus(HttpStatus.OK.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(PUBLICACION_UPDATED_SUCCESS);
    } catch (Exception e) {
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
    }
    return responseDTO;
  }

  /**
   * Metodo que recorre la db para encontrar la imagenURL y asi eliminarlo en cloudinary como
   * tambien en la db
   *
   * @param idPublicacion
   * @return ResponseDTO con el resultado de la acción
   */
  @Override
  public ResponseDTO deleteImagenPublicacion(Long idPublicacion) {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      cloudinaryService.deleteFile(idPublicacion);
      responseDTO.setBody(null);
      responseDTO.setStatus(HttpStatus.OK.value());
      responseDTO.setMessage(IMAGEN_DELETED_SUCCES);
    } catch (Exception e) {
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
    }
    return responseDTO;
  }

  /**
   * Sube la imagen a Cloudinary y asocia el Secure_Url al registro de la publicacion
   *
   * @param idPublicacion
   * @param file
   * @return ResponseDTO con el resultado de la acción
   */
  @Override
  public ResponseDTO asociarImagenPublicacion(Long idPublicacion, MultipartFile file) {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      cloudinaryService.asociarImagenPublicacion(idPublicacion, file);
      responseDTO.setStatus(HttpStatus.OK.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(IMAGEN_UPLOAD_SUCCESS_ASSOCIATE_PUBLICACION);
    } catch (Exception e) {
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
    }
    return responseDTO;
  }

  /**
   * Elimina el registro de la base de datos como tambien la imagen de Cloudinary
   *
   * @param idPublicacion
   * @return ResponseDTO con el resultado de la acción
   */
  @Override
  public ResponseDTO deletePublicaicon(Long idPublicacion) {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      cloudinaryService.deleteFile(idPublicacion);
      Publicaciones publicacionByRequest =
          publicacionesRepository
              .findById(idPublicacion)
              .orElseThrow(() -> new NoSuchElementException(PUBLICACION_NOT_FOUND));
      publicacionesRepository.delete(publicacionByRequest);
      responseDTO.setStatus(HttpStatus.OK.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(PUBLICACION_DELETED_SUCCESS);
    } catch (Exception e) {
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
    }
    return responseDTO;
  }
}
