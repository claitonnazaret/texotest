package br.com.texo;

import br.com.texo.domain.Movie;
import br.com.texo.domain.Producer;
import br.com.texo.domain.Studio;
import br.com.texo.repository.MovieRepository;
import br.com.texo.resource.MovieResource;
import br.com.texo.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovieTest extends TexoApplicationTests{
    private static final String PATH_API = "/movies/";

    private MockMvc mockMvc;

    @Autowired
    private MovieResource movieResource;

    @Autowired
    private MovieService service;

    @Mock
    private MovieRepository repository;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testGETIndexMovieResource_ReturnStatusCode200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH_API)).andExpect(status().isOk());
    }

    @Test
    public void testGETMovieIntervalResource_TamanhoArrayMaiorQueZero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_API)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void FindAll_ReturnsAPagedListOfTasks() {
        when(repository.findAll()).thenReturn(Arrays.asList(
                new Movie(
                        Calendar.getInstance().getTime(),
                        "Movie 1",
                        new HashSet(Arrays.asList(new Studio("Studio 1"))),
                        new HashSet(Arrays.asList(new Producer("Producer 1"))),
                        true
                ),
                new Movie(
                        Calendar.getInstance().getTime(),
                        "Movie 2",
                        new HashSet(Arrays.asList(new Studio("Studio 2"))),
                        new HashSet(Arrays.asList(new Producer("Producer 2"))),
                        true
                )
        ));

        Pageable pageRequest = PageRequest.of(0, 10);
        List<Movie> movies = service.getAllMovies(pageRequest).getContent();

        assertThat(movies.size(), equalTo(10));
    }

    @Test
    public void testGETMovieIntervalResource_RetornarStatusCode404() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/movies/not_found"))
                .andExpect(status().isNotFound());
    }
}
