package backend.controller;
import WebConfig.ApplicationConfig;
import backend.entity.AppUser;
import backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class })
@WebAppConfiguration
public class UserControllerTest {
    private AppUser tmpUser =new AppUser("31","abd","2","mohamad","3","2");
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @MockBean
    private UserRepository mockRepo;
    @MockBean
    private BCryptPasswordEncoder mockCrypto;
    @MockBean
    private UserDetailsService userService;
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }
    @Test
    public void givenWac_whenServletContext_thenItProvidesuserController() {
    ServletContext servletContext = wac.getServletContext();
    Assert.assertNotNull(servletContext);
    Assert.assertTrue(servletContext instanceof MockServletContext);
    Assert.assertNotNull(wac.getBean("userController"));
    }
    @Test
    public void signUpTestWithSameUserExist() throws Exception {
        Mockito.when(mockRepo.findByUsername("abd")).thenReturn(tmpUser);
        this.mockMvc.perform(post("/users/sign-up").content(asJsonString(tmpUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        Mockito.verify(mockRepo).findByUsername(tmpUser.getUsername());
          Mockito.verify(mockRepo,Mockito.times(0)).save(any(tmpUser.getClass()));


    }
    @Test
    public void signUpTest() throws Exception {
        this.mockMvc.perform(post("/users/sign-up").content(asJsonString(tmpUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockRepo).findByUsername(tmpUser.getUsername());
        Mockito.verify(mockRepo,Mockito.times(1)).save(any(tmpUser.getClass()));


    }
    @Test
    public void logInTestWithoutPermission() throws Exception {
        this.mockMvc.perform(get("/users/username")).andExpect(status().isForbidden());

    }
    @Test
    public void loginTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
        this.mockMvc.perform(get("/users/username").with(user("user"))).andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
