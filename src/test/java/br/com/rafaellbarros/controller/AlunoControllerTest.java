package br.com.rafaellbarros.controller;

import br.com.rafaellbarros.model.Aluno;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void deveCriarAluno() throws Exception {
        final Aluno aluno = new Aluno("João Silva", 25, 'M', "MAT-2024-001");
        mockMvc.perform(post("/api/alunos").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(aluno))).andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nome").value("João Silva"))
            .andExpect(jsonPath("$.idade").value(25))
            .andExpect(jsonPath("$.sexo").value("M")).andExpect(jsonPath("$.matricula").value("MAT-2024-001"));
    }

    @Test
    @Order(2)
    void deveListarAlunos() throws Exception {
        mockMvc.perform(get("/api/alunos")).andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].nome").value("João Silva"));
    }

    @Test
    @Order(3)
    void deveBuscarPorId() throws Exception {
        mockMvc.perform(get("/api/alunos/1")).andExpect(status().isOk())
            .andExpect(jsonPath("$.matricula").value("MAT-2024-001"));
    }

    @Test
    @Order(4)
    void deveBuscarPorMatricula() throws Exception {
        mockMvc.perform(get("/api/alunos/matricula/MAT-2024-001")).andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    @Order(5)
    void deveAtualizarAluno() throws Exception {
        final Aluno aluno = new Aluno("João Silva Atualizado", 26, 'M', "MAT-2024-001");
        mockMvc.perform(put("/api/alunos/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(aluno)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("João Silva Atualizado")).andExpect(jsonPath("$.idade").value(26));
    }

    @Test
    @Order(6)
    void deveDeletarAluno() throws Exception {
        mockMvc.perform(delete("/api/alunos/1")).andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundParaIdInexistente() throws Exception {
        mockMvc.perform(get("/api/alunos/999")).andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarNotFoundParaMatriculaInexistente() throws Exception {
        mockMvc.perform(get("/api/alunos/matricula/NAO-EXISTE")).andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarIdInexistente() throws Exception {
        final Aluno aluno = new Aluno("X", 1, 'M', "X");
        mockMvc.perform(put("/api/alunos/999").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(aluno))).andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarNotFoundAoDeletarIdInexistente() throws Exception {
        mockMvc.perform(delete("/api/alunos/999")).andExpect(status().isNotFound());
    }
}
