/* Licensed under MIT 2023. */
package edu.kit.kastel.mcse.ardoco.stanfordnlp.corenlp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kit.kastel.mcse.ardoco.core.api.text.Text;
import edu.kit.kastel.mcse.ardoco.core.textproviderjson.converter.ObjectToDtoConverter;
import edu.kit.kastel.mcse.ardoco.core.textproviderjson.dto.TextDto;
import edu.kit.kastel.mcse.ardoco.core.textproviderjson.error.NotConvertableException;

@RestController
public class TextController {
    private static final Logger logger = LoggerFactory.getLogger(TextController.class);

    @PostMapping(path = "/stanfordnlp")
    public TextDto postTextRequest(@RequestBody TextRequestBody textRequestBody) throws NotConvertableException { //TODO
        String text = textRequestBody.getText();
        logger.info("Getting request for a text (length: {})", text.length());
        InputStream inputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
        Text annotatedText = processText(inputStream);
        return convertToDto(annotatedText);
    }

    @GetMapping("/stanfordnlp")
    public TextDto texting(@RequestParam(defaultValue = "The quick brown fox jumped over the lazy dog.") String text) throws NotConvertableException {
        InputStream inputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
        Text annotatedText = processText(inputStream);
        return convertToDto(annotatedText);
    }

    private Text processText(InputStream text) {
        CoreNLPProvider nlpProvider = new CoreNLPProvider();
        return nlpProvider.processText(text);
    }

    private TextDto convertToDto(Text text) throws NotConvertableException {
        ObjectToDtoConverter converter = new ObjectToDtoConverter();
        return converter.convertTextToDTO(text);
    }

}
