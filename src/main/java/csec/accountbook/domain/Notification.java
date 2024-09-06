package csec.accountbook.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private String message;
    private Long timestamp;
    private String type;

    public Notification(Long userId, String message, long timestamp, String type) {
        this.id = userId;
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
    }

    public Notification() {}
}
