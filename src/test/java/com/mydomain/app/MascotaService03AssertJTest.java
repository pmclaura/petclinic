package com.mydomain.app;

import com.mydomain.app.model.Mascota;
import com.mydomain.app.model.Propietario;
import com.mydomain.app.repository.MascotaRepository;
import com.mydomain.app.repository.MascotaRepositoryImpl;
import com.mydomain.app.service.ExternalService;
import com.mydomain.app.service.ExternalServiceImpl;
import com.mydomain.app.service.MascotaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class MascotaService03AssertJTest {

    MascotaService mascotaService;

    @Test
    @DisplayName("Registrar mascota correctamente")
    void testRegistrarMascotaCorrectamente(){

        // Arrange(Preparar)
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        mascotaService = new MascotaService(mascotaRepository, externalService);

        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        // Act(Actuar)
        Mascota registrada = mascotaService.registrarMascota(mascota);

        // Assert(Afirmar) : AssertJ

        // Verificar las propiedades de la mascota registrada
        assertThat(registrada).isNotNull();
        assertThat(registrada.getNombre()).isEqualTo("Garfield");
        assertThat(registrada.getPropietario()).isNotNull();
        assertThat(registrada.getPropietario().getNombre()).isEqualTo("Dany");
        assertThat(registrada.getPropietario().getCiudad()).isEqualTo("Lima");
        assertThat(registrada.getPropietario().getTelefono()).isEqualTo("987654321");
        assertThat(registrada).isSameAs(mascota);

        assertThat(registrada.getId()).isPositive();
    }

}
