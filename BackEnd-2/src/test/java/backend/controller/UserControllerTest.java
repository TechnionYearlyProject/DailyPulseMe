package backend.controller;
import WebConfig.ApplicationConfig;
import backend.CallParser.GoogleCallParser;
import backend.entity.AppUser;
import backend.entity.Event;
import backend.helperClasses.TwoStrings;
import backend.repository.SubscribedRepository;
import backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static backend.entity.EventTag.Sport;
import static backend.helperClasses.BandType.GOOGLEFIT_BAND;
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


import java.util.ArrayList;

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
    private AppUser tmpUser =new AppUser("31","abd","2","mohamad","ya29.GlvBBdBUYGcDZ3y9TWKK3WcTCUboukfAVSajc3SpUuxvqH3nHNGOxquRND_2amxnuvqlK5te3gCDUm04AKIqJzfxbWXS8kK20h5gbfo0dyDfOjO_48LaC2V57vih","1/3qCL9J5Qr7Ou_PidxdOuNAlN0swZ3nr-5C290pgpfMo");
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @MockBean
    private UserRepository mockRepo;
    @MockBean
    private BCryptPasswordEncoder mockCrypto;
    @MockBean
    private UserDetailsService userService;
    @MockBean
    private SubscribedRepository subRepo;
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
//    @Test /// in this tests i need requested body in the signature of the method in the controller
//    public void signUpTestWithSameUserExist() throws Exception {
//        Mockito.when(mockRepo.findByUsername("abd")).thenReturn(tmpUser);
//        this.mockMvc.perform(post("/users/sign-up").content(asJsonString(tmpUser))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//
//        Mockito.verify(mockRepo).findByUsername(tmpUser.getUsername());
//          Mockito.verify(mockRepo,Mockito.times(0)).save(any(tmpUser.getClass()));
//
//
//    }
//    @Test /// in this tests i need requested body in the signature of the method in the controller
//    public void signUpTest() throws Exception {
//        this.mockMvc.perform(post("/users/sign-up").content(asJsonString(tmpUser))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//        Mockito.verify(mockRepo).findByUsername(tmpUser.getUsername());
//        Mockito.verify(mockRepo,Mockito.times(1)).save(any(tmpUser.getClass()));
//
//
//    }
    @Test
    public void logInTestWithoutPermission() throws Exception {
        this.mockMvc.perform(get("/users/username")).andExpect(status().isForbidden());

    }
    @Test
    public void loginTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
        this.mockMvc.perform(get("/users/username").with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void signUpWithGoogleTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
        this.mockMvc.perform(post("/users/sign-up-google").content("4/AACFanFsDbi7_1uHgzwNzpfvtKKvlfBqNNPfxvcpWawSKTq66u7gBwFqGoD-acw34vFjqoI5xxoZUhLLuvSS2Iw").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    public void getOutlookTokenTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
        TwoStrings tmp=new TwoStrings("firstForTest","secondForTest");
        this.mockMvc.perform(post("/users/getOutlookToken").content(asJsonString(tmp)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void getOutlookTokennotWorkingTest() throws Exception {
        TwoStrings tmp=new TwoStrings("firstForTest","secondForTest");
        this.mockMvc.perform(post("/users/getOutlookToken").content(asJsonString(tmp)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(user("user"))).andExpect(status().isOk());
    }
       @Test
    public void updateTokensTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
        TwoStrings tmp=new TwoStrings("firstForTest","secondForTest");
        this.mockMvc.perform(post("/users/updateTokens").content(asJsonString(tmp)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void verifyAccessTokenTest() throws Exception {
        tmpUser.setCallParser(new GoogleCallParser());
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);

        this.mockMvc.perform(get("/users/verifyAccessToken").with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void refreshAccessTokenTest() throws Exception {
        tmpUser.setCallParser(new GoogleCallParser());
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
        this.mockMvc.perform(get("/users/refreshAccessToken").with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void setFitbitBandTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
        this.mockMvc.perform(post("/users/setFitBitBand").with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void setGoogleFitBandTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
        this.mockMvc.perform(post("/users/setGoogleFitBand").with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void getActiveBandTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
        tmpUser.setActiveBandType(GOOGLEFIT_BAND);
        this.mockMvc.perform(get("/users/getActiveBand").with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void subscribeUserTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(null);
        //Mockito.when(subRepo.findByEmail(any())).thenReturn(tmpUser);
        this.mockMvc.perform(post("/users/subscribe").with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void unsubscribeUserTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(null);
        //Mockito.when(subRepo.findByEmail(any())).thenReturn(tmpUser);
        this.mockMvc.perform(post("/users/unsubscribe").with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void getEventTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);

        this.mockMvc.perform(post("/users/getEvent").content("4/AA").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void deleteEventTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);

        this.mockMvc.perform(post("/users/deleteEvent").content("4/AA").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(user("user"))).andExpect(status().isOk());
    }
    @Test
    public void addEventTest() throws Exception {
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
        Event event=new Event();
        event.setId("dd");
        event.setName("dd");
        event.setStartTime("0");
        event.setEndTime("1000");
        event.setTag(Sport);
        event.setDescription("jjjjj");
        tmpUser.setEvents(new ArrayList<>());
        this.mockMvc.perform(post("/users/addEvent").content(asJsonString(event)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(user("user"))).andExpect(status().isOk());
    }
//    @Test /// in this tests i need requested body in the signature of the method in the controller
//    public void getEventsTest() throws Exception {
//        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmpUser);
//        tmpUser.setEvents(new ArrayList<>());
//        TwoStrings tmp=new TwoStrings("0","1000000000000");
//        this.mockMvc.perform(post("/users/getEventsBetweenInterval").content(asJsonString(tmp)).contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON).with(user("user"))).andExpect(status().isOk());
//    }


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
