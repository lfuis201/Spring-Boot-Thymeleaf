package pe.edu.tecsup.springwebthymeleaf.controllers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.tecsup.springwebthymeleaf.entities.Categoria;
import pe.edu.tecsup.springwebthymeleaf.entities.Producto;
import pe.edu.tecsup.springwebthymeleaf.services.CategoriaService;
import pe.edu.tecsup.springwebthymeleaf.services.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	private Logger logger = LoggerFactory.getLogger(ProductoController.class);
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ProductoService productoService;
	@Value("${app.storage.path}")
	private String STORAGEPATH;

	@GetMapping("/")
	public String index(Model model) throws Exception {
		logger.info("call index()");
		List<Producto> productos = productoService.findAll();
		model.addAttribute("productos", productos);
		return "productos/index";
		
	}

	@GetMapping("/images/{filename:.+}")
	public ResponseEntity<Resource> images(@PathVariable String filename) throws Exception {
		logger.info("call images(filename: " + filename + ")");
		Path path = Paths.get(STORAGEPATH).resolve(filename);
		logger.info("Path: " + path);
		if (!Files.exists(path)) {
			return ResponseEntity.notFound().build();
		}
		Resource resource = new UrlResource(path.toUri());
		logger.info("Resource: " + resource);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + resource.getFilename() + "\"")
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Paths.get(STORAGEPATH).resolve(filename)))
				.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength())).body(resource);
	}

	@GetMapping("/create")
	public String create(Model model) throws Exception {
		logger.info("call create()");
		List<Categoria> categorias = categoriaService.findAll();
		model.addAttribute("categorias", categorias);
		Producto producto = new Producto();
		model.addAttribute("producto", producto);
		return "productos/create";
	}

	@PostMapping("/store")
	public String store(@ModelAttribute("producto") Producto producto, Errors errors,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttrs) throws Exception {
		logger.info("call store(producto: " + producto + ")");
		if (file != null && !file.isEmpty()) {
			String filename = System.currentTimeMillis()
					+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			producto.setImagen_nombre(filename);
			if (Files.notExists(Paths.get(STORAGEPATH))) {
				Files.createDirectories(Paths.get(STORAGEPATH));
			}
			Files.copy(file.getInputStream(), Paths.get(STORAGEPATH).resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
		}

		producto.setEstado(1);
		productoService.save(producto);
		redirectAttrs.addFlashAttribute("message", "Registro guardado correctamente");
		return "redirect:/productos/";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) throws Exception {
		logger.info("edit edit(id: " + id + ")");
		List<Categoria> categorias = categoriaService.findAll();
		model.addAttribute("categorias", categorias);
		Producto producto = productoService.findById(id);
		model.addAttribute("producto", producto);
		return "productos/edit";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute("producto") Producto producto, Errors errors,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttrs) throws Exception {
		logger.info("call update(producto: " + producto + ")");
		if (file != null && !file.isEmpty()) {
			String filename = System.currentTimeMillis()
					+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			producto.setImagen_nombre(filename);
			if (Files.notExists(Paths.get(STORAGEPATH))) {
				Files.createDirectories(Paths.get(STORAGEPATH));
			}
			Files.copy(file.getInputStream(), Paths.get(STORAGEPATH).resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
		}
		productoService.save(producto);
		redirectAttrs.addFlashAttribute("message", "Registro guardado correctamente");
		return "redirect:/productos/";
	}
	

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) throws Exception {
		logger.info("edit delete(id: " + id + ")");
		productoService.deleteById(id);
		redirectAttrs.addFlashAttribute("message", "Registro eliminado correctamente");
		return "redirect:/productos/";
	}
	
	@GetMapping("/search")
	public String search(Model model, @ModelAttribute("nombre") String nombre, RedirectAttributes redirectAttrs) throws Exception {
		List<Producto> lista = productoService.findByNameContaining(nombre);
		model.addAttribute("productos", lista);
		return "productos/search";
	}
}
