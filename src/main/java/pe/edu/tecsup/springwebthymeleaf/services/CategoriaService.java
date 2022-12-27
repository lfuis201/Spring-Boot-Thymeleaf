package pe.edu.tecsup.springwebthymeleaf.services;

import java.util.List;

import pe.edu.tecsup.springwebthymeleaf.entities.Categoria;

public interface CategoriaService {
	public List<Categoria> findAll();
}
