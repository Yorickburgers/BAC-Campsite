package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.CharacterRequestDto;
import nl.novi.baccampsite.dtos.CharacterResponseDto;
import nl.novi.baccampsite.dtos.CharacterSummaryDto;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.mappers.CharacterMapper;
import nl.novi.baccampsite.models.Character;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.models.Specialization;
import nl.novi.baccampsite.models.User;
import nl.novi.baccampsite.repositories.CharacterRepository;
import nl.novi.baccampsite.repositories.ProfessionRepository;
import nl.novi.baccampsite.repositories.SpecializationRepository;
import nl.novi.baccampsite.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CharacterServiceUnitTest {
    @InjectMocks
    private CharacterService characterService;

    @Mock
    private ProfessionRepository professionRepository;
    @Mock
    private SpecializationRepository specializationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CharacterRepository characterRepository;

    private Character char1;
    private Character char2;
    private List<Character> characters = new ArrayList<>();
    private Profession prof1;
    private Profession prof2;
    private Specialization spec1;
    private Specialization spec2;
    private User user1;
    private User user2;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        prof1 = new Profession();
        prof1.setName("Mountain Thane");
        prof1.setId("mountain-thane");

        prof2 = new Profession();
        prof2.setName("Fallen");
        prof2.setId("fallen");

        spec1 = new Specialization();
        spec1.setProfession(prof1);
        spec1.setName("Thunder Lord");
        spec1.setId("mountain-thane-thunder-lord");

        spec2 = new Specialization();
        spec2.setProfession(prof2);
        spec2.setName("Wyrdweaver");
        spec2.setId("fallen-wyrdweaver");

        user1 = new User();
        user1.setUsername("Brandon");

        user2 = new User();
        user2.setUsername("Linda");

        char1 = new Character();
        char1.setName("Wendy");
        char1.setSpecies("Dwarf");
        char1.setLevel(5);
        char1.setProfession(prof1);
        char1.setSpecialization(spec1);
        char1.setUser(user1);

        char2 = new Character();
        char2.setName("Olga");
        char2.setSpecies("Goliath");
        char2.setLevel(12);
        char2.setProfession(prof2);
        char2.setSpecialization(spec2);
        char2.setUser(user2);

        characters.add(char1);
        characters.add(char2);
    }

    @Test
    void shouldRetrieveAllCharacters() {
        Mockito.when(characterRepository.findAll()).thenReturn(characters);

        List<CharacterSummaryDto> result = characterService.retrieveAllCharacters();

        assertEquals(2, result.size());
        assertEquals(5, result.get(0).level);
        assertEquals("Goliath", result.get(1).species);
        assertTrue(result.get(0).path.contains("/characters/"));
        assertEquals("mountain-thane", result.get(0).profession.id);
        assertEquals("fallen", result.get(1).profession.id);

    }

    @Test
    void shouldRetrieveCharacter() {
        Mockito.when(characterRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(char1));

        CharacterResponseDto result = characterService.retrieveCharacter(1L);

        assertEquals("Brandon", result.user);
        assertEquals("Dwarf", result.species);
        assertEquals(5, result.level);
        assertEquals("mountain-thane", result.profession.id);
        assertEquals("mountain-thane-thunder-lord", result.specialization.id);

    }

    @Test
    void shouldCreateCharacter() {
        CharacterRequestDto dto = new CharacterRequestDto();
        dto.name = "Susan";
        dto.backstory = "Always smiling, but it feels empty... and why are her eyes all black?";
        dto.professionId = "fallen";

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername()).thenReturn("Brandon");
        Mockito.when(userRepository.findById(userDetails.getUsername())).thenReturn(Optional.of(user1));
        Mockito.when(professionRepository.findById("fallen")).thenReturn(Optional.of(prof2));

        Character mockChar1 = CharacterMapper.toCharacter(dto, prof2);
        mockChar1.setUser(user1);
        Mockito.when(characterRepository.save(Mockito.any(Character.class)))
                .thenReturn(mockChar1);
        CharacterResponseDto result = characterService.createCharacter(dto, userDetails);

                assertEquals("Susan", result.name);
                assertTrue(result.backstory.contains("smiling"));
                assertEquals("Fallen", result.profession.name);
                assertEquals("Brandon", result.user);
    }

    @Test
    void shouldUpdateCharacter() {
        CharacterRequestDto dto = new CharacterRequestDto();
        dto.name = "Susan updated";
        dto.backstory = "A shadow behind her smile";
        dto.professionId = "fallen";

        Character existChar = new Character();
        existChar.setId(1L);
        existChar.setName("Susan");
        existChar.setSpecies("A smile behind her shadow");
        existChar.setProfession(prof1);
        existChar.setUser(user1);

        Mockito.when(characterRepository.findById(1L)).thenReturn(Optional.of(existChar));
        Mockito.when(professionRepository.findById("fallen")).thenReturn(Optional.of(prof2));

        Character newChar = CharacterMapper.toCharacter(dto, prof2);
        newChar.setUser(user1);
        Mockito.when(characterRepository.save(Mockito.any(Character.class)))
                .thenReturn(newChar);

        CharacterResponseDto result = characterService.updateCharacter(1L, dto);

        assertEquals("Susan updated", result.name);
        assertEquals("A shadow behind her smile", result.backstory);
        assertEquals("Fallen", result.profession.name);
        assertEquals("Brandon", result.user);


    }

    @Test
    void shouldUnclaimCharacter() {
        char1.setId(1L);
        char1.setUser(user1);

        Mockito.when(characterRepository.findById(1L)).thenReturn(Optional.of(char1));
        Mockito.when(characterRepository.save(char1)).thenReturn(char1);

        String result = characterService.unclaimCharacter(1L);

        assertEquals("Character Wendy has been unclaimed!", result);
        assertNull(char1.getUser());
    }

    @Test
    void shouldThrowIfCharacterNotFound() {
        Mockito.when(characterRepository.findById(99L)).thenReturn(Optional.empty());

        RecordNotFoundException thrown = assertThrows(
                RecordNotFoundException.class,
                () -> characterService.unclaimCharacter(99L)
        );

        assertTrue(thrown.getMessage().contains("Character 99 not found"));
    }

    @Test
    void shouldClaimCharacter() {

    }

    @Test
    void shouldSpecializeCharacter() {
    }

    @Test
    void shouldDeleteCharacter() {
    }
}