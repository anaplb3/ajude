package service;

import com.dsc.ajude.modelos.Campanha;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

class CampanhaServicoTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void getCampanhaPorSubstring() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Campanha>> typeReference = new TypeReference<List<Campanha>>() {
        };
        InputStream inputStream = ObjectMapper.class.getResourceAsStream("/json/disciplinas.json");
        try {
            List<Campanha> disciplinas = objectMapper.readValue(inputStream, typeReference);
            //disciplinaRepository.saveAll(disciplinas);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}