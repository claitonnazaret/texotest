package br.com.texo.resource;

import br.com.texo.domain.Movie;
import br.com.texo.dto.ResultDTO;
import br.com.texo.service.MovieService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@Api(tags = "Movies", description = "API movies")
public class MovieResource {

    private final MovieService movieService;

    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @ApiOperation(value = "List all movies")
    @GetMapping("/")
    public ResponseEntity<Page<Movie>> getAllMovies(Pageable pageable) {
        return ResponseEntity
                .ok(movieService.getAllMovies(pageable));
    }

    @ApiOperation(value = "List intervals between winner movies")
    @GetMapping("/intervals")
    public ResponseEntity<ResultDTO> getIntervalPremium() {
        return ResponseEntity
                .ok(movieService.getIntervalPremium());
    }
}
