package daniel.divergente.entities.user;

import java.time.LocalDateTime;
import java.util.UUID;

import daniel.divergente.entities.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;
    private String email;
    private String password;

    private String verificationCode;
    private boolean verified = false;
    private int verificationAttempts = 0;
    
    private LocalDateTime codeExpiresAt;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private PlanType plan;

    private Integer availableCredits;
    private Integer referralBonusCap = 0;

    private boolean deleted = false;
    private LocalDateTime deletedAt;

    public void increaseVerificationAttempts() {
        this.verificationAttempts++;
    }

    public boolean isVerificationCodeExpired() {
        if (this.codeExpiresAt == null) return true;
        return LocalDateTime.now().isAfter(this.codeExpiresAt);
    }

    public boolean requiresPassword() {
        return this.plan != null && this.plan != PlanType.FREE;
    }
}