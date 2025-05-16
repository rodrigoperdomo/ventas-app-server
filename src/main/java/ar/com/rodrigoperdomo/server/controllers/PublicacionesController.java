package ar.com.rodrigoperdomo.server.controllers;

import static ar.com.rodrigoperdomo.server.utils.Constantes.BEARER_AUTH_STRING;

import ar.com.rodrigoperdomo.server.dtos.PublicacionesDTO;
import ar.com.rodrigoperdomo.server.dtos.ResponseDTO;
import ar.com.rodrigoperdomo.server.services.interfaces.IPublicacionesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/publicaciones")
@SecurityRequirement(name = BEARER_AUTH_STRING)
public class PublicacionesController {

  @Autowired IPublicacionesService publicacionesService;

  /**
   * Endpoint para crear publicaciones en el sistema
   *
   * @param productoDTO
   * @param file
   * @return ResponseDTO con el resultado de la acción
   */
  @PostMapping(value = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseDTO crearPublicacion(
      @RequestPart("producto") String productoDTO, @RequestPart("file") MultipartFile file) {
    try {
      return publicacionesService.crearNuevoProducto(file, productoDTO);
    } catch (Exception e) {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
      return responseDTO;
    }
  }

  /**
   * Endpoint para traer todas las publicaciones desde la db
   *
   * @return ResponseDTO con el listado de publicaciones
   */
  @GetMapping("/leer-todos")
  public ResponseDTO leerTodasPublicaciones() {
    try {
      return publicacionesService.leerTodosProductos();
    } catch (Exception e) {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
      return responseDTO;
    }
  }

  /**
   * Endpoint para traer una publicacion desde la db segun id que me llegue por request
   *
   * @param idPublicacion
   * @return ResponseDTO con el resultado de la acción
   */
  @GetMapping("/leer/{idPublicacion}")
  public ResponseDTO getPublicacionById(@PathVariable Long idPublicacion) {
    try {
      return publicacionesService.leerProducto(idPublicacion);
    } catch (Exception e) {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
      return responseDTO;
    }
  }

  /**
   * Endpoint para actualizar algunos campos de la publicacion que se encuentra en la base de datos
   *
   * @param publicacionesDTO
   * @return ResponseDTO con el resultado de la acción
   */
  @PutMapping("/actualizar")
  public ResponseDTO actualizarCamposPublicacion(@RequestBody PublicacionesDTO publicacionesDTO) {
    try {
      return publicacionesService.updatePublicacion(publicacionesDTO);
    } catch (Exception e) {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
      return responseDTO;
    }
  }

  /**
   * Endpoint para eliminar una imagen asociada a la publicacion desde cloudinary y luego en el
   * registro de la db
   *
   * @param idPublicacion
   * @return ResponseDTO con el resultado de la acción
   */
  @DeleteMapping("/eliminar-imagen/{idPublicacion}")
  public ResponseDTO eliminarImagenPublicacion(@PathVariable Long idPublicacion) {
    try {
      return publicacionesService.deleteImagenPublicacion(idPublicacion);
    } catch (Exception e) {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
      return responseDTO;
    }
  }

  /**
   * Endpoint para subir una imagen a Cloudinary y asociarlo a un registro de publicacion en la db
   *
   * @param idPublicacion
   * @param file
   * @return ResponseDTO con el resultado de la acción
   */
  @PostMapping(value = "/asociar-imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseDTO asociarImagen(
      @RequestPart("idPublicacion") Long idPublicacion, @RequestPart("file") MultipartFile file) {
    try {
      return publicacionesService.asociarImagenPublicacion(idPublicacion, file);
    } catch (Exception e) {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
      return responseDTO;
    }
  }

  /**
   * Endpoint para eliminar registro de publicacion en la db, como también la imagen en Cloudinary
   *
   * @param idPublicacion
   * @return ResponseDTO con el resultado de la acción
   */
  @DeleteMapping("/eliminar/{idPublicacion}")
  public ResponseDTO eliminarPublicacion(@PathVariable Long idPublicacion) {
    try {
      return publicacionesService.deletePublicaicon(idPublicacion);
    } catch (Exception e) {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
      return responseDTO;
    }
  }
}
