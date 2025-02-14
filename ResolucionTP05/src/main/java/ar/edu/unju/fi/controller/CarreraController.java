package ar.edu.unju.fi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.DTO.CarreraDTO;
import ar.edu.unju.fi.service.CarreraService;
import jakarta.validation.Valid;

@Controller
public class CarreraController {
    
	@Autowired
    private CarreraDTO nuevacarreraDTO;
    
    @Autowired
    private CarreraService cs;
    
    @GetMapping("/formularioCarrera")
    public ModelAndView getFormCarrera() {
        ModelAndView modelView = new ModelAndView("carrera/formCarrera");
        modelView.addObject("nuevaCarrera", nuevacarreraDTO);
        modelView.addObject("band", false);
        return modelView;
    }

    @GetMapping("/listadoCarrera")
    public ModelAndView getListadoCarrera() {
        ModelAndView modelView = new ModelAndView("carrera/listaDeCarreras");
        modelView.addObject("listadoCarreras", cs.MostrarCarrera());
        return modelView;
    }

    @GetMapping("/listadoCarreraInactivas")
    public ModelAndView getListadoInactivasCarrera() {
        ModelAndView modelView = new ModelAndView("carrera/listaDeCarreraInactivos");
        modelView.addObject("listadoCarreras", cs.MostrarCarreraInactivas());
        return modelView;
    }

    @GetMapping("/listadoAlumnoCarrera/{codigo}")
    public ModelAndView getListadoAlumnoCarrera(@PathVariable(name = "codigo") String codigo) {
        ModelAndView modelView = new ModelAndView("carrera/listaDeAlumnosDeCarrera");
        modelView.addObject("carrera", cs.buscaCarrera(codigo));
        return modelView;
    }

    @GetMapping("/listadoMateriaCarrera/{codigo}")
    public ModelAndView getListadoMateriaCarrera(@PathVariable(name = "codigo") String codigo) {
        ModelAndView modelView = new ModelAndView("carrera/listaDeMateriasDeCarrera");
        modelView.addObject("carrera", cs.buscaCarrera(codigo));
        return modelView;
    }

    @PostMapping("/guardarCarrera")
    public ModelAndView saveCarrera(@Valid @ModelAttribute("nuevaCarrera") CarreraDTO carrera, BindingResult bindingResult) {
        ModelAndView modelView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelView.setViewName("carrera/formCarrera");
            modelView.addObject("nuevaCarrera", carrera);
            modelView.addObject("band", false); // Si es necesario manejar el band en caso de errores
            return modelView;
        }
        cs.guardarCarrera(carrera);
        modelView.setViewName("carrera/listaDeCarreras");
        modelView.addObject("listadoCarreras", cs.MostrarCarrera());
        
        
        return modelView;
    }

    @GetMapping("/borrarCarrera/{codigo}")
    public ModelAndView borrarCarrera(@PathVariable(name = "codigo") String codigo) {
        cs.borrarCarrera(codigo);
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("carrera/listaDeCarreras");
        modelView.addObject("listadoCarreras", cs.MostrarCarrera());
        return modelView;
    }
    
    @GetMapping("/borrarDefinitivoCarrera/{codigo}")
    public ModelAndView borrarDefinitivoCarrera(@PathVariable(name = "codigo") String codigo) {
        cs.borrarDefinitivoCarrera(codigo);
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("carrera/listaDeCarreras");
        modelView.addObject("listadoCarreras", cs.MostrarCarrera());
        return modelView;
    }

    @GetMapping("/darDeBajaAlumnoDeCarrera/{codigoCarrera}/{LU}")
    public ModelAndView darDeBajaAlumnoDeCarrera(@PathVariable(name = "codigoCarrera") String codigoCarrera, @PathVariable(name = "LU") String LU) {
        cs.darDeBajaAlumnoDeCarrera(codigoCarrera, LU);
        ModelAndView modelView = new ModelAndView("carrera/listaDeCarreras");
        modelView.addObject("listadoCarreras", cs.MostrarCarrera());
        return modelView;
    }

    @GetMapping("/DardeAltaCarrera/{codigo}")
    public ModelAndView DarDeAlta(@PathVariable(name = "codigo") String codigo) {
        cs.darDeAltaCarrera(codigo);
        ModelAndView modelView = new ModelAndView("carrera/listaDeCarreras");
        modelView.addObject("listadoCarreras", cs.MostrarCarrera());
        return modelView;
    }

    @GetMapping("/modificarCarrera/{codigo}")
    public ModelAndView mostrarFormularioModificarCarrera(@PathVariable("codigo") String codigo) {
        ModelAndView modelView = new ModelAndView("carrera/formCarrera");
        modelView.addObject("nuevaCarrera", cs.buscaCarrera(codigo));
        modelView.addObject("band", true);
        return modelView;
    }

    @PostMapping("/GuardarModCarrera")
    public ModelAndView guardarModificacionCarrera(@Valid @ModelAttribute("nuevaCarrera") CarreraDTO carrera, BindingResult bindingResult) {
        ModelAndView modelView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelView.setViewName("carrera/formCarrera");
            modelView.addObject("nuevaCarrera", carrera);
            modelView.addObject("band", true); // Para manejar la modificación
            return modelView;
        }
        cs.modificarCarrera(carrera);
        modelView.setViewName("carrera/listaDeCarreras");
        modelView.addObject("listadoCarreras", cs.MostrarCarrera());
        return modelView;
    }

    @GetMapping("/darDeBajaMateriaDeCarrera/{codigoCarrera}/{codigoMateria}")
    public ModelAndView darDeBajaMateriaDeCarrera(@PathVariable(name = "codigoCarrera") String codigoCarrera, @PathVariable(name = "codigoMateria") String codigoMateria) {
        cs.darDeBajaMateriaDeCarrera(codigoCarrera, codigoMateria);
        ModelAndView modelView = new ModelAndView("carrera/listaDeCarreras");
        modelView.addObject("listadoCarreras", cs.MostrarCarrera());
        
        return modelView;
    }
}