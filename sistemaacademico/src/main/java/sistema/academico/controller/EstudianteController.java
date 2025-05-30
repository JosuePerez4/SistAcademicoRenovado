package sistema.academico.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sistema.academico.DTO.EstudianteRequestDTO;
import sistema.academico.DTO.EstudianteResponseDTO;
import sistema.academico.DTO.EstudianteUpdateDTO;
import sistema.academico.entities.Estudiante;
import sistema.academico.entities.ProgramaAcademico;
import sistema.academico.entities.Rol;
import sistema.academico.repository.ProgramaAcademicoRepository;
import sistema.academico.repository.RolRepository;
import sistema.academico.services.EstudianteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/estudiante")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ESTUDIANTE') or hasRole('ADMINISTRADOR')") // Aseguramos que solo estudiantes y admins accedan
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private ProgramaAcademicoRepository programaAcademicoRepository;

    @Autowired
    private RolRepository rolRepository;

    // Crear un estudiante
    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMINISTRADOR')") // Solo los administradores pueden registrar estudiantes
    public ResponseEntity<Estudiante> registrarEstudiante(@RequestBody EstudianteRequestDTO dto) {
        ProgramaAcademico programa = programaAcademicoRepository
                .findById(dto.getProgramaAcademicoId())
                .orElseThrow(() -> new RuntimeException("Programa académico no encontrado"));

        Estudiante estudiante = new Estudiante();
        estudiante.setCedula(dto.getCedula());
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setDireccion(dto.getDireccion());
        estudiante.setCorreo(dto.getCorreo());
        estudiante.setTelefono(dto.getTelefono());
        estudiante.setGenero(dto.getGenero());
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());
        estudiante.setCodigo(dto.getCodigo());
        estudiante.setContrasena(dto.getContrasena());
        estudiante.setEstado(dto.isEstado());

        // Buscar el rol existente
        Rol rolEstudiante = rolRepository.findByNombre("ESTUDIANTE")
                .orElseThrow(() -> new RuntimeException("Rol 'ESTUDIANTE' no encontrado"));
        estudiante.agregarRol(rolEstudiante);
        Set<Rol> roles = new HashSet<>();
        roles.add(rolEstudiante);
        estudiante.agregarRol(rolEstudiante);
        estudiante.setRoles(roles);
        estudiante.setPromedio(dto.getPromedio());
        estudiante.setBeca(dto.isBeca());
        estudiante.setFechaIngreso(LocalDate.now());
        estudiante.setFechaEgreso(dto.getFechaEgreso());
        estudiante.setProgramaAcademico(programa);

        Estudiante guardado = estudianteService.registrarEstudiante(estudiante);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    @PutMapping("actualizar/{codigo}")
    public ResponseEntity<?> actualizarEstudiante(@PathVariable String codigo,
            @RequestBody EstudianteUpdateDTO estudianteActualizado) {

        Estudiante estudianteEncontrao = estudianteService.buscarPorCodigo(codigo);

        if (estudianteEncontrao != null) {
            // Guardamos la contraseña actual
            String contrasenaActual = estudianteEncontrao.getContrasena();
            
            // Actualizamos los demás campos
            estudianteEncontrao.setNombre(estudianteActualizado.getNombre());
            estudianteEncontrao.setApellido(estudianteActualizado.getApellido());
            estudianteEncontrao.setDireccion(estudianteActualizado.getDireccion());
            estudianteEncontrao.setCorreo(estudianteActualizado.getCorreo());
            estudianteEncontrao.setTelefono(estudianteActualizado.getTelefono());
            estudianteEncontrao.setEstado(estudianteActualizado.isEstado());
            estudianteEncontrao.setBeca(estudianteActualizado.isBeca());
            estudianteEncontrao.setFechaEgreso(estudianteActualizado.getFechaEgreso());
            
            // Restauramos la contraseña original
            estudianteEncontrao.setContrasena(contrasenaActual);

            Estudiante actualizado = estudianteService.actualizarEstudiante(estudianteEncontrao);

            return ResponseEntity.ok(actualizado);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El estudiante con el código " + codigo + " no fue encontrado y no se pudo actualizar.");
    }

    // Eliminar un estudiante
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')") // Solo los administradores pueden eliminar estudiantes
    public ResponseEntity<String> eliminarEstudiante(@PathVariable("id") long idEstudiante) {

        boolean eliminado = estudianteService.eliminarEstudiante(idEstudiante);
        if (eliminado) {
            return ResponseEntity.ok("Estudiante con código " + idEstudiante + " eliminado correctamente");
        }
        return ResponseEntity.status(404).body("Estudiante con código " + idEstudiante + " no encontrado");
    }

    // Obtener un estudiante por id
    @GetMapping("/obtener/{id}")
    public ResponseEntity<EstudianteResponseDTO> obtenerEstudiante(@PathVariable long id) {
        Optional<Estudiante> estudianteOp = estudianteService.obtenerEstudiantePorId(id);

        if (estudianteOp.isPresent()) {
            Estudiante estudiante = estudianteOp.get();
            EstudianteResponseDTO dto = new EstudianteResponseDTO();
            dto.setId(estudiante.getId());
            dto.setCedula(estudiante.getCedula());
            dto.setNombre(estudiante.getNombre());
            dto.setApellido(estudiante.getApellido());
            dto.setDireccion(estudiante.getDireccion());
            dto.setCorreo(estudiante.getCorreo());
            dto.setTelefono(estudiante.getTelefono());
            dto.setGenero(estudiante.getGenero());
            dto.setFechaNacimiento(estudiante.getFechaNacimiento().toString());
            dto.setCodigo(estudiante.getCodigo());
            dto.setEstado(estudiante.isEstado());
            // dto.setRol(estudiante.getRol()); // Eliminar esta línea
            dto.setPromedio(estudiante.getPromedio());
            dto.setBeca(estudiante.isBeca());
            dto.setFechaIngreso(estudiante.getFechaIngreso().toString());
            dto.setFechaEgreso(estudiante.getFechaEgreso() != null ? estudiante.getFechaEgreso().toString() : null);

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/obtenerTodos")
    public ResponseEntity<List<EstudianteResponseDTO>> obtenerEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();

        List<EstudianteResponseDTO> respuesta = new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {
            EstudianteResponseDTO dto = new EstudianteResponseDTO();
            dto.setId(estudiante.getId());
            dto.setCedula(estudiante.getCedula());
            dto.setNombre(estudiante.getNombre());
            dto.setApellido(estudiante.getApellido());
            dto.setDireccion(estudiante.getDireccion());
            dto.setCorreo(estudiante.getCorreo());
            dto.setTelefono(estudiante.getTelefono());
            dto.setGenero(estudiante.getGenero());
            dto.setFechaNacimiento(estudiante.getFechaNacimiento().toString());
            dto.setCodigo(estudiante.getCodigo());
            dto.setEstado(estudiante.isEstado());
            // dto.setRol(estudiante.getRol()); // Eliminar esta línea
            dto.setPromedio(estudiante.getPromedio());
            dto.setBeca(estudiante.isBeca());
            dto.setFechaIngreso(estudiante.getFechaIngreso().toString());
            dto.setFechaEgreso(estudiante.getFechaEgreso() != null ? estudiante.getFechaEgreso().toString() : null);

            respuesta.add(dto);
        }

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<EstudianteResponseDTO> buscarPorCodigo(@PathVariable String codigo) {
        Estudiante estudiante = estudianteService.buscarPorCodigo(codigo);
        
        if (estudiante != null) {
            EstudianteResponseDTO dto = new EstudianteResponseDTO();
            dto.setId(estudiante.getId());
            dto.setCedula(estudiante.getCedula());
            dto.setNombre(estudiante.getNombre());
            dto.setApellido(estudiante.getApellido());
            dto.setDireccion(estudiante.getDireccion());
            dto.setCorreo(estudiante.getCorreo());
            dto.setTelefono(estudiante.getTelefono());
            dto.setGenero(estudiante.getGenero());
            dto.setFechaNacimiento(estudiante.getFechaNacimiento().toString());
            dto.setCodigo(estudiante.getCodigo());
            dto.setEstado(estudiante.isEstado());
            dto.setPromedio(estudiante.getPromedio());
            dto.setBeca(estudiante.isBeca());
            dto.setFechaIngreso(estudiante.getFechaIngreso().toString());
            dto.setFechaEgreso(estudiante.getFechaEgreso() != null ? estudiante.getFechaEgreso().toString() : null);

            return ResponseEntity.ok(dto);
        }
        
        return ResponseEntity.notFound().build();
    }

}