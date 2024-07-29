package com.mydomain.app;

import com.mydomain.app.model.Mascota;
import com.mydomain.app.model.Propietario;
import com.mydomain.app.repository.MascotaRepository;
import com.mydomain.app.repository.MascotaRepositoryImpl;
import com.mydomain.app.service.ExternalService;
import com.mydomain.app.service.ExternalServiceImpl;
import com.mydomain.app.service.MascotaService;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MascotaService04JUnitFixtureTest {

    MascotaService mascotaService;

    @BeforeEach
    void beforeEach(){
        System.out.println("beforeEach");
        // Arrange(Preparar) - Configuración global
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        mascotaService = new MascotaService(mascotaRepository, externalService);
    }

    @AfterEach
    void afterEach(){
        System.out.println("afterEach");
    }

    @BeforeAll
    static void beforeAll(){
        System.out.println("beforeAll");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("afterAll");
    }

//    @Test
//    @DisplayName("Registrar mascota correctamente")
//    void testRegistrarMascotaCorrectamente(){
//
//        // Arrange(Preparar) - Configuración local
//        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
//        Mascota mascota = new Mascota();
//        mascota.setNombre("Garfield");
//        mascota.setPropietario(propietario);
//
//        // Act(Actuar)
//        Mascota registrada = mascotaService.registrarMascota(mascota);
//
//        // Assert(Afirmar) : JUnit
//
//        // Verifica que el objeto no es null.
//        assertNotNull(registrada, "La mascota registrada no debería ser null.");
//    }

    @Test
    @DisplayName("Registrar mascota con nombre Null")
    void testRegistrarMascotaConNombreNullLanzaExcepcion(){

        // Arrange(Preparar) - Configuración local
        Mascota mascota = new Mascota();

        // Assert(Afirmar) : JUnit

        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
        assertEquals("El nombre de la mascota no puede estar vacío.", exception.getMessage());
    }

    @Test
    @DisplayName("Registrar mascota con nombre Vacio")
    void testRegistrarMascotaConNombreVacio(){

        // Arrange(Preparar) - Configuración local
        Mascota mascota = new Mascota();
        mascota.setNombre("");

        // Assert(Afirmar) : JUnit

        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));
        assertEquals("El nombre de la mascota no puede estar vacío.", exception.getMessage());
    }

//    @Test
//    @DisplayName("Buscar mascota correctamente")
//    void testBuscarMascotaExitosamente(){
//
//        // Act(Actuar)
//        Optional<Mascota> result = mascotaService.buscarMascotaPorId(1);
//        assertTrue(result.isPresent(), "La mascota debería estár presente");
//        assertEquals("Milo", result.get().getNombre(), "El nombre de la mascota debería ser Milo");
//        assertEquals("John", result.get().getPropietario().getNombre());
//        assertEquals("Lima", result.get().getPropietario().getCiudad());
//        assertEquals("911111111", result.get().getPropietario().getTelefono());
//
//    }

}
