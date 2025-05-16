package ar.com.rodrigoperdomo.server.services;

import ar.com.rodrigoperdomo.server.entities.Publicaciones;
import ar.com.rodrigoperdomo.server.repositories.IPublicacionesRepository;
import ar.com.rodrigoperdomo.server.services.interfaces.ICloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

import static ar.com.rodrigoperdomo.server.utils.Constantes.*;

@Service
public class ICloudinaryServiceImpl implements ICloudinaryService {

  private final Cloudinary cloudinary;
  @Autowired IPublicacionesRepository publicacionesRepository;

  public ICloudinaryServiceImpl() {
    this.cloudinary =
        new Cloudinary(
            ObjectUtils.asMap(
                CLOUD_NAME_KEY,
                System.getenv(CLOUDINARY_CLOUD_NAME_ENV),
                API_KEY_NAME_KEY,
                System.getenv(CLOUDINARY_API_KEY_ENV),
                API_SECRET_KEY,
                System.getenv(CLOUDINARY_SECRET_KEY_ENV)));
  }

  /**
   * Método para subir imagen a Cloudinary
   *
   * @param file
   * @return String url_secure de la imagen guardada
   * @throws IOException
   */
  @Override
  public String uploadFile(MultipartFile file) throws IOException {
    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    return uploadResult.get(SECURE_URL_KEY).toString();
  }

  /**
   * Elimina la imagen asociada a una publicación en Cloudinary
   *
   * @param idPublicacion
   */
  @Override
  public void deleteFile(Long idPublicacion) {
    Publicaciones publicacion =
        publicacionesRepository
            .findById(idPublicacion)
            .orElseThrow(() -> new NoSuchElementException(PUBLICACION_NOT_FOUND));
    String secureUrl = publicacion.getImagenUrl();
    if (secureUrl == null || secureUrl.isBlank()) {
      throw new IllegalStateException(PUBLICACION_IMAGEN_NOT_FOUND);
    }
    String publicId = extraerPublicIdDesdeSecureUrl(secureUrl);
    try {
      cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
      publicacion.setImagenUrl(null);
      publicacionesRepository.save(publicacion);
    } catch (IOException e) {
      throw new RuntimeException(ERROR_DELETE_IMAGEN_CLOUDINARY, e);
    }
  }

  /**
   * Busca una publicacion que me llega por parametro y asocia la imagen al campo ImagenUrl, tambien
   * lo sube a Cloudinary
   *
   * @param idPublicacion
   * @param file
   * @throws IOException
   */
  @Override
  public void asociarImagenPublicacion(Long idPublicacion, MultipartFile file) throws IOException {
    Publicaciones publicacionByRequest =
        publicacionesRepository
            .findById(idPublicacion)
            .orElseThrow(() -> new NoSuchElementException(PUBLICACION_NOT_FOUND));
    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    String secureUrl = uploadResult.get(SECURE_URL_KEY).toString();
    publicacionByRequest.setImagenUrl(secureUrl);
    publicacionesRepository.save(publicacionByRequest);
  }

  /**
   * Metodo para extraer el public Id desde el Secure URL de CLoudinary
   *
   * @param secureUrl
   * @return String Public ID
   */
  private String extraerPublicIdDesdeSecureUrl(String secureUrl) {
    String[] partes = secureUrl.split(UPLOAD_PATH);
    if (partes.length < 2) {
      throw new IllegalArgumentException(URL_NO_VALIDA_PATH_FILE_MESSAGE);
    }
    String rutaConExtension = partes[1];
    String[] subPartes = rutaConExtension.split(BARRA);
    String archivo = subPartes[subPartes.length - 1];
    return archivo.replaceAll(BARRA_REGEXP, VACIO);
  }
}
