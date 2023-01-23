package com.NWT_KTS_project.dto;

import com.NWT_KTS_project.model.Photo;
import com.NWT_KTS_project.model.enums.City;
import com.NWT_KTS_project.model.users.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoggedUserDTO {

    /**
     * ACCESS ROLE SPECIFIC FIELDS WITH HTTP REQUESTS
     * USING ID OR EMAIL */

    public Integer id;

    public String name;
    public String lastName;
    public String email;
    public String phone;
    public City city;
    public Boolean blocked;
    public Boolean activated;
    public Photo photo;

    public String accessToken;
    public String role;

    public LoggedUserDTO(User u, TokenState t) {
        id = u.getId();
        name = u.getName();
        lastName = u.getLastName();
        email = u.getEmail();
        phone = u.getPhone();
        city = u.getCity();
        blocked = u.getBlocked();
        activated = u.getActivated();
        photo = u.getPhoto();
        accessToken = t.getAccessToken();
        role = t.getRole();
    }
}
