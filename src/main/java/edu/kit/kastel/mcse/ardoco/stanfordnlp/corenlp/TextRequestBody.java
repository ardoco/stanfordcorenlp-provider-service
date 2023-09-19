package edu.kit.kastel.mcse.ardoco.stanfordnlp.corenlp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextRequestBody {
    private static final Logger logger = LoggerFactory.getLogger(TextRequestBody.class);

    private String text;

    public TextRequestBody(String text) {
        this.text = URLDecoder.decode(text, StandardCharsets.UTF_8);
    }

    public TextRequestBody() {
        // empty
    }

    public void setText(String text) {
        this.text = URLDecoder.decode(text, StandardCharsets.UTF_8);
    }

    public String getText() {
        return text;
    }
}
