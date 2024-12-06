package br.com.vrbeneficios.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.vrbeneficios.contants.PathConstants;
import br.com.vrbeneficios.domain.dto.CartaoDto;
import br.com.vrbeneficios.domain.request.CartaoRequest;
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
 * The type Cartao controller impl test.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class CartaoControllerImplTest {

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
     * Cadastrar cartao teste sucesso.
     *
     * @throws Exception the exception
     */
    @Test
    void cadastrarCartaoTeste_Sucesso() throws Exception {
        mockMvc.perform(post(PathConstants.CARTOES_PATH)
                        .with(httpBasic("username", "password"))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CartaoRequest("1234987612349876", "1234"))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroCartao").value("1234987612349876"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.senha").value("1234"));
    }

    /**
     * Cadastrar cartao teste ja existe.
     *
     * @throws Exception the exception
     */
    @Test
    void cadastrarCartaoTeste_JaExiste() throws Exception {
        mockMvc.perform(post(PathConstants.CARTOES_PATH)
                        .with(httpBasic("username", "password"))
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CartaoRequest("1234123412341234", "1234"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numeroCartao").value("1234123412341234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.senha").value("1234"));
    }

    /**
     * Obter saldo cartao teste sucesso.
     *
     * @throws Exception the exception
     */
    @Test
    void obterSaldoCartaoTeste_Sucesso() throws Exception {
        mockMvc.perform(get(PathConstants.CARTOES_NUMERO_PATH, "1234123412341234")
                        .with(httpBasic("username", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN + ";charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("500.00"));
    }

    /**
     * Obter saldo cartao teste not found.
     *
     * @throws Exception the exception
     */
    @Test
    void obterSaldoCartaoTeste_NotFound() throws Exception {
        mockMvc.perform(get(PathConstants.CARTOES_NUMERO_PATH, "1234123412346666")
                        .with(httpBasic("username", "password")))
                .andExpect(status().isNotFound());
    }
}
