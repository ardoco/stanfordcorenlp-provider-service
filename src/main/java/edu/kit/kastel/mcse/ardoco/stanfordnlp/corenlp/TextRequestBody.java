package edu.kit.kastel.mcse.ardoco.stanfordnlp.corenlp;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class TextRequestBody {

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
        return this.text;
    }
}
