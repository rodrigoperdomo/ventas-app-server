package ar.com.rodrigoperdomo.server.repositories;

import ar.com.rodrigoperdomo.server.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuarios, Long> {
  @Query(value = "SELECT u From Usuarios u WHERE u.username=?1")
  Usuarios getUsuarioByUsername(String username);
}
