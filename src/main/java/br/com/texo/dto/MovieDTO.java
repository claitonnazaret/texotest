package br.com.texo.dto;

import br.com.texo.domain.Movie;
import br.com.texo.utils.Util;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class MovieDTO {
    private String year;
    private String title;
    private String studios;
    private String producers;
    private String winner;

    public MovieDTO() {
    }

    public MovieDTO(Movie movie) {
        this.year = Util.format.format(movie.getYear());
        this.title = movie.getTitle();
        this.studios = movie.getStudios().toString();
        this.producers = movie.getProducers().toString();
        this.winner = Util.booleanToString(movie.isWinner());
    }

    public Movie convertToMovie(MovieDTO movieDTO) {
        return new Movie(
                Util.yearToDate(movieDTO.getYear()),
                movieDTO.getTitle(),
                Util.getStudios(movieDTO.getStudios()),
                Util.getProducers(movieDTO.getProducers()),
                Util.stringToBoolean(movieDTO.getWinner())
        );
    }

}
