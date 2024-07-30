package com.mydomain.app;

import com.mydomain.app.model.Mascota;
import com.mydomain.app.model.Propietario;
import com.mydomain.app.repository.MascotaRepository;
import com.mydomain.app.service.ExternalService;
import com.mydomain.app.service.MascotaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MascotaService05JUnitMockito1Test {

    MascotaService mascotaService;

    @Test
    @DisplayName("Registrar mascota correctamente")
    void testRegistrarMascotaCorrectamente(){

        // Arrange(Preparar)
//        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
//        ExternalService externalService = new ExternalServiceImpl();

        // Inicializar mocks
        MascotaRepository mascotaRepository = mock(MascotaRepository.class);
        ExternalService externalService = mock(ExternalService.class);

        // Inyección de dependencias manual
        mascotaService = new MascotaService(mascotaRepository, externalService);

        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);
        when(mascotaRepository.findById(any(Integer.class))).thenReturn(Optional.of(mascota));
        when(mascotaRepository.guardar(any(Mascota.class))).thenReturn(mascota);


        // Act(Actuar)
        Mascota registrada = mascotaService.registrarMascotaTipoUno(mascota);

        // Assert(Afirmar) : JUnit

        // Verifica que el objeto no es null.
        assertNotNull(registrada, "La mascota registrada no debería ser null.");

        // Verifica que dos referencias apunten al mismo objeto, útil para confirmar que la instancia no ha sido clonada o modificada inesperadamente.
        assertSame(mascota, registrada, "La mascota registrada debería ser la misma que la ingresada.");

    }

}
