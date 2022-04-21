package ru.sargassov.fmweb.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.dto.Sponsor;
import ru.sargassov.fmweb.services.SponsorService;

import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class SponsorApi {
    private List<Sponsor> sponsorApiList;

    public void setSponsorApiList(List<Sponsor> sponsorApiList) {
        this.sponsorApiList = sponsorApiList;
    }
}
