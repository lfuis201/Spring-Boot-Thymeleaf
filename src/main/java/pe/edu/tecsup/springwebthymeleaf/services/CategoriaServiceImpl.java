package pe.edu.tecsup.springwebthymeleaf.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.tecsup.springwebthymeleaf.entities.Categoria;
import pe.edu.tecsup.springwebthymeleaf.repositories.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService{
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Override
	public List<Categoria> findAll() {
	return categoriaRepository.findAll();
	}

}
