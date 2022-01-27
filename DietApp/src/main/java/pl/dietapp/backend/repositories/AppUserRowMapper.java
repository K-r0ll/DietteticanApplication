package pl.dietapp.backend.repositories;

import lombok.Builder;
import org.springframework.jdbc.core.RowMapper;
import pl.dietapp.backend.model.AppUser;
import pl.dietapp.backend.model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AppUserRowMapper implements RowMapper<AppUser> {


    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
       final AppUser appUser = new AppUser();
       appUser.setId(rs.getLong("id"));
       appUser.setUsername(rs.getString("name"));
       appUser.setPasswordSalt(rs.getString("passwordsalt"));
       appUser.setPasswordHash(rs.getString("passwordhash"));
       appUser.setRole(Role.valueOf(rs.getString("role")));
        return appUser;
    }
}
