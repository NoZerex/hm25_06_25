import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.top.web.hw10.model.CustomResponse;
import ru.top.web.hw10.model.MovieApiResponse;
import ru.top.web.hw10.model.Search;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieApiService {

    @Autowired
    @Qualifier("restTemplateStandard")
    private RestTemplate restTemplate;

    @Value("${movie.api.key}")
    private String apiKey;

    @Value("${movie.base.url}")
    private String baseUrl;

    public CustomResponse searchMovies(String title, int viewSize, int page) {
        // Первый запрос для получения общего количества результатов
        String initialUrl = String.format("%s/?s=%s&apiKey=%s", baseUrl, title, apiKey);
        MovieApiResponse initialResponse = restTemplate.getForObject(initialUrl, MovieApiResponse.class);

        if (initialResponse == null || !"True".equalsIgnoreCase(initialResponse.Response)) {
            throw new RuntimeException("Ошибка при получении данных");
        }

        int totalResults = Integer.parseInt(initialResponse.totalResults);
        int totalPages = (int) Math.ceil((double) totalResults / viewSize);

        // Рассчитываем индексы для пагинации
        int startIndex = (page - 1) * viewSize;
        int endIndex = Math.min(startIndex + viewSize, totalResults);

        // Собираем результаты со всех необходимых страниц
        List<Search> allResults = new ArrayList<>();
        int currentOmdbPage = 1;
        int collected = 0;

        while (collected < endIndex) {
            String url = String.format("%s/?s=%s&apiKey=%s&page=%d", baseUrl, title, apiKey, currentOmdbPage);
            MovieApiResponse response = restTemplate.getForObject(url, MovieApiResponse.class);

            if (response != null && response.Search != null) {
                for (Search item : response.Search) {
                    if (collected >= startIndex && collected < endIndex) {
                        allResults.add(item);
                    }
                    collected++;
                    if (collected >= endIndex) break;
                }
            }
            currentOmdbPage++;
        }

        // Формируем кастомный ответ
        CustomResponse customResponse = new CustomResponse();
        customResponse.setSearch(allResults);
        customResponse.setCurrentPage(page);
        customResponse.setViewSize(viewSize);
        customResponse.setPages(totalPages);
        customResponse.setTotalResults(totalResults);

        return customResponse;
    }
}