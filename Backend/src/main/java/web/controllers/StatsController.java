package web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.dto.response.StatsDto;
import web.exception.DataException;
import web.model.User;
import web.service.ActivityService;
import web.service.PostService;
import web.service.StatsService;
import web.service.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


@RestController
@RequestMapping("/stats")
@CrossOrigin("${cors.hosts}")
@PreAuthorize("isAuthenticated()")
public class StatsController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private StatsService statsService;

    @PostMapping("/list")
    public ResponseEntity getList(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        HttpClient client = HttpClient.newHttpClient();
        User user = userService.findUserByUsername(userDetails.getUsername());
        Date firstDate = statsService.minDate(user);
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        LocalDate firstLocalDate = firstDate.toLocalDate();
        LocalDate currentLocalDate = currentDate.toLocalDate();
        List<LocalDate> allDates = new ArrayList<>(IntStream.range(0, (int) ChronoUnit.DAYS.between(firstLocalDate, currentLocalDate))
                .mapToObj(firstLocalDate::plusDays)
                .toList());
        allDates.add(currentLocalDate);
        Map<LocalDate, Integer> mapDate = new HashMap<>();
        for (LocalDate date : allDates){
            mapDate.put(date, statsService.count(user, Date.valueOf(date)));
        }
        StatsDto statsDto = new StatsDto();
        statsDto.setMap(mapDate);
        statsDto.setCountPosts(postService.countAllByUser(user));
        statsDto.setCountLikes(activityService.getCountPostByUser(user));
        ObjectMapper objectMapper = new ObjectMapper();
        String respBody;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8099/stats"))
                    .headers("Content-Type", "application/json;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(statsDto)))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            respBody = response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new DataException("Send data error");
        }
        return ResponseEntity.ok(respBody);
    }

}
