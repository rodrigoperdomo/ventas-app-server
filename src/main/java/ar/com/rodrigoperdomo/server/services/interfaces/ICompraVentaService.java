package ar.com.rodrigoperdomo.server.services.interfaces;

import ar.com.rodrigoperdomo.server.dtos.CompraVentaRequestDTO;
import ar.com.rodrigoperdomo.server.dtos.ResponseDTO;

public interface ICompraVentaService {
  ResponseDTO compraVentaAction(CompraVentaRequestDTO compraVentaRequestDTO);
}
