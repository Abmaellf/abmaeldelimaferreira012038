package select.music.infra.sockt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import select.music.event.album.AlbumCreatedEvent;

@Component
@RequiredArgsConstructor
public class AlbumWebSocketListener {

    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void onAlbumCreated(AlbumCreatedEvent event) {
        messagingTemplate.convertAndSend(
                "/topic/albums",
                event.album()
        );
    }
}
