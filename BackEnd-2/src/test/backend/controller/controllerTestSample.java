package backend.controller;
import WebConfig.ApplicationConfig;
import backend.DailyPulseApp;
import backend.entity.AppUser;
import backend.repository.UserRepository;
import backend.security.JWTAuthorizationFilter;
import backend.security.RequestFilter;
import backend.security.WebSecurity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
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
public class controllerTestSample {
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
        AppUser user=new AppUser("31","abd","2","mohamad","3","2");
        Mockito.when(mockRepo.findByUsername("abd")).thenReturn(user);
        this.mockMvc.perform(post("/users/sign-up").content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        Mockito.verify(mockRepo).findByUsername(user.getUsername());
          Mockito.verify(mockRepo,Mockito.times(0)).save(any(user.getClass()));


    }
    @Test
    public void signUpTest() throws Exception {
        AppUser user=new AppUser("31","abd","2","mohamad","3","2");
        this.mockMvc.perform(post("/users/sign-up").content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(mockRepo).findByUsername(user.getUsername());
        Mockito.verify(mockRepo,Mockito.times(1)).save(any(user.getClass()));


    }
    @Test
    public void logInTestWithoutPermission() throws Exception {
        this.mockMvc.perform(get("/users/username")).andExpect(status().isForbidden());

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
