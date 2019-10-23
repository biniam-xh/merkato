package edu.mum.mercato.service;

import edu.mum.mercato.config.MerkatoUserDetails;

public interface SecurityService {
    public MerkatoUserDetails findLoggedInUser();

    void autoLogin(String username, String password);
}
