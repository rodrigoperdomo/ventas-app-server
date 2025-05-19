package ar.com.rodrigoperdomo.server.controllers;

import ar.com.rodrigoperdomo.server.dtos.CompraVentaRequestDTO;
import ar.com.rodrigoperdomo.server.dtos.ResponseDTO;
import ar.com.rodrigoperdomo.server.services.interfaces.ICompraVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compra-venta")
public class CompraVentaController {

  @Autowired ICompraVentaService compraVentaService;

  /**
   * Endpoint que reduce la cantidad del producto que me llega por request y envia los datos del
   * vendedor/comprador a cada uno.
   *
   * @param compraVentaRequestDTO
   * @return
   */
  @PostMapping("/compra-venta")
  public ResponseDTO compraVenta(@RequestBody CompraVentaRequestDTO compraVentaRequestDTO) {
    try {
      return compraVentaService.compraVentaAction(compraVentaRequestDTO);
    } catch (Exception e) {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseDTO.setBody(null);
      responseDTO.setMessage(e.getMessage());
      return responseDTO;
    }
  }
}
