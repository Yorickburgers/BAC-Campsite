package nl.novi.baccampsite.services;

import nl.novi.baccampsite.BacCampsiteApplication;
import nl.novi.baccampsite.dtos.ProfessionRequestDto;
import nl.novi.baccampsite.dtos.ProfessionResponseDto;
import nl.novi.baccampsite.exceptions.BadRequestException;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.mappers.ProfessionMapper;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.repositories.ProfessionRepository;
import nl.novi.baccampsite.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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
        prof1.setTraitOne("Inspiration");

        Profession prof2 = new Profession();
        prof2.setName("Cleric");
        prof2.setDescription("Devout and Overpowered");
        prof2.setTraitOne("Penance");
        prof2.setId(StringUtils.slugify(prof2.getName()));

        List<Profession> mockProfessions = List.of(prof1, prof2);

        Mockito.when(professionRepository.findAll()).thenReturn(mockProfessions);

        List<ProfessionResponseDto> result = professionService.retrieveAllProfessions();

        assertEquals(2, result.size());
        assertEquals("Bard", result.get(0).name);
        assertEquals("cleric", result.get(1).id);
        assertEquals("Whimsical and Manipulative", result.get(0).description);
        assertEquals("Devout and Overpowered", result.get(1).description);
        assertEquals("Inspiration", result.get(0).traitOne);
        assertEquals("Penance", result.get(1).traitOne);
    }

    @Test
    void shouldRetrieveProfession() {
        Profession prof1 = new Profession();
        prof1.setName("Blood Hunter");
        prof1.setDescription("Hunting for Blood");
        prof1.setTraitOne("Bloodlust");
        prof1.setId(StringUtils.slugify(prof1.getName()));

        Mockito.when(professionRepository.findById(prof1.getId())).thenReturn(Optional.of(prof1));

        ProfessionResponseDto result = professionService.retrieveProfession(prof1.getId());

        assertEquals(prof1.getName(), result.name);
        assertEquals(prof1.getId(), result.id);
        assertEquals(prof1.getDescription(), result.description);
        assertEquals(prof1.getTraitOne(), result.traitOne);

    }

    @Test
    void shouldCreateProfessionSuccessfully() {
        ProfessionRequestDto requestDto = new ProfessionRequestDto();
        requestDto.name = "Artificer";
        requestDto.description = "A crafty mechanic";
        requestDto.hpModifier = 8;
        requestDto.primaryAbility = "Intelligence";
        requestDto.traitOne = "Craft Trinket";
        requestDto.traitTwo = "Craft Turret";
        requestDto.traitThree = "Craft Armor";
        requestDto.traitFour = "Craft Weapon";

        Mockito.when(professionRepository.findById(StringUtils.slugify(requestDto.name))).thenReturn(Optional.empty());
//        Mockito.when(professionRepository.save(Mockito.any(Profession.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(professionRepository.save(Mockito.any(Profession.class)))
                .thenReturn(ProfessionMapper.toProfession(requestDto));
        ProfessionResponseDto result = professionService.createProfession(requestDto);

        assertEquals(requestDto.name, result.name);
        assertEquals(StringUtils.slugify(requestDto.name), result.id);
        assertEquals(requestDto.description, result.description);
        assertEquals(requestDto.hpModifier, result.hpModifier);
        assertEquals(requestDto.primaryAbility, result.primaryAbility);
        assertEquals(requestDto.traitOne, result.traitOne);
        assertEquals(requestDto.traitTwo, result.traitTwo);
        assertEquals(requestDto.traitThree, result.traitThree);
        assertEquals(requestDto.traitFour, result.traitFour);

    }

    @Test
    void shouldThrowWhenProfessionAlreadyExists() {
        ProfessionRequestDto requestDto = new ProfessionRequestDto();
        requestDto.name = "Warlock";

        Mockito.when(professionRepository.findById(StringUtils.slugify(requestDto.name))).thenReturn(Optional.of(new Profession()));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> professionService.createProfession(requestDto));

        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void shouldUpdateProfession() {
        String id = "wizard";

        Profession existingProfession = new Profession();
        existingProfession.setId(id);
        existingProfession.setName("Wizard");
        existingProfession.setDescription("Spellslinger Extraordinaire");

        ProfessionRequestDto updateDto = new ProfessionRequestDto();
        updateDto.name = "Wizard";
        updateDto.description = "Spellswinger Extra Ordinary";
        updateDto.traitOne = "Spellcasting";

        Mockito.when(professionRepository.findById(id)).thenReturn(Optional.of(existingProfession));
        Mockito.when(professionRepository.save(Mockito.any(Profession.class))).thenReturn(ProfessionMapper.toProfession(updateDto));

        ProfessionResponseDto result = professionService.updateProfession(id, updateDto);

        assertEquals("Wizard", result.name);
        assertEquals("Spellswinger Extra Ordinary", result.description);
        assertEquals("Spellcasting", result.traitOne);

    }

    @Test
    void shouldDeleteProfession() {
        String id = "monk";
        Profession prof = new Profession();
        prof.setId(id);
        prof.setName("Monk");

        Mockito.when(professionRepository.findById(id)).thenReturn(Optional.of(prof));

        String result = professionService.deleteProfession(id);

        assertEquals("Profession Monk with id monk has been deleted!", result);
        Mockito.verify(professionRepository).delete(prof);
    }

    @Test
    void shouldThrowWhenProfessionNotFound() {
        String id = "mnok";
        Mockito.when(professionRepository.findById(id)).thenReturn(Optional.empty());

        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class, () -> professionService.deleteProfession(id));

        assertEquals("Profession mnok not found!", ex.getMessage());
    }
}