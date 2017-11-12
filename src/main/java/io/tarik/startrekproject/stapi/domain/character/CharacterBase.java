package io.tarik.startrekproject.stapi.domain.character;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterBase {
    private String uid;
    private String name;
}
