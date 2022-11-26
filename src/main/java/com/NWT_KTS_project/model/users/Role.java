package com.NWT_KTS_project.model.users;

import org.springframework.security.core.GrantedAuthority;


public class Role implements GrantedAuthority {

    /**
     * String implementation of GrantedAuthority
     * Possible values are:
     * ROLE_CLIENT
     * ROLE_DRIVER
     * ROLE_ADMIN
     * */
    String role;

    public Role(String role_name) {
        role = role_name;
    }

    /**
     * If the <code>GrantedAuthority</code> can be represented as a <code>String</code>
     * and that <code>String</code> is sufficient in precision to be relied upon for an
     * access control decision by an {@link AccessDecisionManager} (or delegate), this
     * method should return such a <code>String</code>.
     * <p>
     * If the <code>GrantedAuthority</code> cannot be expressed with sufficient precision
     * as a <code>String</code>, <code>null</code> should be returned. Returning
     * <code>null</code> will require an <code>AccessDecisionManager</code> (or delegate)
     * to specifically support the <code>GrantedAuthority</code> implementation, so
     * returning <code>null</code> should be avoided unless actually required.
     *
     * @return a representation of the granted authority (or <code>null</code> if the
     * granted authority cannot be expressed as a <code>String</code> with sufficient
     * precision).
     */
    @Override
    public String getAuthority() {
        return this.role;
    }
}