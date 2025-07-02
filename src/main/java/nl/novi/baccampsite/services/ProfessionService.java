package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.ProfessionRequestDto;
import nl.novi.baccampsite.dtos.ProfessionResponseDto;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.mappers.ProfessionMapper;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.repositories.ProfessionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessionService {
    private final ProfessionRepository professionRepository;

    public ProfessionService(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }

    public List<ProfessionResponseDto> retrieveAllProfessions() {
        List<ProfessionResponseDto> professions = new ArrayList<>();
        professionRepository.findAll().forEach(profession -> professions.add(ProfessionMapper.toProfessionResponseDto(profession)));
        return professions;
    }

    public ProfessionResponseDto retrieveProfession(Long id) {
        return ProfessionMapper.toProfessionResponseDto(professionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Profession " + id + " not found!")));
    }

    public ProfessionResponseDto createProfession(ProfessionRequestDto professionRequestDto) {
        Profession profession = ProfessionMapper.toProfession(professionRequestDto);
        return ProfessionMapper.toProfessionResponseDto(professionRepository.save(profession));
    }

    public ProfessionResponseDto updateProfession(Long id, ProfessionRequestDto professionRequestDto) {
        Profession currentProfession = professionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Profession " + id + " not found!"));
        ProfessionMapper.updateProfessionFromDto(professionRequestDto, currentProfession);
        return ProfessionMapper.toProfessionResponseDto(professionRepository.save(currentProfession));
    }

    public String deleteProfession(Long id) {
        Profession profession = professionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Profession " + id + " not found!"));
        professionRepository.delete(profession);
        return "Profession " + id + " has been deleted!";
    }
}
