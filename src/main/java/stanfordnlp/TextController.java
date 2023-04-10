package stanfordnlp;

import edu.kit.kastel.mcse.ardoco.core.api.data.text.Text;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stanfordnlp.dtoconverter.ObjectToDtoConverter;
import stanfordnlp.dtoconverter.dto.TextDTO;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class TextController {

    @GetMapping ("/stanfordnlp")
    public TextDTO texting(@RequestParam(defaultValue = "I like you.") String text) {
        InputStream inputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
        Text annotatedText = processText(inputStream);
        return convertToDto(annotatedText);
    }

    private Text processText(InputStream text) {
        CoreNLPProvider nlpProvider = new CoreNLPProvider();
        return nlpProvider.processText(text);
    }

    private TextDTO convertToDto(Text text) {
        ObjectToDtoConverter converter = new ObjectToDtoConverter();
        return converter.convertTextToDTO(text);
    }

}
