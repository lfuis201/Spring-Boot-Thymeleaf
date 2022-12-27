package pe.edu.tecsup.springwebthymeleaf.entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private Integer orden;
	@OneToMany(mappedBy="categoria")
	private List<Producto> productos = new ArrayList<>();
	public Long getId() {
	return id;
	}
	public void setId(Long id) {
	this.id = id;
	}
	public String getNombre() {
	return nombre;
	}
	public void setNombre(String nombre) {
	this.nombre = nombre;
	}
	public Integer getOrden() {
	return orden;
	}
	public void setOrden(Integer orden) {
	this.orden = orden;
	}
	public List<Producto> getProductos() {
	return productos;
	}
	public void setProductos(List<Producto> productos) {
	this.productos = productos;
	}
	@Override
	public String toString() {
	return "Categoria [id=" + id + ", nombre=" + nombre + ", orden=" + orden + "]";
	}
}
