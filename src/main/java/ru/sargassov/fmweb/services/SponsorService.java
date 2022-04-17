package ru.sargassov.fmweb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sargassov.fmweb.converters.SponsorConverter;
import ru.sargassov.fmweb.dto.SponsorDto;
import ru.sargassov.fmweb.repositories.SponsorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SponsorService {
    private final SponsorRepository sponsorRepository;
    private final SponsorConverter sponsorConverter;

    public List<SponsorDto> getAllSponsors(){
        log.info("SponsorSerive.getAllSponsors");
        return sponsorRepository.findAll().stream()
                .map(sponsorConverter::entityToDto).collect(Collectors.toList());
    }
}
