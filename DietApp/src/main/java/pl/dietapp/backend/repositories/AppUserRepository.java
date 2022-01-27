package pl.dietapp.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.dietapp.backend.model.AppUser;

import java.util.Optional;

@Repository
public class AppUserRepository {

    @Autowired
    JdbcTemplate template;

    public AppUser findByUserName(String username) {
        String sql = "SELECT * FROM AppUser WHERE name ='"+username+"' ";
        return template.queryForObject(sql, new AppUserRowMapper());

    }

    public void saveUser(AppUser appUser) {
    String sql = "INSERT INTO AppUser (name, passwordSalt,passwordHash, role) VALUES(?,?,?,?)";
   template.update(sql, appUser.getUsername(),appUser.getPasswordSalt(),appUser.getPasswordHash() , appUser.getRole().toString());


    }

}
