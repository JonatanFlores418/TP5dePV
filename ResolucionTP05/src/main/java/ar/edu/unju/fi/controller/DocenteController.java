package ar.edu.unju.fi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;

import ar.edu.unju.fi.DTO.DocenteDTO;
import ar.edu.unju.fi.service.DocenteService;


@Controller
public class DocenteController {
	
	
	@Autowired
	DocenteService docenteService;
	
	@Autowired
	DocenteDTO nuevoDocenteDTO;
	
	@GetMapping("/formularioDocente")
	public ModelAndView getFormDocente() {
		ModelAndView modelView = new ModelAndView("docente/formDocente");
		modelView.addObject("nuevoDocente",nuevoDocenteDTO);
		modelView.addObject("band",false);
		
		return modelView;
	}
	
	@GetMapping("/listadoDocente")
    public ModelAndView getListadoAlumno() {
    	ModelAndView modelView = new ModelAndView("docente/listadoDeDocentes");
    	modelView.addObject("listadoDocentes",docenteService.MostrarDocente());
    	return modelView;
    }
	
	@GetMapping("/listadoDocenteInactivos")
    public ModelAndView getListadoAlumnoInactivos() {
    	ModelAndView modelView = new ModelAndView("docente/listaDeDocentesInactivos");
    	modelView.addObject("listadoDocentes",docenteService.MostrarDocenteInactivos());
    	return modelView;
    }
	
	@PostMapping("/guardarDocente")
    public ModelAndView saveDocente(@Valid @ModelAttribute("nuevoDocente") DocenteDTO docenteDTO, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelView = new ModelAndView("docente/formDocente");
            modelView.addObject("nuevoDocente", docenteDTO);
            modelView.addObject("band", false);
            return modelView;
        }

        docenteService.save(docenteDTO);
        return new ModelAndView("redirect:/listadoDocente");
    }
	
	@GetMapping("/borrarDocente/{legajo}")
	public ModelAndView borrarDocente(@PathVariable(name="legajo") String legajo){
		docenteService.deleteByLegajo(legajo);
		ModelAndView modelView = new ModelAndView("docente/listadoDeDocentes");
		modelView.addObject("listadoDocentes",docenteService.MostrarDocente());
		
		return modelView;
	}
	
	@GetMapping("/borrarDefinitivoDocente/{legajo}")
	public ModelAndView borrarDefinitivoDocente(@PathVariable(name="legajo") String legajo){
		docenteService.deletDefinitiveeByLegajo(legajo);
		ModelAndView modelView = new ModelAndView("docente/listadoDeDocentes");
		modelView.addObject("listadoDocentes",docenteService.MostrarDocente());
		
		return modelView;
	}
	
	@GetMapping("/modificarDocente/{legajo}")
	public ModelAndView mostrarFormularioModificarAlumno(@PathVariable(name="legajo") String legajo){
		ModelAndView modelView = new ModelAndView("docente/formDocente");
		modelView.addObject("nuevoDocente", docenteService.buscaDocente(legajo));
		modelView.addObject("band",true);
		return modelView;
	}
	
	 @PostMapping("/guardarModDocente")
	    public ModelAndView guardarModificacionAlumno(@Valid @ModelAttribute("nuevoDocente") DocenteDTO docenteDTO, BindingResult result) {
	        if (result.hasErrors()) {
	            ModelAndView modelView = new ModelAndView("docente/formDocente");
	            modelView.addObject("nuevoDocente", docenteDTO);
	            modelView.addObject("band", true);
	            return modelView;
	        }

	        docenteService.edit(docenteDTO);
	        return new ModelAndView("redirect:/listadoDocente");
	    }
	
	@GetMapping("/darDeAlta/{legajo}")
	public ModelAndView darDeAlta(@PathVariable(name="legajo") String legajo){
		docenteService.darDeAlta(legajo);
		ModelAndView modelView = new ModelAndView("docente/listadoDeDocentes");
		modelView.addObject("listadoDocentes",docenteService.MostrarDocente());
		
		return modelView;
	}
	
}