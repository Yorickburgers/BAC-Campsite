package nl.novi.baccampsite.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.baccampsite.dtos.CampaignResponseDto;
import nl.novi.baccampsite.models.User;
import nl.novi.baccampsite.repositories.CampaignRepository;
import nl.novi.baccampsite.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("testuser")
class CampaignControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        campaignRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("Castiga");
        user.setPassword("1234");
        user.setEmail("castiga@cleric.nl");
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "Castiga", roles = {"USER"})
    void shouldCreateCorrectCampaign() throws Exception {
        String requestJson = """
                {
                    "name": "Victory or Death",
                    "description": "An enduring war leads to bloody battlefields."
                }
                """;

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/campaigns")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
                        .with(user("Castiga").password("1234").roles("USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(header().string("Message", "Campaign Victory or Death has been created!"))
                .andReturn();

        CampaignResponseDto dto = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CampaignResponseDto.class
        );

        String createdId = dto.id.toString();

        assertThat(result.getResponse().getHeader("Location"), matchesPattern("^.*/campaigns/" + createdId));
    }
}