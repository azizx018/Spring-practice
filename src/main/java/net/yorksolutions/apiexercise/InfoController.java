package net.yorksolutions.apiexercise;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/info")
public class InfoController {
    public final UserAccountRepository repository;
    public final HashMap<UUID, Long> tokenMap;

    @Autowired
    public InfoController(@NonNull UserAccountRepository repository) {
        this.repository = repository;
        this.tokenMap = new HashMap<>();
    }

    public InfoController(@NonNull UserAccountRepository repository, @NonNull HashMap<UUID, Long> tokenMap) {
        this.repository = repository;
        this.tokenMap = tokenMap;
    }


    @GetMapping("/login")
    UUID login (@RequestParam String username, @RequestParam String password) {
        //check to see if the username exists
        //Search for a UserAccount with the given username and password
        var result = repository.findByUsernameAndPassword(username, password);

        if(result.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        //If found:
        //Generate a token that is to be used for all future requests that are associated
            //with this user account
        final var token = UUID.randomUUID();
        //Associate the generated token w/ the user account
        tokenMap.put(token, result.get().id);
        //Provide the generated token to the client for future use
        return token; //from now on use this UUID to let me know who you are
    }

    @GetMapping("/register")
    public void register(@RequestParam String username, @RequestParam String password) {
        if(repository.findByUsername(username).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        repository.save(new UserAccount());
    }


    @GetMapping("/ip")
    public IpInformation ip(UUID token, HttpServletRequest request) throws UnknownHostException {

        if (!tokenMap.containsKey(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
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