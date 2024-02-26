package com.junioroffers.domain.login;

import java.util.Optional;

interface LoginRepository {

    Optional<User> findByUsername(String username);

    User save(User user);

}
