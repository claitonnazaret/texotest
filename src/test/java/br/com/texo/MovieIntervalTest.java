package br.com.texo;

import br.com.texo.resource.MovieResource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MovieIntervalTest extends TexoApplicationTests{
    private static final String PATH_API = "/movies/intervals";

    private MockMvc mockMvc;

    @Autowired
    private MovieResource movieResource;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieResource).build();
    }

    @Test
    public void testGETIndexMovieResource_ReturnStatusCode200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(PATH_API)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGETMovieIntervalResource_TamanhoArrayMax_Min() throws Exception {
         this.mockMvc.perform(MockMvcRequestBuilders.get(PATH_API))
                 .andExpect(jsonPath("$.max", hasSize(1)))
                 .andExpect(jsonPath("$.min", hasSize(1)));
    }
}
