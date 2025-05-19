package ar.com.rodrigoperdomo.server.services;

import static ar.com.rodrigoperdomo.server.utils.Constantes.*;

import ar.com.rodrigoperdomo.server.dtos.CompraVentaRequestDTO;
import ar.com.rodrigoperdomo.server.dtos.ResponseDTO;
import ar.com.rodrigoperdomo.server.entities.Publicaciones;
import ar.com.rodrigoperdomo.server.entities.Usuarios;
import ar.com.rodrigoperdomo.server.repositories.IPublicacionesRepository;
import ar.com.rodrigoperdomo.server.repositories.IUsuarioRepository;
import ar.com.rodrigoperdomo.server.services.interfaces.ICompraVentaService;
import ar.com.rodrigoperdomo.server.services.interfaces.IEmailSenderService;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ICompraVentaServiceImpl implements ICompraVentaService {

  @Autowired IUsuarioRepository usuarioRepository;
  @Autowired IPublicacionesRepository publicacionesRepository;
  @Autowired IEmailSenderService emailSenderService;

  /**
   * Metodo de CompraVenta que reduce la cantidad de producto y también envia mail al comprodor
   * vendedor con los datos del otro
   *
   * @param compraVentaRequestDTO
   * @return ResponseDTO con el resultado del acción
   */
  @Override
  public ResponseDTO compraVentaAction(CompraVentaRequestDTO compraVentaRequestDTO) {
    ResponseDTO responseDTO = new ResponseDTO();
    try {
      // Busco usuario Comprador y productos en la db
      Usuarios comprador =
          usuarioRepository
              .findById(compraVentaRequestDTO.getIdComprador())
              .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
      Publicaciones publicacionVendida =
          publicacionesRepository
              .findById(compraVentaRequestDTO.getIdProducto())
              .orElseThrow(() -> new NoSuchElementException(PUBLICACION_NOT_FOUND));
      // Reduzco la cantidad del producto y guardo en la db
      if (publicacionVendida.getCantidadProducto() > ZERO_LONG) {
        Long newCantidadProductoVendido = publicacionVendida.getCantidadProducto() - ONE_LONG;
        publicacionVendida.setCantidadProducto(newCantidadProductoVendido);
        publicacionesRepository.save(publicacionVendida);
      }
      // Envio mail comprador
      String asunto = ASUNTO_MAIL + SPACE + publicacionVendida.getNombreProducto();
      String cuerpoMailComprador =
          CUERPO_MAIL
              + SPACE
              + publicacionVendida.getNombreProducto()
              + SPACE
              + publicacionVendida.getDescripcionProducto()
              + SPACE
              + DATOS_CUERPO_MAIL_COMPRADOR
              + SPACE
              + comprador.getEmail()
              + SPACE
              + comprador.getNumero();
      emailSenderService.enviarCorreo(
          asunto, publicacionVendida.getUsuarios().getEmail(), cuerpoMailComprador);
      // Envio mail vendedor
      String cuerpoMailVendedor =
          CUERPO_MAIL
              + SPACE
              + publicacionVendida.getNombreProducto()
              + SPACE
              + publicacionVendida.getDescripcionProducto()
              + SPACE
              + DATOS_CUERPO_MAIL_VENDEDOR
              + SPACE
              + publicacionVendida.getUsuarios().getEmail()
              + SPACE
              + publicacionVendida.getUsuarios().getNumero();
      emailSenderService.enviarCorreo(asunto, comprador.getEmail(), cuerpoMailVendedor);
      responseDTO.setMessage(COMPRA_VENTAS_ACTION_SUCCESS);
      responseDTO.setStatus(HttpStatus.OK.value());
      responseDTO.setBody(null);
    } catch (Exception e) {
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
    }
    return responseDTO;
  }
}
