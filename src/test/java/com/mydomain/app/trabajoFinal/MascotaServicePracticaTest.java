package com.mydomain.app.trabajoFinal;

import com.mydomain.app.exception.EdadNoValidaException;
import com.mydomain.app.exception.MascotaException;
import com.mydomain.app.exception.TipoDeMascotaNoValidaException;
import com.mydomain.app.model.Mascota;
import com.mydomain.app.model.Propietario;
import com.mydomain.app.model.TipoDeMascota;
import com.mydomain.app.repository.MascotaRepository;
import com.mydomain.app.service.ExternalService;
import com.mydomain.app.service.MascotaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MascotaServicePracticaTest {

    @InjectMocks
    MascotaService mascotaService;

    @Mock
    MascotaRepository mascotaRepository;

    @Mock
    ExternalService externalService;


    @Test
    @DisplayName("TEST 01 - Registrar mascota correctamente tipo dos")
    void testRegistrarMascotaCorrectamente() {

        Propietario propietario = new Propietario("Pamela", "Cochabamba", "70785123");
        Mascota mascota = new Mascota();
        mascota.setNombre("Duquesa");
        mascota.setEdad(5);
        mascota.setTipoDeMascota(TipoDeMascota.PERRO);
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);
        when(mascotaRepository.findById(any())).thenReturn(Optional.empty());
        when(mascotaRepository.guardar(any(Mascota.class))).thenReturn(mascota);

        Mascota registrada = mascotaService.registrarMascotaTipoDos(mascota);

        assertNotNull(registrada);
        assertNotNull(registrada.getPropietario());
        assertEquals("Duquesa", registrada.getNombre());
        assertDoesNotThrow(() -> registrada.getEdad());
        assertEquals(TipoDeMascota.PERRO, registrada.getTipoDeMascota());
    }

    @Test
    @DisplayName("TEST 02 - Registrar mascota edad no valida tipo dos")
    void testRegistrarMascotaEdadNoValida() {

        Propietario propietario = new Propietario("Pamela", "Cochabamba", "70785123");
        Mascota mascota = new Mascota();
        mascota.setNombre("Duquesa");
        mascota.setEdad(-2);
        mascota.setTipoDeMascota(TipoDeMascota.PERRO);
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);

        assertThrows(EdadNoValidaException.class, () -> mascotaService.registrarMascotaTipoDos(mascota));
    }

    @Test
    @DisplayName("TEST 03 - Registrar mascota con edad 0 tipo dos")
    void testRegistrarMascotaConEdadCero() {

        Propietario propietario = new Propietario("Pamela", "Cochabamba", "70785123");
        Mascota mascota = new Mascota();
        mascota.setNombre("Napoleon");
        mascota.setEdad(0);
        mascota.setTipoDeMascota(TipoDeMascota.PERRO);
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);

        Exception exception = assertThrows(EdadNoValidaException.class, () -> mascotaService.registrarMascotaTipoDos(mascota));

        assertEquals("Edad no válida", exception.getMessage());
    }

    @Test
    @DisplayName("TEST 04 - Registrar mascota sin tipo")
    void testRegistrarMascotaSinTipo() {

        Propietario propietario = new Propietario("Pamela", "Cochabamba", "70785123");
        Mascota mascota = new Mascota();
        mascota.setNombre("Beto");
        mascota.setEdad(2);
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);

        Exception exception = assertThrows(TipoDeMascotaNoValidaException.class, () -> mascotaService.registrarMascotaTipoDos(mascota));

        assertEquals("Se debe indicar el tipo de mascota", exception.getMessage());
    }

    @Test
    @DisplayName("TEST 05 - Registrar mascota sin tipo teniendo error generico")
    void testRegistrarMascotaSinTipoConErrorGenerico() {

        Propietario propietario = new Propietario("Pamela", "Cochabamba", "70785123");
        Mascota mascota = new Mascota();
        mascota.setNombre("Beto");
        mascota.setEdad(2);
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);

        Exception exception = assertThrows(MascotaException.class, () -> mascotaService.registrarMascotaTipoDos(mascota));

        assertEquals("Se debe indicar el tipo de mascota", exception.getMessage());
    }

    @Test
    @DisplayName("TEST 06 - Editar mascota correctamente")
    void testEditarMascotaCorrectamente() {

        Propietario propietario = new Propietario("Pamela", "Cochabamba", "70785123");
        Mascota mascota = new Mascota();
        mascota.setId(456);
        mascota.setNombre("Pino");
        mascota.setEdad(2);
        mascota.setTipoDeMascota(TipoDeMascota.GATO);
        mascota.setPropietario(propietario);

        when(mascotaRepository.findById(456)).thenReturn(Optional.of(mascota));
        when(mascotaRepository.guardar(any(Mascota.class))).thenReturn(mascota);

        Mascota actualizada = mascotaService.editarMascotaTipoDos(mascota);

        assertNotNull(actualizada);
        assertNotNull(actualizada.getPropietario());
        assertEquals("Pino", actualizada.getNombre());
        assertDoesNotThrow(() -> actualizada.getEdad());
        assertEquals(TipoDeMascota.GATO, actualizada.getTipoDeMascota());

    }

    @Test
    @DisplayName("TEST 07 - Editar mascota sin propietario")
    void testEditarMascotaSinPropietario() {

        Mascota mascota = new Mascota();
        mascota.setId(456);
        mascota.setNombre("Pino");
        mascota.setEdad(2);
        mascota.setTipoDeMascota(TipoDeMascota.GATO);

        when(mascotaRepository.findById(456)).thenReturn(Optional.of(mascota));

        assertThrows(IllegalArgumentException.class, () -> mascotaService.editarMascotaTipoDos(mascota));

    }

    @Test
    @DisplayName("TEST 08 - Editar mascota sin estar registrada previamente")
    void testEditarMascotaSinRegistroPrevio() {

        Mascota mascota = new Mascota();
        mascota.setId(456);
        mascota.setNombre("Pino");
        mascota.setEdad(2);
        mascota.setTipoDeMascota(TipoDeMascota.GATO);

        assertThrows(NoSuchElementException.class, () -> mascotaService.editarMascotaTipoDos(mascota));

    }

    @Test
    @DisplayName("TEST 09 - Validar si se debe esterilizar mascota")
    void testValidarEsterelizarMascota() {
        Propietario propietario = new Propietario("Pamela", "Cochabamba", "70785123");
        Mascota mascota = new Mascota();
        mascota.setId(88);
        mascota.setNombre("Puca");
        mascota.setEdad(2);
        mascota.setTipoDeMascota(TipoDeMascota.GATO);

        when(mascotaRepository.findById(88)).thenReturn(Optional.of(mascota));

        assertTrue(mascotaService.esterelizar(mascota));

    }

    @Test
    @DisplayName("TEST 10 - Validar que no es necesario esterilizar mascota")
    void testValidarEsterelizarMascotaNoNecesario() {
        Propietario propietario = new Propietario("Pamela", "Cochabamba", "70785123");
        Mascota mascota = new Mascota();
        mascota.setId(100);
        mascota.setNombre("Willy");
        mascota.setEdad(1);
        mascota.setTipoDeMascota(TipoDeMascota.PEZ);

        when(mascotaRepository.findById(100)).thenReturn(Optional.of(mascota));

        assertFalse(mascotaService.esterelizar(mascota));

    }

}
