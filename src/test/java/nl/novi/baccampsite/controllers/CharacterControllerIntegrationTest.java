package nl.novi.baccampsite.controllers;

import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.models.User;
import nl.novi.baccampsite.models.Character;
import nl.novi.baccampsite.repositories.CampaignRepository;
import nl.novi.baccampsite.repositories.CharacterRepository;
import nl.novi.baccampsite.repositories.ProfessionRepository;
import nl.novi.baccampsite.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("testuser")
class CharacterControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    ProfessionRepository professionRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @BeforeEach
    void setUp() {
        characterRepository.deleteAll();
        campaignRepository.deleteAll();
        userRepository.deleteAll();


        User owner = new User();
        owner.setUsername("Castiga");
        owner.setPassword("1234");
        owner.setEmail("castiga@cleric.nl");
        userRepository.save(owner);

        Character character = new Character();
        character.setName("Shasz'liq");
        character.setUser(owner);
        character.setLevel(5);
        character.setProfession(professionRepository.findById("monk").orElseThrow(() -> new RecordNotFoundException("No profession found")));
        characterRepository.save(character);
    }

    @Test
    @WithMockUser(username = "Castiga", roles = {"USER"})
    void shouldReturnCharacterWhenUserIsOwner() throws Exception {
        Character character = characterRepository.findAll().getFirst();

        mockMvc.perform(get("/characters/" + character.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Shasz'liq"))
                .andExpect(jsonPath("$.level").value(5));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnCharacterWhenUserIsAdmin() throws Exception {
        Character character = characterRepository.findAll().getFirst();

        mockMvc.perform(get("/characters/" + character.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Shasz'liq"))
                .andExpect(jsonPath("$.level").value(5));
    }

    @Test
    @WithMockUser(username = "Rando", roles = {"USER"})
    void shouldReturnForbiddenWhenUserIsNotOwnerOrDm() throws Exception {
        Character character = characterRepository.findAll().getFirst();
        mockMvc.perform(get("/characters/" + character.getId()))
                .andExpect(status().isForbidden());
    }

}