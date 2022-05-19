package net.yorksolutions.apiexercise;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/info")
public class InfoController {


    @GetMapping("/ip")
    public IpInformation ip() throws UnknownHostException {
        return new IpInformation();

    }

    @GetMapping("/date")
    public DateTimeInformation date() {
        return new DateTimeInformation();

    }

    @GetMapping("/header")
    public HashMap<String, String> getHeaders(@RequestHeader HashMap<String, String> headers) {
        return new HashMap<String, String>(headers);
    }

    @GetMapping("/md5")
    public Md5Information md5(@RequestParam String text) throws NoSuchAlgorithmException {
        return new Md5Information(text);

    }

    @GetMapping("/json/validate")
    public JsonInformation jsonValidate(@RequestParam String json) {
        return new JsonInformation(json);

    }
}