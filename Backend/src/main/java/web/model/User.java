package web.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.InheritanceType.JOINED;

@Getter@Setter
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String secondName;
    private String username;
    private String email;

    @Enumerated(value = EnumType.STRING)
    private RoleType userRole;
    private String password;
}

