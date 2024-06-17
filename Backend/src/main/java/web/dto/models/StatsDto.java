package web.dto.models;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import web.model.User;

import java.sql.Date;
import java.sql.Timestamp;


@Getter
@Setter
public class StatsDto {

    private Long id;
    private UserDto user;
    private Date date;
}
