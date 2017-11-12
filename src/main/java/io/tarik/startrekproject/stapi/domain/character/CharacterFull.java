package io.tarik.startrekproject.stapi.domain.character;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class CharacterFull {
    private String uid;
    private String name;
    @Singular("characterSpecies")
    private List<CharacterSpecies> characterSpecies;

}
