package com.mydomain.app;

import com.mydomain.app.model.Mascota;
import com.mydomain.app.model.Propietario;
import com.mydomain.app.repository.MascotaRepository;
import com.mydomain.app.service.ExternalService;
import com.mydomain.app.service.MascotaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MascotaService06JUnitMockitoCaptorTest {

    @InjectMocks
    MascotaService mascotaService;

    @Mock
    MascotaRepository mascotaRepository;

    @Mock
    ExternalService externalService;

    @Captor
    ArgumentCaptor<Mascota> mascotaCaptor;

    @Test
    @DisplayName("Registrar mascota correctamente")
    void testRegistrarMascotaCorrectamente(){

        // Arrange(Preparar)
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);
        when(mascotaRepository.findById(any())).thenReturn(Optional.empty());
        when(mascotaRepository.guardar(any(Mascota.class))).thenReturn(mascota);


        // Act(Actuar)
        Mascota registrada = mascotaService.registrarMascotaTipoUno(mascota);

        // Verifica que el objeto no es null.
        assertNotNull(registrada, "La mascota registrada no debería ser null.");

        // Verifica que dos referencias apunten al mismo objeto, útil para confirmar que la instancia no ha sido clonada o modificada inesperadamente.
        assertSame(mascota, registrada, "La mascota registrada debería ser la misma que la ingresada.");

        // Captor
        verify(mascotaRepository).guardar(mascotaCaptor.capture());
        Mascota captureMascota = mascotaCaptor.capture();

        assertEquals("Garfield", captureMascota.getNombre());
        assertNotNull(captureMascota.getPropietario());
        assertEquals(propietario, captureMascota.getPropietario());
    }

}
