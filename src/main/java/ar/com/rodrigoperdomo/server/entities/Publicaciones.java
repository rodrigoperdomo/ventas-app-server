package ar.com.rodrigoperdomo.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publicaciones {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idProducto;

  @Column(name = "nombre_producto")
  private String nombreProducto;

  @Column(name = "descripcion_producto")
  private String descripcionProducto;

  @Column(name = "precio_producto")
  private Long precioProducto;

  @Column(name = "cantidad_producto")
  private Long cantidadProducto;

  @Column(name = "imagen_url")
  private String imagenUrl;

  @ManyToOne
  @JoinColumn(name = "id_usuario", referencedColumnName = "idUsuario")
  private Usuarios usuarios;
}
