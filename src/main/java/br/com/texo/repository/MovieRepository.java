package br.com.texo.repository;

import br.com.texo.domain.Movie;
import br.com.texo.dto.ResultObjectDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    final String SQL_MIN = "with t_interval(producer, \"interval\", previousWin, followingWin) as ("
                + "select "
                + "     distinct p.name as producer"
                + "     ,datediff(year, min(m.year), max(m.year)) as \"interval\" "
                + "     ,min(year(m.year)) as previousWin "
                + "     ,max(year(m.year)) as followingWin "
                + "from movie m "
                + "join movie_producers mp on mp.movie_id = m.id "
                + "join producer p on p.id = mp.producers_id "
                + "where m.winner is true "
                + "group by p.name "
                + "having count(m.id) > 1 "
                + "order by \"interval\" "
                + ") "
                + "select tt.producer, tt.\"interval\", tt.previousWin, tt.followingWin "
                + "from t_interval as tt "
                + "where tt.\"interval\" = select min(tt.\"interval\") from t_interval as tt";
    @Query(value = SQL_MIN, nativeQuery = true)
    List<ResultObjectDTO> findMinIntervalProducers();

    final String SQL_MAX = "with t_interval(producer, \"interval\", previousWin, followingWin) as ("
            + "select "
            + "     distinct p.name as producer"
            + "     ,datediff(year, min(m.year), max(m.year)) as \"interval\" "
            + "     ,min(year(m.year)) as previousWin "
            + "     ,max(year(m.year)) as followingWin "
            + "from movie m "
            + "join movie_producers mp on mp.movie_id = m.id "
            + "join producer p on p.id = mp.producers_id "
            + "where m.winner is true "
            + "group by p.name "
            + "having count(m.id) > 1 "
            + "order by \"interval\" "
            + ") "
            + "select tt.producer, tt.\"interval\", tt.previousWin, tt.followingWin "
            + "from t_interval as tt "
            + "where tt.\"interval\" = select max(tt.\"interval\") from t_interval as tt";
    @Query( value = SQL_MAX, nativeQuery = true)
    List<ResultObjectDTO>  findMaxIntervalProducers();
}
