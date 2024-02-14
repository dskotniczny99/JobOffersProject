package com.junioroffers.domain.loginandregister;

import com.junioroffers.domain.loginandregister.dto.RegisterUserDto;
import com.junioroffers.domain.loginandregister.dto.RegistrationResultDto;
import com.junioroffers.domain.loginandregister.dto.UserDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(new InMemoryLoginRepositoryToTest());

    @Test
    void should_register_user() {

        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "password");

        // when
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(registerUserDto);

        // then
        assertAll(
                () -> assertThat(registrationResultDto.created()).isTrue(),
                () -> assertThat(registrationResultDto.username()).isEqualTo("username")
        );
    }

    @Test
    void should_find_user_by_user_name() {

        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "password");
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(registerUserDto);

        // when
        UserDto userDto= loginAndRegisterFacade.findByUsername(registerUserDto.username());

        // then
        assertThat(userDto).isEqualTo(new UserDto(registrationResultDto.id(), "password", "username"));
    }


    @Test
    void should_throw_exception_when_user_not_found() {

        // given
        String username = "not_existing_user";

        // when
        Throwable throwable = catchThrowable(() -> loginAndRegisterFacade.findByUsername(username));

        // then
        AssertionsForClassTypes.assertThat(throwable)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");
    }

}