package br.com.texo.service;

import br.com.texo.domain.Movie;
import br.com.texo.domain.Producer;
import br.com.texo.domain.Studio;
import br.com.texo.dto.MovieDTO;
import br.com.texo.dto.ResultDTO;
import br.com.texo.dto.ResultObjectDTO;
import br.com.texo.repository.MovieRepository;
import br.com.texo.repository.ProducerRepository;
import br.com.texo.repository.StudioRepository;
import br.com.texo.utils.Util;
import net.bytebuddy.implementation.bytecode.constant.LongConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private final MovieRepository movieRepository;
    @Autowired
    private final ProducerRepository producerRepository;
    @Autowired
    private final StudioRepository studioRepository;

    private final String FILE_NAME = "classpath:csv/movielist.csv";

    public MovieService(MovieRepository movieRepository, ProducerRepository producerRepository, StudioRepository studioRepository) {
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository;
        this.studioRepository = studioRepository;
    }

    @PostConstruct
    public void init() {
        try {
            Long totalMovies = movieRepository.findAll(Pageable.unpaged()).getTotalElements();
            if(totalMovies.equals(0L)) {
                Resource resource = resourceLoader.getResource(FILE_NAME);
                List list = Util.mapToCSV(resource);
                for (Object object : list) {
                    if(list.indexOf(object) > 0){
                        Movie movie = new MovieDTO().convertToMovie((MovieDTO) object);
                        movie.setStudios(persistStudios(movie.getStudios()));
                        movie.setProducers(persistProducers(movie.getProducers()));

                        movieRepository.save(movie);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<Producer> persistProducers(Set<Producer> producers) {
        Set<Producer> ret = new HashSet<>();
        producers.stream().forEach(producer -> {
            Optional<Producer> p = producerRepository.findAllByNameIgnoreCase(producer.getName());
            Producer producer1 = p.orElse(producer);
            ret.add(producerRepository.save(producer1));
        });
        return ret;
    }

    private Set<Studio> persistStudios(Set<Studio> studios) {
        Set<Studio> ret = new HashSet<>();
        studios.stream().forEach(studio -> {
            Optional<Studio> s = studioRepository.findAllByNameIgnoreCase(studio.getName());
            Studio studio1 = s.orElse(studio);
            ret.add(studioRepository.save(studio1));
        });
        return ret;
    }

    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public ResultDTO getIntervalPremium() {
        List<ResultObjectDTO> minIntervalProducers = movieRepository.findMinIntervalProducers();
        List<ResultObjectDTO> maxIntervalProducers = movieRepository.findMaxIntervalProducers();
        return new ResultDTO(minIntervalProducers, maxIntervalProducers);
    }
}
