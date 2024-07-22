package csec.accountbook.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter @Setter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"to_user", "from_user"})
)
@IdClass(Follow.PK.class)
public class Follow {

    @Id
    @Column(name = "to_user")
    private Long toUser;

    @Id
    @Column(name = "from_user")
    private Long fromUser;

    private LocalDateTime followedAt;

    public Follow(Long toUser, Long fromUser) {
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.followedAt = LocalDateTime.now();
    }

    @NoArgsConstructor
    public static class PK implements Serializable {

        private Long toUser;
        private Long fromUser;

        public PK(Long toUser, Long fromUser) {
            this.toUser = toUser;
            this.fromUser = fromUser;
        }

        @Override
        public int hashCode() {
            return toUser.hashCode() + fromUser.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            PK pk = (PK) obj;
            return toUser.equals(pk.toUser) && fromUser.equals(pk.fromUser);
        }
    }
}
