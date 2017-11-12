package io.tarik.startrekproject.stapi.domain.character;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterSpecies {
    private String uid;
    private String name;
}
