package net.yorksolutions.apiexercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)


public class InfoControllerTest {

    //below either way is correct- if you do not need to inject MOCKS then do not use @Inject mocks to the top way
   InfoController controller = new InfoController();
//    @InjectMocks
//    InfoController controller;

    @Mock
    HttpHeaders headers;

    @Mock
    HttpServletRequest request;




    @Test
    void itShouldReturnAnIpAddress() throws UnknownHostException {
        final String ip = "1.2.3.4";
        final IpInformation expectedIP = new IpInformation(ip);
        when(request.getRemoteAddr()).thenReturn(ip);
        Assertions.assertEquals(expectedIP, controller.ip(request));

    }
    @Test
    void itShouldTakeInAMapAndReturnTheSameMap() {
            HashMap<String, String> expected = new HashMap<>();
            expected.put("A", "a");
            HashMap<String, String> HttpHeaders = new HashMap<>();
            HttpHeaders.put("A", "a");
            Assertions.assertEquals(expected, controller.getHeaders(HttpHeaders));

    }
    @Test
    void itShouldReturnADateAndTime() {
        final var expectedDate = LocalDate.of(2022,1,1);
        final var expectedTime = LocalTime.of(10,30,05);
        Mockito.mockStatic(LocalDate.class).when(LocalDate::now).thenReturn(expectedDate);
        Mockito.mockStatic(LocalTime.class).when(LocalTime::now).thenReturn(expectedTime);
        DateTimeInformation result = controller.date();
        Assertions.assertEquals("01-01-2022",result.date);
        Assertions.assertEquals("10:30:05 AM", result.time);

    }
    @Test
    void itShouldCorrectlyHashHelloWorld() throws NoSuchAlgorithmException {
        final String hashHelloWorld = "68e109f0f40ca72a15e05cc22786f8e6";
        final String userInput = "HelloWorld";
        //Md5Information expected = new Md5Information(userInput);
        //expected.md5 = "68e109f0f40ca72a15e05cc22786f8e6";
        Md5Information result = controller.md5(userInput);
        Assertions.assertEquals(hashHelloWorld, result.md5);
        Assertions.assertEquals(userInput, result.original);
    }
    @Test
    void itShouldValidateAsTrueWithValidJSON() {
        final String passedJSON = "{}";
        JSONValidator result = controller.jsonValidate(passedJSON);
        Assertions.assertEquals(true, result.validate);
    }
    @Test
    void itShouldShowValidateOfFalseWithInvalidJSON() {
        final String invalidJSON = "hi";
        JSONValidator result = controller.jsonValidate(invalidJSON);
        Assertions.assertEquals(false, result.validate);
    }

}
