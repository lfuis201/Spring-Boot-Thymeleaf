package pe.edu.tecsup.springwebthymeleaf.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.tecsup.springwebthymeleaf.entities.Producto;
public interface ProductoRepository extends JpaRepository<Producto, Long>{
	@Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:name%")
	List<Producto> searchByNameLike(@Param("name") String name);
}
