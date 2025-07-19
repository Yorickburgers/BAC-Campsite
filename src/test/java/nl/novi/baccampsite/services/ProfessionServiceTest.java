package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.ProfessionResponseDto;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.repositories.ProfessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProfessionServiceUnitTest {
    @InjectMocks
    private ProfessionService professionService;

    @Mock
    private ProfessionRepository professionRepository;

    @Test
    void shouldRetrieveAllProfessions() {
        Profession prof1 = new Profession();
        prof1.setId("bard");
        prof1.setName("Bard");
        prof1.setDescription("Whimsical and Manipulative");

        Profession prof2 = new Profession();
        prof2.setId("cleric");
        prof2.setName("Cleric");
        prof2.setDescription("Devout and Overpowered");

        List<Profession> mockProfessions = List.of(prof1, prof2);

        Mockito.when(professionRepository.findAll()).thenReturn(mockProfessions);

        List<ProfessionResponseDto> result = professionService.retrieveAllProfessions();

        assertEquals(2, result.size());
        assertEquals("Bard", result.get(0).name);
        assertEquals("cleric", result.get(1).id);
        assertEquals("Whimsical and Manipulative", result.get(0).description);
        assertEquals("Devout and Overpowered", result.get(1).description);
    }

    @Test
    void retrieveProfession() {
    }

    @Test
    void createProfession() {
    }

    @Test
    void updateProfession() {
    }

    @Test
    void deleteProfession() {
    }
}