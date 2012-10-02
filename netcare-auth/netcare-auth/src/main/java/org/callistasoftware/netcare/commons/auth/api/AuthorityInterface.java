package org.callistasoftware.netcare.commons.auth.api;

/**
 * Author: Henrik Rostam
 */
public interface AuthorityInterface {
    String getId();

    void setId(String id);

    AuthorityRole getAuthority();

    void setAuthority(AuthorityRole authority);

    UserInterface getUser();

    void setUser(UserInterface user);

    @Override
    String toString();
}
