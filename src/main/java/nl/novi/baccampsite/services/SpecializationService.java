package nl.novi.baccampsite.services;

import nl.novi.baccampsite.dtos.SpecializationRequestDto;
import nl.novi.baccampsite.dtos.SpecializationResponseDto;
import nl.novi.baccampsite.dtos.SpecializationSummaryDto;
import nl.novi.baccampsite.exceptions.RecordNotFoundException;
import nl.novi.baccampsite.mappers.SpecializationMapper;
import nl.novi.baccampsite.models.Profession;
import nl.novi.baccampsite.models.Specialization;
import nl.novi.baccampsite.repositories.ProfessionRepository;
import nl.novi.baccampsite.repositories.SpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecializationService {
    private final SpecializationRepository specializationRepository;
    private final ProfessionRepository professionRepository;

    public SpecializationService (SpecializationRepository specializationRepository, ProfessionRepository professionRepository) {
        this.specializationRepository = specializationRepository;
        this.professionRepository = professionRepository;
    }

    public List<SpecializationSummaryDto> retrieveSpecializationsByProfession(Long id) {
        List<SpecializationSummaryDto> specializations = new ArrayList<>();
        specializationRepository.findAllByProfessionId(id).forEach(specialization -> specializations.add(SpecializationMapper.toSpecializationSummaryDto(specialization)));
        return specializations;
    }

    public List<SpecializationResponseDto> retrieveAllSpecializations() {
        List<SpecializationResponseDto> specializations = new ArrayList<>();
        specializationRepository.findAll().forEach(specialization -> specializations.add(SpecializationMapper.toSpecializationResponseDto(specialization)));
        return specializations;
    }

    public SpecializationResponseDto retrieveSpecialization(Long id) {
        return SpecializationMapper.toSpecializationResponseDto(specializationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Specialization " + id + " not found!")));
    }

    public SpecializationResponseDto createSpecialization(SpecializationRequestDto specializationRequestDto) {
        Profession profession = professionRepository.findById(specializationRequestDto.professionId)
                .orElseThrow(() -> new RecordNotFoundException("Profession " + specializationRequestDto.professionId + " not found!"));
        Specialization specialization = SpecializationMapper.toSpecialization(specializationRequestDto, profession);
        return SpecializationMapper.toSpecializationResponseDto(specializationRepository.save(specialization));
    }

    public SpecializationResponseDto updateSpecialization(Long id, SpecializationRequestDto specializationRequestDto) {
        Specialization currentSpec = specializationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Specialization " + id + " not found!"));
        Profession profession = professionRepository.findById(specializationRequestDto.professionId)
                .orElseThrow(() -> new RecordNotFoundException("Profession " + id + " not found!"));
        SpecializationMapper.updateSpecializationFromDto(specializationRequestDto, currentSpec, profession);
        return SpecializationMapper.toSpecializationResponseDto(specializationRepository.save(currentSpec));
    }

    public String deleteSpecialization (Long id) {
        Specialization specialization = specializationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Specialization " + id + " not found!"));
        specializationRepository.delete(specialization);
        return "Specialization " + specialization.getName() + " with id " + id + " has been deleted!";
    }
}
