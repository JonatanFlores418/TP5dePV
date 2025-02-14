package ar.edu.unju.fi.map;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ar.edu.unju.fi.DTO.AlumnoDTO;
import ar.edu.unju.fi.model.Alumno;

@Mapper(componentModel = "spring")
public interface AlumnoMapDTO {


	@Mapping(target = "LU", source = "LU")
    @Mapping(target = "dni", source = "dni")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "apellido", source = "apellido")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "telefono", source = "telefono")
    @Mapping(target = "domicilio", source = "domicilio")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "fechaNacimiento", source = "fechaNacimiento")
    AlumnoDTO convertirAlumnoAAlumnoDTO(Alumno a);
    
    @Mapping(target = "carrera", ignore = true)
	@InheritInverseConfiguration(name = "convertirAlumnoAAlumnoDTO")
    Alumno convertirAlumnoDTOAAlumno(AlumnoDTO aDTO);

    List<AlumnoDTO> convertirListaAlumnoAListaAlumnoDTO(List<Alumno> listaA);
    List<Alumno> convertirListaAlumnoDTOAListaAlumno(List<AlumnoDTO> listaADTO);
}


