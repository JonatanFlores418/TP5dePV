package ar.edu.unju.fi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.DTO.AlumnoDTO;
import ar.edu.unju.fi.map.AlumnoMapDTO;
import ar.edu.unju.fi.service.AlumnoService;
import ar.edu.unju.fi.service.CarreraService;
import ar.edu.unju.fi.service.MateriaService;
import jakarta.validation.Valid;

@Controller
public class AlumnoController {

    @Autowired
    AlumnoDTO nuevoAlumnoDTO;

    @Autowired
    AlumnoMapDTO alumnoMap;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    MateriaService materiaService;

    @Autowired
    CarreraService cs;

    @GetMapping("/formularioAlumno")
    public ModelAndView getFormAlumno() {
        ModelAndView modelView = new ModelAndView("alumno/formAlumno");
        modelView.addObject("nuevoAlumno", nuevoAlumnoDTO);
        modelView.addObject("listaMaterias", materiaService.mostrarMaterias());
        modelView.addObject("listaCarreras", cs.MostrarCarrera());
        modelView.addObject("band", false);
        return modelView;
    }

    @GetMapping("/listadoAlumno")
    public ModelAndView getListadoAlumno() {
        ModelAndView modelView = new ModelAndView("alumno/listaDeAlumnos");
        try {
            modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumno());
        } catch (Exception e) {
            modelView.addObject("error", "Error al obtener el listado de alumnos: " + e.getMessage());
        }
        return modelView;
    }

    @GetMapping("/listadoAlumnoInactivo")
    public ModelAndView getListadoInactivoAlumno() {
        ModelAndView modelView = new ModelAndView("alumno/listaDeAlumnosInactivos");
        try {
            modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumnoInactivos());
        } catch (Exception e) {
            modelView.addObject("error", "Error al obtener el listado de alumnos inactivos: " + e.getMessage());
        }
        return modelView;
    }
    
    @GetMapping("/listadoMateriasCarreras/{LU}")
    public ModelAndView getListadoMateriasCarreras(@PathVariable(name = "LU") String LU){
        ModelAndView modelView = new ModelAndView("alumno/listaDeMateriasCarrerasDeAlumno");
        try {
            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
        } catch (Exception e) {
            modelView.addObject("error", "Error al obtener el listado de alumnos inactivos: " + e.getMessage());
        }
        return modelView;
    }

    @PostMapping("/guardarAlumno")
    public ModelAndView saveAlumno(@Valid @ModelAttribute("nuevoAlumno") AlumnoDTO Alumno, BindingResult resultado) {
        ModelAndView modelView = new ModelAndView();
        try {
            if (resultado.hasErrors()) {
                modelView.setViewName("alumno/formAlumno");
                modelView.addObject("nuevoAlumno", Alumno);
                modelView.addObject("listaMaterias", materiaService.mostrarMaterias());
                modelView.addObject("listaCarreras", cs.MostrarCarrera());
            } else {
                alumnoService.guardarAlumno(Alumno);
                modelView.setViewName("alumno/listaDeAlumnos");
                modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumno());
            }
        } catch (Exception e) {
            modelView.addObject("error", "Error al guardar el alumno: " + e.getMessage());
            modelView.setViewName("alumno/formAlumno");
            modelView.addObject("nuevoAlumno", Alumno);
            modelView.addObject("listaMaterias", materiaService.mostrarMaterias());
            modelView.addObject("listaCarreras", cs.MostrarCarrera());
        }
        return modelView;
    }

    @GetMapping("/borrarAlumno/{LU}")
    public ModelAndView borrarAlumno(@PathVariable(name = "LU") String LU) {
        ModelAndView modelView = new ModelAndView("/Alumno/listaDeAlumnos");
        try {
            alumnoService.borrarAlumno(LU);
            modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumno());
        } catch (Exception e) {
            modelView.addObject("error", "Error al borrar el alumno: " + e.getMessage());
            modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumno());
        }
        return modelView;
    }
    
    @GetMapping("/borrarDefinitivoAlumno/{LU}")
    public ModelAndView borrarDefinitivoAlumno(@PathVariable(name = "LU") String LU) {
        ModelAndView modelView = new ModelAndView("/Alumno/listaDeAlumnos");
        try {
            alumnoService.borrarDefinitivoAlumno(LU);
            modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumno());
        } catch (Exception e) {
            modelView.addObject("error", "Error al borrar el alumno: " + e.getMessage());
            modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumno());
        }
        return modelView;
    }

    @GetMapping("/modificarAlumno/{LU}")
    public ModelAndView mostrarFormularioModificarAlumno(@PathVariable(name = "LU") String LU) {
        ModelAndView modelView = new ModelAndView("Alumno/formAlumno");
        try {
            modelView.addObject("nuevoAlumno", alumnoService.buscarAlumno(LU));
            modelView.addObject("listaMaterias", materiaService.mostrarMaterias());
            modelView.addObject("listaCarreras", cs.MostrarCarrera());
            modelView.addObject("band", false);
        } catch (Exception e) {
            modelView.addObject("error", "Error al cargar datos del alumno para modificar: " + e.getMessage());
        }
        return modelView;
    }

    @PostMapping("/guardarModAlumno")
    public ModelAndView guardarModificacionAlumno(@ModelAttribute("nuevoAlumno") AlumnoDTO Alumno) {
        ModelAndView modelView = new ModelAndView("Alumno/listaDeAlumnos");
        try {
            alumnoService.modificarAlumno(Alumno);
            modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumno());
        } catch (Exception e) {
            modelView.addObject("error", "Error al modificar el alumno: " + e.getMessage());
            modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumno());
        }
        return modelView;
    }

    @GetMapping("/DardeAltaAlumno/{LU}")
    public ModelAndView DarDeAltaAlumno(@PathVariable(name = "LU") String LU) {
        ModelAndView modelView = new ModelAndView("/Alumno/listaDeAlumnos");
        try {
            alumnoService.DardeAlta(LU);
            modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumno());
        } catch (Exception e) {
            modelView.addObject("error", "Error al dar de alta al alumno: " + e.getMessage());
            modelView.addObject("listadoAlumnos", alumnoService.mostrarAlumno());
        }
        return modelView;
    }
    @GetMapping("/darDeBajaMateria/{LU}/{codigoMateria}")
    public ModelAndView darDeBajaMateria(@PathVariable("LU") String LU, @PathVariable("codigoMateria") String codigoMateria) {
    	ModelAndView modelView = new ModelAndView("/Alumno/listaDeMAteriasCarrerasDeAlumno");
        try {
            alumnoService.darDeBajaMateria(LU, codigoMateria);
            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
            
        } catch (Exception e) {
            modelView.addObject("error", "Error al dar de baja la materias: " + e.getMessage());
            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
        }
        return modelView;
    }
    
    @GetMapping("/darDeBajaCarrera/{LU}/{codigoCarrera}")
    public ModelAndView darDeBajaCarrera(@PathVariable("LU") String LU, @PathVariable("codigoCarrera") String codigoCarrera) {
    	ModelAndView modelView = new ModelAndView("/Alumno/listaDeMAteriasCarrerasDeAlumno");
        try {
            alumnoService.darDeBajaCarrera(LU, codigoCarrera);
            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
            
        } catch (Exception e) {
            modelView.addObject("error", "Error al dar de baja la carrera: " + e.getMessage());
            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
        }
        return modelView;
    }
    
    @GetMapping("/darDeAltaMateria/{LU}/{codigoMateria}")
    public ModelAndView darDeAltaMateria(@PathVariable("LU") String LU, @PathVariable("codigoMateria") String codigoMateria) {
    	ModelAndView modelView = new ModelAndView("/Alumno/listaDeMAteriasCarrerasDeAlumno");
        try {
            alumnoService.darDeAltaMateria(LU, codigoMateria);
            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
            
        } catch (Exception e) {
            modelView.addObject("error", "Error al dar de baja la materias: " + e.getMessage());
            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
        }
        return modelView;
    }
    
    @GetMapping("/darDeAltaCarrera/{LU}/{codigoCarrera}")
    public ModelAndView darDeAltaCarrera(@PathVariable("LU") String LU, @PathVariable("codigoCarrera") String codigoCarrera) {
    	ModelAndView modelView = new ModelAndView("/Alumno/listaDeMateriasCarrerasDeAlumno");
        try {
            alumnoService.darDeAltaCarrera(LU, codigoCarrera);
            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
            
        } catch (Exception e) {
            modelView.addObject("error", "Error al dar de baja la materias: " + e.getMessage());
            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
        }
        return modelView;
    }
    @GetMapping("/listadoMateriasCarrerasIncripcion/{LU}")
    public ModelAndView getListadoMateriasCarrerasDisponibles(@PathVariable("LU") String LU){
        ModelAndView modelView = new ModelAndView("alumno/listaDeMateriasCarreras");
        try {
        	if(alumnoService.buscarAlumno(LU).getCarrera() != null){
	        	modelView.addObject("listadoMaterias",alumnoService.buscarAlumno(LU).getCarrera().getMaterias());
	            modelView.addObject("listadoCarreras",cs.MostrarCarrera());
	            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
        	}
        	else {
        		modelView.addObject("listadoCarreras",cs.MostrarCarrera());
                modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
        	}
        } catch (Exception e) {
            modelView.addObject("error", "Error al obtener el listado las materias y carreras disponibles: " + e.getMessage());
            if(alumnoService.buscarAlumno(LU).getCarrera() != null){
	        	modelView.addObject("listadoMaterias",alumnoService.buscarAlumno(LU).getCarrera().getMaterias());
	            modelView.addObject("listadoCarreras",cs.MostrarCarrera());
	            modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
        	}
        	else {
        		modelView.addObject("listadoCarreras",cs.MostrarCarrera());
                modelView.addObject("alumno",alumnoService.buscarAlumno(LU));
        	}
        }
        return modelView;
    }
    
}
