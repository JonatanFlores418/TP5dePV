package ar.edu.unju.fi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.DTO.MateriaDTO;
import ar.edu.unju.fi.service.CarreraService;
import ar.edu.unju.fi.service.DocenteService;
import ar.edu.unju.fi.service.MateriaService;
import jakarta.validation.Valid;

@Controller
public class MateriaController {

    @Autowired
    private MateriaDTO nuevaMateria;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private CarreraService cs;
    
    @Autowired
    private DocenteService ds;

    @GetMapping("/formularioMateria")
    public ModelAndView getFormMateria() {
        ModelAndView modelView = new ModelAndView("materia/formMateria");
        modelView.addObject("nuevaMateria", nuevaMateria);
        modelView.addObject("listaCarreras", cs.MostrarCarrera());
        modelView.addObject("listaDocente" , ds.MostrarDocente());
        modelView.addObject("band", false);
        return modelView;
    }

    @GetMapping("/listadoMateria")
    public ModelAndView getListadoMateria() {
        ModelAndView modelView = new ModelAndView("materia/listadoDeMaterias");
        modelView.addObject("listadoMaterias", materiaService.mostrarMaterias());
        return modelView;
    }

    @GetMapping("/listadoMateriaInactiva")
    public ModelAndView getListadoMateriaInactiva() {
        ModelAndView modelView = new ModelAndView("materia/listaDeMateriasInactivos");
        modelView.addObject("listadoMaterias", materiaService.mostrarMateriasInactivas());
        return modelView;
    }

    @GetMapping("/listadoAlumnoMateria/{codigo}")
    public ModelAndView getListadoAlumnoCarrera(@PathVariable(name = "codigo") String codigo) {
        ModelAndView modelView = new ModelAndView("materia/listaDeAlumnosDeMateria");
        modelView.addObject("materia", materiaService.buscarMateria(codigo));
        return modelView;
    }

    @PostMapping("/guardarMateria")
    public ModelAndView saveMateria(@Valid @ModelAttribute("nuevaMateria") MateriaDTO materia, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelView = new ModelAndView("materia/formMateria");
            modelView.addObject("nuevaMateria", materia);
            modelView.addObject("listaCarreras", cs.MostrarCarrera());
            modelView.addObject("listaDocente" , ds.MostrarDocente());
            modelView.addObject("band", false);
            return modelView;
        }

        materiaService.guardarMateria(materia);
        return new ModelAndView("redirect:/listadoMateria");
    }

    @GetMapping("/darDeBajaAlumnoDeMateria/{codigoMateria}/{LU}")
    public ModelAndView darDeBajaAlumnoDeMateria(@PathVariable(name = "codigoMateria") String codigoMateria, @PathVariable(name = "LU") String LU) {
        materiaService.darDeBajaAlumno(codigoMateria, LU);
        ModelAndView modelView = new ModelAndView("materia/listaDeAlumnosDeMateria");
        modelView.addObject("materia", materiaService.buscarMateria(codigoMateria));
        return modelView;
    }

    @GetMapping("/borrarMateria/{codigo}")
    public ModelAndView borrarMateria(@PathVariable(name = "codigo") String codigo) {
        materiaService.borrarMateria(codigo);
        return new ModelAndView("redirect:/listadoMateria");
    }
    
    @GetMapping("/borrarDefinitivoMateria/{codigo}")
    public ModelAndView borrarDefinitivoMateria(@PathVariable(name = "codigo") String codigo) {
        materiaService.borrarDefinitivoMateria(codigo);
        return new ModelAndView("redirect:/listadoMateria");
    }

    @GetMapping("/darDeAltaMateria/{codigo}")
    public ModelAndView darDeAltaMateria(@PathVariable(name = "codigo") String codigo) {
        materiaService.darDeAlta(codigo);
        return new ModelAndView("redirect:/listadoMateriaInactiva");
    }

    @GetMapping("/modificarMateria/{codigo}")
    public ModelAndView mostrarFormularioModificarMateria(@PathVariable("codigo") String codigo) {
        ModelAndView modelView = new ModelAndView("materia/formMateria");
        MateriaDTO materia = materiaService.buscarMateria(codigo);
        modelView.addObject("nuevaMateria", materia);
        modelView.addObject("listaCarreras", cs.MostrarCarrera());
        modelView.addObject("listaDocente" , ds.MostrarDocente());
        modelView.addObject("band", true);
        return modelView;
    }

    @PostMapping("/GuardarModMateria")
    public ModelAndView guardarModificacionMateria(@Valid @ModelAttribute("nuevaMateria") MateriaDTO materia, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelView = new ModelAndView("materia/formMateria");
            modelView.addObject("nuevaMateria", materia);
            modelView.addObject("listaCarreras", cs.MostrarCarrera());
            modelView.addObject("listaDocente" , ds.MostrarDocente());
            modelView.addObject("band", true);
            return modelView;
        }

        materiaService.modificarMateria(materia);
        return new ModelAndView("redirect:/listadoMateria");
    }
}