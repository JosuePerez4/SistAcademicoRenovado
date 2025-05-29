package sistema.academico.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.academico.DTO.*;
import sistema.academico.entities.Espacio;
import sistema.academico.repository.EspacioRepository;

@Service
public class EspacioService {

    @Autowired
    private EspacioRepository espacioRepository;

    public EspacioResponseDTO crearEspacio(EspacioRequestDTO espacioRequestDTO) {
        // Convertir DTO a entidad
        Espacio espacio = new Espacio();
        espacio.setNombre(espacioRequestDTO.getNombre());
        espacio.setTipo(espacioRequestDTO.getTipo());
        espacio.setCapacidad(espacioRequestDTO.getCapacidad());
        espacio.setUbicacion(espacioRequestDTO.getUbicacion());
        espacio.setDisponible(espacioRequestDTO.getDisponible());

        // Guardar el espacio en la base de datos
        Espacio espacioGuardado = espacioRepository.save(espacio);

        // Convertir entidad a DTO para la respuesta
        EspacioResponseDTO espacioResponseDTO = new EspacioResponseDTO();
        espacioResponseDTO.setId(espacioGuardado.getId());
        espacioResponseDTO.setNombre(espacioGuardado.getNombre());
        espacioResponseDTO.setTipo(espacioGuardado.getTipo());
        espacioResponseDTO.setCapacidad(espacioGuardado.getCapacidad());
        espacioResponseDTO.setUbicacion(espacioGuardado.getUbicacion());
        espacioResponseDTO.setDisponible(espacioGuardado.getDisponible());

        return espacioResponseDTO;
    }

    public List<EspacioResponseDTO> obtenerEspaciosDisponibles() {
        List<Espacio> disponibles = espacioRepository.findByDisponibleTrue();
        return disponibles.stream().map(espacio -> {
            EspacioResponseDTO dto = new EspacioResponseDTO();
            dto.setId(espacio.getId());
            dto.setNombre(espacio.getNombre());
            dto.setTipo(espacio.getTipo());
            dto.setCapacidad(espacio.getCapacidad());
            dto.setUbicacion(espacio.getUbicacion());
            dto.setDisponible(espacio.getDisponible());
            return dto;
        }).collect(Collectors.toList());
    }

    public EspacioResponseDTO actualizarEspacio(Long id, EspacioRequestDTO dto) {
        Optional<Espacio> optionalEspacio = espacioRepository.findById(id);
        if (optionalEspacio.isEmpty()) {
            throw new RuntimeException("Espacio no encontrado con ID: " + id);
        }

        Espacio espacio = optionalEspacio.get();
        espacio.setNombre(dto.getNombre());
        espacio.setTipo(dto.getTipo());
        espacio.setCapacidad(dto.getCapacidad());
        espacio.setUbicacion(dto.getUbicacion());
        espacio.setDisponible(dto.getDisponible());

        Espacio actualizado = espacioRepository.save(espacio);

        EspacioResponseDTO response = new EspacioResponseDTO();
        response.setId(actualizado.getId());
        response.setNombre(actualizado.getNombre());
        response.setTipo(actualizado.getTipo());
        response.setCapacidad(actualizado.getCapacidad());
        response.setUbicacion(actualizado.getUbicacion());
        response.setDisponible(actualizado.getDisponible());

        return response;
    }

    public String eliminarEspacio(Long id) {
        Optional<Espacio> optionalEspacio = espacioRepository.findById(id);
        if (optionalEspacio.isPresent()) {
            espacioRepository.deleteById(id);
            return "Espacio eliminado con éxito";
        } else {
            return "Espacio no encontrado con ID: " + id;
        }
    }
}
