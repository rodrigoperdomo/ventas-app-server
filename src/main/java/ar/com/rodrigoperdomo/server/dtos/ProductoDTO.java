package ar.com.rodrigoperdomo.server.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
  private String nombreProducto;
  private String descripcion;
  private Long precio;
  private Long cantidadProducto;
  private Long idUsuario;
}
