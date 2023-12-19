package com.soa.canete.funcionary;

import com.soa.canete.funcionary.domain.model.Funcionary;
import com.soa.canete.funcionary.repository.FuncionaryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
@AutoConfigureWebTestClient
class T01BackEndSoaPrsFuncionaryApplicationTests {

    @Autowired
    FuncionaryRepository funcionaryRepository;
    @Autowired
    private WebTestClient webTestClient;
    @Test
    void testCreateFuncionary() {
        Funcionary newLegalGuardian = new Funcionary(
                "Boris",
                "Alcantara",
                "Gonzalez",
                "73829730",
                "997533646",
                "Legislador",
                "S",
                "Las Palmas",
                "rey@gmail.com",
                "010116",
                1,
                "A");
        webTestClient.post()
                .uri("/api/funcionaryData")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newLegalGuardian), Funcionary.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Funcionary.class)
                .value(Funcionary::getId_funcionary, notNullValue())
                .value(Funcionary::getName, equalTo("Boris"))
                .value(Funcionary::getSurnameFather, equalTo("Alcantara"))
                .value(Funcionary::getSurnameMother, equalTo("Gonzalez"))
                .value(Funcionary::getDni, equalTo("73829730"))
                .value(Funcionary::getPhoneNumber, equalTo("997533646"))
                .value(Funcionary::getRange, equalTo("Legislador"))
                .value(Funcionary::getConfirmation, equalTo("S"))
                .value(Funcionary::getAddress, equalTo("Las Palmas"))
                .value(Funcionary::getEmail, equalTo("rey@gmail.com"))
                .value(Funcionary::getCodubi, equalTo("010116"))
                .value(Funcionary::getIdOperativeUnit, equalTo(1))
                .value(Funcionary::getStatus, equalTo("A"));
    }
    @Test
    public void testListFuncionary() {
        webTestClient.get().uri("/api/funcionaryData/listData")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Funcionary.class)
                .consumeWith(response -> {
                    List<Funcionary> dataList = response.getResponseBody();
                    Assertions.assertTrue(dataList != null && !dataList.isEmpty(), "La lista no debe de estar vacía: ");
                    Assertions.assertFalse(false, "La lista debe contener al menos " + 1 + " elementos");
                    System.out.println(dataList);
                });
    }
    @Test
    public void testListInactiveLegalGuardian() {
        webTestClient.get().uri("/api/funcionaryData/listData/inactive")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Funcionary.class)
                .consumeWith(response -> {
                    List<Funcionary> dataList = response.getResponseBody();
                    Assertions.assertTrue(dataList != null && !dataList.isEmpty(), "La lista no debe de estar vacía: ");
                    Assertions.assertTrue(true, "La lista debe contener al menos " + 1 + " elementos");
                    System.out.println(dataList);
                });
    }
    @Test
    public void testListActiveLegalGuardian() {
        webTestClient.get().uri("/api/funcionaryData/listData/active")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Funcionary.class)
                .consumeWith(response -> {
                    List<Funcionary> dataList = response.getResponseBody();
                    Assertions.assertTrue(dataList != null && !dataList.isEmpty(), "La lista no debe de estar vacía: ");
                    Assertions.assertTrue(true, "La lista debe contener al menos " + 1 + " elementos");
                    System.out.println(dataList);
                });
    }
    @ParameterizedTest
    @ValueSource(ints = {436})
    public void testUpdateFuncionary(int dataId) {
        Funcionary UpdateFuncionary = new Funcionary(
                "Miguel Angel",
                "Lopez",
                "Gonzalez",
                "73829730",
                "997533646",
                "Legislador",
                "S",
                "Las Palmas",
                "rey@gmail.com",
                "010116",
                1,
                "A");
        webTestClient.put().uri("/api/funcionaryData/{id}", dataId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(UpdateFuncionary)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Funcionary.class)
                .consumeWith(response -> {
                });
    }

}
