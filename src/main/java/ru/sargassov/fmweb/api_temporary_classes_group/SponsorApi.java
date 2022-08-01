package ru.sargassov.fmweb.api_temporary_classes_group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sargassov.fmweb.intermediate_entites.Sponsor;
import ru.sargassov.fmweb.exceptions.SponsorNotFoundException;

import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class SponsorApi { //TEMPORARY CLASS
    private List<Sponsor> sponsorApiList;

    public void setSponsorApiList(List<Sponsor> sponsorApiList) {
        this.sponsorApiList = sponsorApiList;
    }

    public Sponsor getSponsorFromApiById(long id){
        return sponsorApiList.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new SponsorNotFoundException(String.format("Sponsor with id = %d not found!", id)));
    }

    public Sponsor getSponsorByName(String name) {
        return sponsorApiList.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new SponsorNotFoundException(String.format("Sponsor with name = %s not found!", name)));
    }
}
