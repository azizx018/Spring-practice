package net.yorksolutions.apiexercise;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeTest {

    class Controller{
        String getDate() {
            final var now = LocalDateTime.now();
            return now.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        }
    }


    @Test
    void itShouldReturnARecentTime() {
        final var controller = new Controller();
        final var expceted = LocalDateTime.of(2020,1,1,1,1,1,1);
        Mockito.mockStatic(LocalDateTime.class).when(LocalDateTime::now)
                .thenReturn(expceted);
        assertEquals("01-01-2020", controller.getDate());

    }
}
