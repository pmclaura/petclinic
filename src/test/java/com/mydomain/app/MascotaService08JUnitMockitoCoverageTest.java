package com.mydomain.app;

import com.mydomain.app.model.Mascota;
import com.mydomain.app.model.Propietario;
import com.mydomain.app.repository.MascotaRepository;
import com.mydomain.app.service.ExternalService;
import com.mydomain.app.service.MascotaService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MascotaService08JUnitMockitoCoverageTest {

    @InjectMocks
    MascotaService mascotaService;

    @Mock
    MascotaRepository mascotaRepository;

    @Mock
    ExternalService externalService;

    @Test
    @DisplayName("Registrar mascota correctamente")
//    @Disabled
    @Tag("one")
    void testRegistrarMascotaCorrectamente(){
        // Arrange(Preparar) - Local
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        // Mocks
        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);
        when(mascotaRepository.findById(any())).thenReturn(Optional.empty());
        when(mascotaRepository.guardar(any(Mascota.class))).thenReturn(mascota);

        // Act(Actuar)
        Mascota registrada = mascotaService.registrarMascota(mascota);

        // Assert(Afirmar) : JUnit
        assertNotNull(registrada, "La mascota registrada no debería ser null.");
        assertEquals("Garfield", registrada.getNombre(), "El nombre de la mascota registrada debería ser 'Garfield'.");
    }

//    @Test // Regla 01
//    @Tag("two")
//    void testRegistrarMascotaConNombreNull(){
//        // Arrange(Preparar) - Local
//        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
//        Mascota mascota = new Mascota();
//        mascota.setPropietario(propietario);
//        // Act(Actuar)
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
//        // Assert(Afirmar)
//        assertEquals("El nombre de la mascota no puede estar vacío.",exception.getMessage());
//    }
//
//    @Test // Regla 01
//    void testRegistrarMascotaConNombreVacio(){
//        // Arrange(Preparar) - Local
//        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
//        Mascota mascota = new Mascota();
//        mascota.setNombre("");
//        mascota.setPropietario(propietario);
//        // Act(Actuar)
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
//        // Assert(Afirmar)
//        assertEquals("El nombre de la mascota no puede estar vacío.",exception.getMessage());
//    }

    @ParameterizedTest
    @NullAndEmptySource
    void testRegistrarMascotaConNombreInvalido(String nombre){
        // Arrange(Preparar) - Local
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setPropietario(propietario);

        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mascotaService.registrarMascota(mascota);
        });

        // Assert(Afirmar)
        assertEquals("El nombre de la mascota no puede estar vacío.", exception.getMessage());
    }

    @Test // Regla 02
    void testRegistrarMascotaSinPropietario(){
        // Arrange(Preparar) - Local
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
        // Assert(Afirmar)
        assertEquals("La mascota debe tener un propietario.",exception.getMessage());
    }

    @Test // Regla 03
    void testRegistrarMascotaConPropiertarioTelefonoNull(){
        // Arrange(Preparar) - Local
        Propietario propietario = new Propietario("Dany", "Lima", null);
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);
        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
        // Assert(Afirmar)
        assertEquals("El propietario debe tener un teléfono registrado.",exception.getMessage());
    }

    @Test // Regla 03
    void testRegistrarMascotaConPropiertarioTelefonoVacio(){
        // Arrange(Preparar) - Local
        Propietario propietario = new Propietario("Dany", "Lima", "");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);
        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
        // Assert(Afirmar)
        assertEquals("El propietario debe tener un teléfono registrado.",exception.getMessage());
    }

    @Test // Regla 04
    void testRegistrarMascotaSinVacunas(){
        // Arrange(Preparar) - Local
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("sin vacuna");
        mascota.setPropietario(propietario);
        // Mocks
        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(false);
        // Act(Actuar)
        Exception exception = assertThrows(IllegalStateException.class, () -> mascotaService.registrarMascota(mascota));
        // Assert(Afirmar)
        assertEquals("La mascota no tiene todas las vacunas al día.",exception.getMessage());
    }

    @Test // Regla 05
    void testRegistrarMascotaSinRegistroMunicipal(){
        // Arrange(Preparar) - Local
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("no registrado");
        mascota.setPropietario(propietario);
        // Mocks
        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(false);
        // Act(Actuar)
        Exception exception = assertThrows(IllegalStateException.class, () -> mascotaService.registrarMascota(mascota));
        // Assert(Afirmar)
        assertEquals("La mascota no está registrada en el municipio.",exception.getMessage());
    }

    @Test // Regla 06
    void testRegistrarMascotaYaRegistrada(){
        // Arrange(Preparar) - Local
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setId(3);
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);
        // Mocks
        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);
        when(mascotaRepository.findById(any())).thenReturn(Optional.of(mascota));
        // Act(Actuar)
        Exception exception = assertThrows(IllegalStateException.class, () -> mascotaService.registrarMascota(mascota));
        // Assert(Afirmar)
        assertEquals("Esta mascota ya está registrada.",exception.getMessage());
    }

    @Test
    void testBuscarMascotaExitosamente() {
        // Arrange(Preparar)
        Mascota mascota = new Mascota();
        mascota.setId(4);
        mascota.setNombre("Milo");
        Propietario propietario = new Propietario("John", "Lima", "911111111");
        mascota.setPropietario(propietario);
        // Mocks
        when(mascotaRepository.findById(4)).thenReturn(Optional.of(mascota));

        // Act(Actuar)
        Optional<Mascota> result = mascotaService.buscarMascotaPorId(4);

        // Assert(Afirmar)
        assertTrue(result.isPresent(), "La mascota debería estar presente");
        assertEquals("Milo", result.get().getNombre(), "El nombre de la mascota debería ser Milo");
        assertEquals("John", result.get().getPropietario().getNombre());
        assertEquals("Lima", result.get().getPropietario().getCiudad());
        assertEquals("911111111", result.get().getPropietario().getTelefono());
    }

    @Test
    void testEliminarMascotaExitosamente() {
        // Arrange(Preparar)
        Mascota mascota = new Mascota();
        when(mascotaRepository.findById(1)).thenReturn(Optional.of(mascota));

        // Act(Actuar)
        mascotaService.eliminarMascotaPorId(1);
        // Mock: Cambiar el comportamiento para simular que la mascota ha sido eliminada
        when(mascotaRepository.findById(1)).thenReturn(Optional.empty());
        Optional<Mascota> result = mascotaService.buscarMascotaPorId(1);

        // Assert(Afirmar)
        assertFalse(result.isPresent(), "La mascota debería haber sido eliminada");
    }

    @Test
    void testEliminarMascotaLanzaExcepcionCuandoNoExiste() {
        // Arrange(Preparar)
        int mascotaId = 666;
        // Mock: Configurar el repositorio para que no encuentre la mascota
        when(mascotaRepository.findById(mascotaId)).thenReturn(Optional.empty());
        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.eliminarMascotaPorId(mascotaId));
        // Assert(Afirmar)
        assertEquals("No se puede eliminar: No se encontró mascota con el ID proporcionado.", exception.getMessage());
    }


}
