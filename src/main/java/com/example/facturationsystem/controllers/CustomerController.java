package com.example.facturationsystem.controllers;

import com.example.facturationsystem.models.entities.Customer;
import com.example.facturationsystem.services.CustomerService;
import com.example.facturationsystem.services.UploadFileService;
import com.example.facturationsystem.util.paginator.PageRender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Map;

@Controller
@SessionAttributes("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UploadFileService uploadFileService;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @GetMapping({ "/", "index", "/home" })
    public String index(Model model) {
        model.addAttribute("title", "Sistema de facturación");
        model.addAttribute("h1", "Bienvenido");
        return "index";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String create(Map<String, Object> model) {
        Customer customer = new Customer();

        model.put("title", "Formulario de registro");
        model.put("customer", customer);
        return "form";
    }

    /**
     * Recupera una lista paginada de clientes y la renderiza en la vista 'list'.
     *
     * @param page           el número de página a recuperar (el valor
     *                       predeterminado es 0 si
     *                       no se proporciona)
     * @param model          el objeto Model para agregar atributos para renderizar
     * @param authentication la información de autenticación del usuario que realiza
     *                       la solicitud
     * @param request        la solicitud HTTP
     * @return el nombre de la vista a renderizar, en este caso, 'list'
     */
    @GetMapping("/list")
    public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
            Authentication authentication, HttpServletRequest request) {

        if (authentication != null) {
            logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info(("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): " +
                    "Usuario autenticado, username: ").concat(auth.getName()));

            // Forma 1
            if (hasRole("ROLE_ADMIN")) {
                logger.info("Hola ".concat(auth.getName()).concat(", tienes acceso"));
            } else {
                logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso"));
            }

            // Forma 2
            SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(
                    request, "");
            if (securityContext.isUserInRole("ROLE_ADMIN")) {
                logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName())
                        .concat(", tienes acceso"));
            } else {
                logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName())
                        .concat(" NO tienes acceso"));
            }

            // Forma 3
            if (request.isUserInRole("ROLE_ADMIN")) {
                logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(", tienes acceso"));
            } else {
                logger.info(
                        "Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso"));
            }
        }
        Pageable pageRequest = PageRequest.of(page, 10);
        Page<Customer> customers = customerService.findAll(pageRequest);
        PageRender<Customer> pageRender = new PageRender<>("/list", customers);

        model.addAttribute("title", "Lista de clientes");
        model.addAttribute("customers", customers);
        model.addAttribute("page", pageRender);
        return "list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/form/{id}")
    public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes attributes) {
        Customer customer;

        if (id > 0) {
            customer = customerService.findById(id);
            if (customer == null) {
                attributes.addFlashAttribute("error", "El id del cliente no existe");
                return "redirect:/list";
            }
        } else {
            attributes.addFlashAttribute("error", "El id del cliente no puede ser 0");
            return "redirect:/list";
        }
        model.put("customer", customer);
        model.put("title", "Editar");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String save(@Valid Customer customer, BindingResult result, Model model,
            @RequestParam("file") MultipartFile photo,
            RedirectAttributes attributes, SessionStatus status) {

        if (result.hasFieldErrors()) {
            model.addAttribute("title", "Formulario de registro");
            return "form";
        }

        if (!photo.isEmpty()) {
            if (customer.getId() != null
                    && customer.getId() > 0
                    && customer.getPhoto() != null
                    && !customer.getPhoto().isEmpty()) {
                uploadFileService.delete(customer.getPhoto());
            }

            String uniqueFilename;
            try {
                uniqueFilename = uploadFileService.copy(photo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            customer.setPhoto(uniqueFilename);
            attributes.addFlashAttribute("info", "Se ha subido exitosamente la foto: '" + uniqueFilename + "'");
        }
        String flashMessage = (customer.getId() != null) ? "Cliente editado exitosamente"
                : "Cliente creado exitosamente";

        customerService.save(customer);
        status.setComplete();
        attributes.addFlashAttribute("success", flashMessage);
        return "redirect:/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes attributes) {
        if (id > 0) {
            Customer customer = customerService.findById(id);

            customerService.delete(id);
            attributes.addFlashAttribute("success", "Cliente eliminado exitosamente");

            if (uploadFileService.delete(customer.getPhoto())) {
                attributes.addFlashAttribute("info", "Foto eliminada exitosamente");
            }
        }
        return "redirect:/list";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "see/{id}")
    public String see(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes attributes) {
        Customer customer = customerService.fetchByIdWithInvoices(id);

        if (customer == null) {
            attributes.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/list";
        } else {
            model.put("customer", customer);
            model.put("title", "Detalles del cliente " + customer.getName() + " " + customer.getLastName());
        }
        return "see";
    }

    /**
     * Método para ver una foto cargada.
     * <p>
     * Nota: Permite que esta parte de la URL tenga extensiones de archivo y otros
     * caracteres especiales
     *
     * @param filename el nombre del archivo de la foto que se desea ver
     * @return ResponseEntity con el recurso de la foto y las cabeceras de respuesta
     *         apropiadas para descargar el archivo.
     * @throws RuntimeException si ocurre un error al cargar la foto debido a una
     *                          URL malformada.
     */
    @Secured("ROLE_USER")
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> viewPhoto(@PathVariable String filename) {
        Resource resource;

        try {
            resource = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Verifica si el usuario autenticado tiene un rol específico.
     *
     * @param role el rol a verificar
     * @return true si el usuario tiene el rol especificado, false de lo contrario
     */
    private boolean hasRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return false;
        }
        Authentication auth = context.getAuthentication();
        if (auth == null) {
            return false;
        }

        // Cualquier clase Role o que representa un Role en nuestra aplicación, tiene
        // que implementar la interface GrantedAuthority
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(role));
    }
}
