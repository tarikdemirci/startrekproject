package io.tarik.startrekproject.stapi.domain.character;

import io.tarik.startrekproject.stapi.domain.common.ResponsePage;
import lombok.Data;

import java.util.List;

@Data
public class CharacterBaseResponse {
    private ResponsePage page;
    private List<CharacterBase> characters;

}
