//package pl.dietapp;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import pl.dietapp.backend.model.AppUser;
//import pl.dietapp.backend.repositories.AppUserRepository;
//@Configuration
//public class Start {


//    public Start(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
//
//
//        AppUser appUser = new AppUser();
//        appUser.setUsername("Karol");
//        appUser.setPassword(passwordEncoder.encode("karol123"));
//        appUser.setRole("ROLE_ADMIN");
//        appUserRepository.save(appUser);
//
//    }
//}
