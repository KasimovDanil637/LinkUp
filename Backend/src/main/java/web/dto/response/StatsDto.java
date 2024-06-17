package web.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
public class StatsDto {
    private Map<LocalDate, Integer> map;
    private int countLikes;
    private int countPosts;
}
