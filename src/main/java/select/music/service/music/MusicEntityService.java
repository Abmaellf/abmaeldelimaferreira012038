package select.music.service.music;

import select.music.dto.music.MusicRequest;
import select.music.dto.music.MusicResponse;

import java.util.UUID;

public interface MusicEntityService {

    MusicResponse create(MusicRequest request);

    MusicResponse update(UUID id, MusicRequest request);

    MusicResponse getById(UUID id);
}
