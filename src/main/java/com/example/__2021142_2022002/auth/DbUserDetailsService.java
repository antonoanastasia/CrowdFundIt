package com.example.__2021142_2022002.auth;

import com.example.__2021142_2022002.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

//κλάση service, αυτόματη εγγραφή στο Spring context
@Service
public class DbUserDetailsService implements UserDetailsService {

    //repository χρηστών
    @Autowired
    private UserRepository users;

    //Μέθοδος που ζητά το Spring Security για authentication
    //Φορτώνει τα στοιχεία του χρήστη με βάση το username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Αναζήτηση του χρήστη στη βάση
        var u = users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        //Μετατρέπει τους ρόλους του χρήστη σε GrantedAuthority
        Set<GrantedAuthority> authorities = u.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toSet());

        //Δημιουργεί και επιστρέφει ένα User object του Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPasswordHash())
                .authorities(authorities)
                .accountLocked(u.isLocked())
                .disabled(!u.isEnabled())
                .build();
    }
}
