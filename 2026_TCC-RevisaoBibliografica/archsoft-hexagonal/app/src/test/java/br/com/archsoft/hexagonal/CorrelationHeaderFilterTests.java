package br.com.archsoft.hexagonal;

import br.com.archsoft.common.observability.CorrelationHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CorrelationHeaderFilterTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    void healthGeneratesRequestIdWhenHeaderIsMissing() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(header().string(CorrelationHeaders.REQUEST_ID, not(blankOrNullString())))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.arch").value("hexagonal"));
    }

    @Test
    void healthEchoesCorrelationHeaders() throws Exception {
        mockMvc.perform(get("/health")
                        .header(CorrelationHeaders.REQUEST_ID, "11111111-1111-1111-1111-111111111111")
                        .header(CorrelationHeaders.RUN_ID, "22222222-2222-2222-2222-222222222222")
                        .header(CorrelationHeaders.CHANGE_ID, "change-01")
                        .header(CorrelationHeaders.ARCHITECTURE, "hexagonal")
                        .header(CorrelationHeaders.CONSTRUCTOR_COMMIT, "abc1234"))
                .andExpect(status().isOk())
                .andExpect(header().string(CorrelationHeaders.REQUEST_ID, "11111111-1111-1111-1111-111111111111"))
                .andExpect(header().string(CorrelationHeaders.RUN_ID, "22222222-2222-2222-2222-222222222222"))
                .andExpect(header().string(CorrelationHeaders.CHANGE_ID, "change-01"));
    }
}
