package stanfordnlp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextController {

    @GetMapping ("/stanfordnlp")
    public Text texting(@RequestParam(defaultValue = "") String text) {
        return new Text(text);
    }

}
