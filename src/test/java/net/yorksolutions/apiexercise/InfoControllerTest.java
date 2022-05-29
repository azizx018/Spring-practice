package net.yorksolutions.apiexercise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class InfoControllerTest {

    //below either way is correct- if you do not need to inject MOCKS then do not use @Inject mocks to the top way
   //InfoController controller = new InfoController(repository);
    @InjectMocks
    InfoController controller;

    @Mock
    UserAccountRepository repository;

    @Mock
    HttpHeaders headers;

    @Mock
    HttpServletRequest request;
    @Mock
    HashMap<UUID, Long> tokenMap;


    @Test
    void itShouldReturnUnauthWhenUserIsWrong() {
        final var badUsername = "badUsername";
        final var goodPassword = "goodPassword";
        when(repository.findByUsernameAndPassword(badUsername,goodPassword)).thenReturn(Optional.empty());
        lenient().when(repository.findByUsernameAndPassword(not( eq(badUsername)), eq(goodPassword))).thenReturn(Optional.of(new UserAccount()));
        assertThrows(ResponseStatusException.class, () -> controller.login("badUsername", "goodPassword"));

    }
    @Test
    void itShouldReturnUnauthWhenPassIsWrong() {
        final var goodUsername = "goodUsername";
        final var badPassword = "badPassword";
        lenient().when(repository.findByUsernameAndPassword(goodUsername,badPassword)).thenReturn(Optional.empty());
        lenient().when(repository.findByUsernameAndPassword(eq(goodUsername), not(eq(badPassword)))).thenReturn(Optional.of(new UserAccount()));
        assertThrows(ResponseStatusException.class, () -> controller.login("badUsername", "goodPassword"));

    }

    @Test
    void itShouldMapUserIdWhenLoginIsSuccessful() {
        final var username = UUID.randomUUID().toString();
        final var password =UUID.randomUUID().toString();
        final Long id = (long) (Math.random() * 9999999);
        final UserAccount expected = new UserAccount();
        expected.id = id;
        expected.username = username;
        expected.password = password;
        when(repository.findByUsernameAndPassword(eq(username), eq(password)))
                .thenReturn(Optional.of(expected));
        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);
        when(tokenMap.put(captor.capture(), eq(id))).thenReturn(0L);
        final var token = controller.login(username,password);
        assertEquals(token, captor.getValue());
    }

    @Test
    void itShouldReturnInvalidIfUsernameExists() {
        final String username = "some username";
        when(repository.findByUsername(username)).thenReturn(Optional.of(
                new UserAccount()));
        assertThrows(ResponseStatusException.class, () -> {
            controller.register(username, "");
        });
    }

    @Test
    void itShouldThrowUnauthWhenIPIsCalledWithBadToken() {
        final var token = UUID.randomUUID();
        when(tokenMap.containsKey(token)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () ->controller.ip(token, request));
    }
    @Test
    void itShouldReturnAnIpAddress() throws UnknownHostException {
        final String ip = "1.2.3.4";
        final IpInformation expectedIP = new IpInformation(ip);
        final var token = UUID.randomUUID();
        when(request.getRemoteAddr()).thenReturn(ip);
        when(tokenMap.containsKey(token)).thenReturn(true);
        assertEquals(expectedIP, controller.ip(token, request));

    }
    @Test
    void itShouldTakeInAMapAndReturnTheSameMap() {
            HashMap<String, String> expected = new HashMap<>();
            expected.put("A", "a");
            HashMap<String, String> HttpHeaders = new HashMap<>();
            HttpHeaders.put("A", "a");
            assertEquals(expected, controller.getHeaders(HttpHeaders));
    }
    @Test
    void itShouldReturnADateAndTime() {
        final var expectedDate = LocalDate.of(2022,1,1);
        final var expectedTime = LocalTime.of(10,30,05);
        Mockito.mockStatic(LocalDate.class).when(LocalDate::now).thenReturn(expectedDate);
        Mockito.mockStatic(LocalTime.class).when(LocalTime::now).thenReturn(expectedTime);
        DateTimeInformation result = controller.date();
        assertEquals("01-01-2022",result.date);
        assertEquals("10:30:05 AM", result.time);

    }
    @Test
    void itShouldCorrectlyHashHelloWorld() throws NoSuchAlgorithmException {
        final String hashHelloWorld = "68e109f0f40ca72a15e05cc22786f8e6";
        final String userInput = "HelloWorld";
        //Md5Information expected = new Md5Information(userInput);
        //expected.md5 = "68e109f0f40ca72a15e05cc22786f8e6";
        Md5Information result = controller.md5(userInput);
        assertEquals(hashHelloWorld, result.md5);
        assertEquals(userInput, result.original);
    }
    @Test
    void itShouldValidateAsTrueWithValidJSON() {
        final String passedJSON = "{}";
        JSONValidator result = controller.jsonValidate(passedJSON);
        assertEquals(true, result.validate);
    }
    @Test
    void itShouldShowValidateOfFalseWithInvalidJSON() {
        final String invalidJSON = "hi";
        JSONValidator result = controller.jsonValidate(invalidJSON);
        assertEquals(false, result.validate);
    }

}
