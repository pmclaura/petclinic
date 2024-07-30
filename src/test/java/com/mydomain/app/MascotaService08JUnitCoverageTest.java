package com.mydomain.app;

import com.mydomain.app.model.Mascota;
import com.mydomain.app.model.Propietario;
import com.mydomain.app.repository.MascotaRepository;
import com.mydomain.app.repository.MascotaRepositoryImpl;
import com.mydomain.app.service.ExternalService;
import com.mydomain.app.service.ExternalServiceImpl;
import com.mydomain.app.service.MascotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MascotaService08JUnitCoverageTest {

    MascotaService mascotaService;

    @BeforeEach
    void setUp(){
        // Arrange(Preparar) - Global
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        mascotaService = new MascotaService(mascotaRepository, externalService);
    }

    @Test
    @DisplayName("Registrar mascota correctamente")
//    @Disabled
    void testRegistrarMascotaCorrectamente(){
        // Arrange(Preparar) - Local
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        // Act(Actuar)
        Mascota registrada = mascotaService.registrarMascotaTipoUno(mascota);

        // Assert(Afirmar) : JUnit
        assertNotNull(registrada, "La mascota registrada no debería ser null.");
        assertEquals("Garfield", registrada.getNombre(), "El nombre de la mascota registrada debería ser 'Garfield'.");
    }

    @Test // Regla 01
    void testRegistrarMascotaConNombreNull(){
        // Arrange(Preparar) - Local
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setPropietario(propietario);
        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascotaTipoUno(mascota));
        // Assert(Afirmar)
        assertEquals("El nombre de la mascota no puede estar vacío.",exception.getMessage());
    }

    @Test // Regla 01
    void testRegistrarMascotaConNombreVacio(){
        // Arrange(Preparar) - Local
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("");
        mascota.setPropietario(propietario);
        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascotaTipoUno(mascota));
        // Assert(Afirmar)
        assertEquals("El nombre de la mascota no puede estar vacío.",exception.getMessage());
    }

    @Test // Regla 02
    void testRegistrarMascotaSinPropietario(){
        // Arrange(Preparar) - Local
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascotaTipoUno(mascota));
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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascotaTipoUno(mascota));
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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascotaTipoUno(mascota));
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
        // Act(Actuar)
        Exception exception = assertThrows(IllegalStateException.class, () -> mascotaService.registrarMascotaTipoUno(mascota));
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
        // Act(Actuar)
        Exception exception = assertThrows(IllegalStateException.class, () -> mascotaService.registrarMascotaTipoUno(mascota));
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
        // Act(Actuar)
        Exception exception = assertThrows(IllegalStateException.class, () -> mascotaService.registrarMascotaTipoUno(mascota));
        // Assert(Afirmar)
        assertEquals("Esta mascota ya está registrada.",exception.getMessage());
    }

    @Test
    void testBuscarMascotaPorId(){
        // Act(Actuar)
        Optional<Mascota> result = mascotaService.buscarMascotaPorId(1);
        // Assert(Afirmar)
        assertTrue(result.isPresent());
    }

    @Test
    void testEliminarMascotaExitosamente(){
        // Arrange(Preparar) - Local
        assertTrue(mascotaService.buscarMascotaPorId(1).isPresent());
        // Act(Actuar)
        mascotaService.eliminarMascotaPorId(1);
        // Assert(Afirmar)
        assertFalse(mascotaService.buscarMascotaPorId(1).isPresent());
    }

    @Test
    void testEliminarMascotaLanzaException(){
        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.eliminarMascotaPorId(666));
        // Assert(Afirmar)
        assertEquals("No se puede eliminar: No se encontró mascota con el ID proporcionado.",exception.getMessage());
    }


}
