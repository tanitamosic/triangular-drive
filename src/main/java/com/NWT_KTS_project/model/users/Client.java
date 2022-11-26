package com.NWT_KTS_project.model.users;

import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.Route;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("CL")
public class Client extends User{

    @Column(name = "paypal")
    private String paypal;
    @Column(name = "wallet")
    private String wallet;
    @Column(name = "credit_available")
    private Float creditAvailable;
    @Column(name = "credit_reserved")
    private Float creditReserved;

    //TODO: da li cuvati ovde ili izvlaciti iz rides?
    //private List<Ride> rideHistory;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "saved_routes",
            joinColumns = {@JoinColumn(name = "passenger_id")},
            inverseJoinColumns = {@JoinColumn(name = "route_id")})
    private List<Route> savedRoutes;

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role r = new Role("ROLE_CLIENT");
        List<Role> retval = new ArrayList<Role>(1);
        retval.add(r);
        return retval;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return this.getEmail();
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.getBlocked();
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
