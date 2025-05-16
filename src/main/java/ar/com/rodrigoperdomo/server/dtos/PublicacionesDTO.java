package ar.com.rodrigoperdomo.server.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionesDTO {
  private Long idProducto;
  private String nombreProducto;
  private String descripcionProducto;
  private Long precioProducto;
  private Long cantidadProducto;
  private String imagenUrl;
  private Long idUsuario;
}
