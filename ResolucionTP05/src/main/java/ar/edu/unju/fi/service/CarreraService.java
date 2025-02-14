package ar.edu.unju.fi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.DTO.CarreraDTO;

@Service
public interface CarreraService {

		public void guardarCarrera(CarreraDTO carreraDTO);
		public List<CarreraDTO> MostrarCarrera();
		public List<CarreraDTO> MostrarCarreraInactivas();
		public void borrarCarrera(String codigo);
		public void borrarDefinitivoCarrera(String codigo);
		public void darDeAltaCarrera(String codigo);
		public void modificarCarrera(CarreraDTO carreraDTO);
		public CarreraDTO buscaCarrera(String codigo);
		public void darDeBajaAlumnoDeCarrera(String codigoCarrera, String LU);
		public void darDeBajaMateriaDeCarrera(String codigoCarrera, String codigoMateria);
		
}