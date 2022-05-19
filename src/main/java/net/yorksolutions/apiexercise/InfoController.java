package net.yorksolutions.apiexercise;


import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/info")
public class InfoController {


    @GetMapping("/ip")
    public IpInformation ip(HttpServletRequest request) throws UnknownHostException {
        return new IpInformation(request.getRemoteAddr());

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
    public JSONValidator jsonValidate(@RequestParam String json){
        return new JSONValidator(json);

    }
}