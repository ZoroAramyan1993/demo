package security;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
public class DetailsService implements UserDetailsService {


    @Autowired(required = true )
    UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceAccessException("user not found" + email));
        return UserPrincipal.createUser(user);
    }

    @Transactional
    public UserDetails findById(Integer id)  {
       User user = userRepository.findById(id).orElseThrow(()->new ResourceAccessException("not found" + id));
       return UserPrincipal.createUser(user);
    }

    public UserDetails finById(Integer id) {
        User user = userRepository.findById(Math.toIntExact(id)).orElseThrow(()->new ResourceAccessException("not found" + id));
        return UserPrincipal.createUser(user);
    }
}
