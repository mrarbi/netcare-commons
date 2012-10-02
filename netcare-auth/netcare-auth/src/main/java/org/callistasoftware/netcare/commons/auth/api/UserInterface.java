package org.callistasoftware.netcare.commons.auth.api;


import java.util.Set;

import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;

/**
 * Author: Henrik Rostam
 */
public interface UserInterface {

    Set<AuthorityInterface> getAuthorities();

    void setAuthorities(Set<AuthorityInterface> authorities);

    Set<CareUnitInterface> getCareUnits();

    void setCareUnits(Set<CareUnitInterface> careUnits);

    void addCareUnit(CareUnitInterface careUnit);

    String getId();

    String getUsername();

    void setUsername(String userName);

    String getFirstname();

    void setFirstname(String firstName);

    String getLastname();

    void setLastname(String lastName);

    Boolean isEnabled();

    void setEnabled(Boolean enabled);

    String getPassword();

    void setPassword(String password);

    void setId(String id);

    @Override
    String toString();
}
