package csec.accountbook.service;

import csec.accountbook.domain.Notification;
import csec.accountbook.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final Map<Long,SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(Long userId){
        SseEmitter emitter = new SseEmitter(300 * 1000L);
        emitters.put(userId,emitter);
        try{
            emitter.send(SseEmitter.event().name("connect")
                    .data("connected!!"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        emitter.onCompletion(() -> emitters.remove(userId, emitter));
        emitter.onTimeout(() -> emitters.remove(userId, emitter));
        emitter.onError((e) -> emitters.remove(userId, emitter));
        return emitter;
    }

    public void sendNotification(Long userId, String message) {
        SseEmitter emitter = emitters.get(userId);
        System.out.println("emitter = " + emitter);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("notification").data(message));
                System.out.println("Sent notification: " + message + " to user: " + userId);
            } catch (IOException e) {
                emitters.remove(userId);
                System.out.println("Failed to send notification, removing emitter for user: " + userId);
            }
        } else {
            System.out.println("No emitter found for user: " + userId);
        }
    }



}
