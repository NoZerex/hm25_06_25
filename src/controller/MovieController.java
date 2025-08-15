import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.top.web.hw10.model.CustomResponse;
import ru.top.web.hw10.service.MovieApiService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieApiService movieApiService;

    @Autowired
    public MovieController(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    @GetMapping
    public ResponseEntity<CustomResponse> searchMovies(
            @RequestParam String title,
            @RequestParam(defaultValue = "10") int viewSize,
            @RequestParam(defaultValue = "1") int page) {

        CustomResponse response = movieApiService.searchMovies(title, viewSize, page);
        return ResponseEntity.ok(response);
    }
}