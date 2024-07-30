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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MascotaService02HamcrestTest {

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
        Mascota registrada = mascotaService.registrarMascotaTipoUno(mascota);

        // Assert(Afirmar) : Hamcrest

        // Verificar las propiedades de la mascota registrada
        assertThat(registrada, is(notNullValue()));
        assertThat(registrada.getNombre(), is(equalTo("Garfield")));
        assertThat(registrada.getPropietario(), is(notNullValue()));
        assertThat(registrada.getPropietario().getNombre(), is(equalTo("Dany")));
        assertThat(registrada.getPropietario().getCiudad(), is(equalTo("Lima")));
        assertThat(registrada.getPropietario().getTelefono(), is(equalTo("987654321")));
        assertThat(registrada, is(sameInstance(mascota)));

        // Verificar las propiedades del propietario con Hamcrest
        assertThat(registrada.getPropietario(), hasProperty("nombre", is("Dany")));
        assertThat(registrada.getPropietario(), hasProperty("ciudad", is("Lima")));
        assertThat(registrada.getPropietario(), hasProperty("telefono", is("987654321")));

        // Comprobar con más matchers de Hamcrest
        assertThat(registrada.getId(), is(greaterThanOrEqualTo(0)));
    }

}
