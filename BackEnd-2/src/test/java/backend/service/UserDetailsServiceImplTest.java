package backend.service;

import backend.entity.AppUser;
import backend.repository.UserRepository;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class UserDetailsServiceImplTest {
    @Test
     public void test(){
        UserRepository mockRepo=Mockito.mock(UserRepository.class);
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(null);
        try {
            mockRepo.findByUsername("123");
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
        UserRepository mockRepo=Mockito.mock(UserRepository.class);
        Mockito.when(mockRepo.findByUsername(any())).thenReturn(tmp);
        try {
            mockRepo.findByUsername("123");
        }
        catch (Exception e){
            return;
        }
    }

}
