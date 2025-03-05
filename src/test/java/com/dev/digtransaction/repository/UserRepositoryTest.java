package com.dev.digtransaction.repository;

import com.dev.digtransaction.domain.user.User;
import com.dev.digtransaction.domain.user.UserType;
import com.dev.digtransaction.dto.UserRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Should get User successfully from database")
    void findUserByDocumentSuccess() {
        String document = "09687452677";
        UserRequest userDTO = new UserRequest("Jonnys", "Gomes", document,
                "jonnys@email.com", "123456", UserType.COMMON);
        createUser(userDTO);
        Optional<User> result = userRepository.findUserByDocument(document);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get user from database when user not exists")
    void findUserByDocumentError() {
        String document = "09687452677";
        Optional<User> result = userRepository.findUserByDocument(document);
        assertThat(result.isPresent()).isFalse();
    }

    private User createUser(UserRequest userDTO) {
        User user = new User(userDTO);
        entityManager.persist(user);
        return user;
    }
}