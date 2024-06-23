import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserCreds;
import user.UserInfo;
import user.UserRequest;
import user.UserResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChangeUserDataWithAuthorizationTests {

    private static UserRequest userRequest;
    private static UserResponse userResponse;
    private UserClient userClient;

    @Before
    public void setUp() {
        userRequest = UserRequest.userGenerator();
        userClient = new UserClient();
        userClient.userCreate(userRequest);
        userResponse = userClient.userLogin(userRequest).body()
                .as(UserResponse.class);
    }

    @Test
    @DisplayName("200 OK: successful change user email")
    public void successfulChangeUserEmail() {
        userRequest.setEmail(UserRequest.userGenerator().getEmail());
        userClient.userChange(userRequest, UserCreds.getCredsFrom(userResponse))
                .then()
                .assertThat()
                .statusCode(SC_OK);
        UserInfo userInfo = userClient.getUserInfo(UserCreds.getCredsFrom(userResponse))
                .body()
                .as(UserInfo.class);
        assertTrue(userInfo.isSuccess());
        assertEquals(userRequest.getEmail().toLowerCase(), userInfo.getUser().getEmail());
    }

    @Test
    @DisplayName("200 OK: successful change user name")
    public void successfulChangeUserName() {
        userRequest.setEmail(UserRequest.userGenerator().getName());
        userClient.userChange(userRequest, UserCreds.getCredsFrom(userResponse))
                .then()
                .assertThat()
                .statusCode(SC_OK);
        UserInfo userInfo = userClient.getUserInfo(UserCreds.getCredsFrom(userResponse))
                .body()
                .as(UserInfo.class);
        assertTrue(userInfo.isSuccess());
        assertEquals(userRequest.getName(), userInfo.getUser().getName());
    }

    @Test
    @DisplayName("200 OK: successful change user password")
    public void successfulChangeUserPassword() {
        userRequest.setEmail(UserRequest.userGenerator().getPassword());
        userClient.userChange(userRequest, UserCreds.getCredsFrom(userResponse))
                .then()
                .assertThat()
                .statusCode(SC_OK);
        userClient.userLogin(userRequest).then().assertThat().statusCode(SC_OK);
    }

    @After
    public void tearDown() {
        userClient.userDelete(userRequest, UserCreds.getCredsFrom(userResponse))
                .body()
                .as(UserResponse.class);
    }
}