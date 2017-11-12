package io.tarik.startrekproject.stapi.domain.character;

import lombok.Data;

import java.util.List;

@Data
public class CharacterFull {
    private String uid;
    private String name;
    private List<CharacterSpecies> characterSpecies;

}
