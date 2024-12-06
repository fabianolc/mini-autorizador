package br.com.vrbeneficios.controller;

import static br.com.vrbeneficios.contants.PathConstants.TRANSACOES_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.vrbeneficios.domain.dto.CartaoDto;
import br.com.vrbeneficios.domain.request.MovimentoCartaoRequest;
import br.com.vrbeneficios.facade.CartaoFacade;
import br.com.vrbeneficios.repository.CartaoRepository;
import br.com.vrbeneficios.repository.MovimentoCartaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * The type Movimento cartao controller impl test.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class MovimentoCartaoControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartaoFacade cartaoFacade;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private MovimentoCartaoRepository movimentoCartaoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The My sql container.
     */
    static final MySQLContainer<?> MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest");
        MY_SQL_CONTAINER.start();
    }

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        cartaoFacade.cadastrarCartao(CartaoDto.builder()
                .numeroCartao("1234123412341234")
                .senha("1234")
                .saldo(new BigDecimal("500.00"))
                .build());
    }

    /**
     * After each.
     */
    @AfterEach
    public void afterEach() {
        movimentoCartaoRepository.deleteAll();
        cartaoRepository.deleteAll();
    }

    /**
     * Configure test properties.
     *
     * @param registry the registry
     */
    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.flyway.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.flyway.user", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.flyway.password", MY_SQL_CONTAINER::getPassword);
    }

    /**
     * Efetuar transacao teste sucesso.
     *
     * @throws Exception the exception
     */
    @Test
    void efetuarTransacaoTeste_Sucesso() throws Exception {
        mockMvc.perform(post("/" + TRANSACOES_PATH)
                        .with(httpBasic("username", "password"))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new MovimentoCartaoRequest("1234123412341234", "1234", new BigDecimal("100.00")))))
                .andExpect(status().isCreated());
    }

    /**
     * Efetuar transacao teste cartao inexistente.
     *
     * @throws Exception the exception
     */
    @Test
    void efetuarTransacaoTeste_CartaoInexistente() throws Exception {
        mockMvc.perform(post("/" + TRANSACOES_PATH)
                        .with(httpBasic("username", "password"))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new MovimentoCartaoRequest("1234987612349876", "1234", new BigDecimal("100.00")))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(TEXT_PLAIN + ";charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("CARTAO_INEXISTENTE"));
    }

    /**
     * Efetuar transacao teste senha incorreta.
     *
     * @throws Exception the exception
     */
    @Test
    void efetuarTransacaoTeste_SenhaIncorreta() throws Exception {
        mockMvc.perform(post("/" + TRANSACOES_PATH)
                        .with(httpBasic("username", "password"))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new MovimentoCartaoRequest("1234123412341234", "12346", new BigDecimal("100.00")))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(TEXT_PLAIN + ";charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("SENHA_INVALIDA"));
    }

    /**
     * Efetuar transacao teste saldo insuficiente.
     *
     * @throws Exception the exception
     */
    @Test
    void efetuarTransacaoTeste_SaldoInsuficiente() throws Exception {
        mockMvc.perform(post("/" + TRANSACOES_PATH)
                        .with(httpBasic("username", "password"))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new MovimentoCartaoRequest("1234123412341234", "1234", new BigDecimal("1000.00")))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(TEXT_PLAIN + ";charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("SALDO_INSUFICIENTE"));
    }
}
