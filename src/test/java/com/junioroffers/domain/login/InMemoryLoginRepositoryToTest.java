package com.junioroffers.domain.login;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryLoginRepositoryToTest implements LoginRepository{
    Map<String, User> database = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(database.get(username));
    }

    @Override
    public User save(User entityUser) {
        UUID id = UUID.randomUUID();
        final User user = new User(
                id.toString(),
                entityUser.username(),
                entityUser.password()
        );
        database.put(user.username(), user);
        return user;
    }
}
