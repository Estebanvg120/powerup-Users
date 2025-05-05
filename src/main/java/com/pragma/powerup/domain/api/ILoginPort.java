package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.LoginModel;

public interface ILoginPort {
    String login(LoginModel loginModel);
}



