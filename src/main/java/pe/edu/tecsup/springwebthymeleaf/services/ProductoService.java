package pe.edu.tecsup.springwebthymeleaf.services;
import java.util.List;

import pe.edu.tecsup.springwebthymeleaf.entities.Producto;

public interface ProductoService {
	public List<Producto> findAll();
	public Producto findById(Long id);
	public Producto save(Producto producto);
	public void deleteById(Long id);
	public List<Producto>findByNameContaining(String name);
}
