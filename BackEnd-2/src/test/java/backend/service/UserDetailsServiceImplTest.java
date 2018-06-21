package backend.service;

import backend.entity.AppUser;
import backend.repository.UserRepository;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.ArgumentMatchers.any;

public class UserDetailsServiceImplTest {
    UserRepository mockRepo=Mockito.mock(UserRepository.class);
    UserDetailsServiceImpl serviceimpl=new UserDetailsServiceImpl(mockRepo);
    @Test
     public void test(){

        Mockito.when(mockRepo.findByUsername(any())).thenReturn(null);
        try {
            serviceimpl.loadUserByUsername("123");
        }
        catch (Exception e){
            return;
        }
    }
    @Test
    public void test2(){
        AppUser tmp=new AppUser();
        tmp.setUsername("123");
        tmp.setPassword("111");

        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmp);
        try {
            serviceimpl.loadUserByUsername("123");
        }
        catch (Exception e){
            return;
        }
    }

}
