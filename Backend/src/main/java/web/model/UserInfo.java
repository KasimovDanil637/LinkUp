package web.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

import static jakarta.persistence.InheritanceType.JOINED;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private String city;
    private Date birthday;
    private String education;
}
