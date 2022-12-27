package pe.edu.tecsup.springwebthymeleaf.services;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.tecsup.springwebthymeleaf.entities.Producto;
import pe.edu.tecsup.springwebthymeleaf.repositories.ProductoRepository;

@Service
@Transactional 
public class ProductoServiceImpl implements ProductoService{
	@Autowired
	private ProductoRepository productoRepository;
	@Override
	public List<Producto> findAll() {
	return productoRepository.findAll();
	}
	@Override
	public Producto findById(Long id) {
	return productoRepository.findById(id).get();
	}
	@Override
	public Producto save(Producto producto) {
	return productoRepository.save(producto);
	}
	@Override
	public void deleteById(Long id) {
	productoRepository.deleteById(id);
	}
	
	@Override
	public List<Producto> findByNameContaining(String name) {
		return productoRepository.searchByNameLike(name);
	}
}
