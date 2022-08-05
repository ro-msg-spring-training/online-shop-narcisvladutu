package ro.msg.learning.shop.contoller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.service.test_utils.RawDataService;

@RestController
@RequiredArgsConstructor
@Profile("test")
public class TestController {
    private final RawDataService rawDataService;

    @PostMapping("/populate")
    public ResponseEntity<String> populate() {
        rawDataService.populate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clear() {
        rawDataService.clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
