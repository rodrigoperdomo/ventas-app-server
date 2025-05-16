package ar.com.rodrigoperdomo.server.repositories;

import ar.com.rodrigoperdomo.server.entities.Publicaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublicacionesRepository extends JpaRepository<Publicaciones, Long> {}
