/* Licensed under MIT 2022. */
package edu.kit.kastel.mcse.ardoco.stanfordnlp.corenlp;

import edu.kit.kastel.mcse.ardoco.core.api.text.Text;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;


/**
 * Copied from <a href="https://github.com/ArDoCo/Core/blob/783f3388617158b0da0a8f43d6cb8e20041f4a09/text-provider/src/main/java/edu/kit/kastel/mcse/ardoco/core/text/providers/corenlp/CoreNLPProvider.java">...</a>
 * since a DataRepository was not needed.
 */
public class CoreNLPProvider {
    private static final String ANNOTATORS = "tokenize,ssplit,pos,parse,depparse,lemma"; // further: ",ner,coref"
    private static final String DEPENDENCIES_ANNOTATION = "EnhancedPlusPlusDependenciesAnnotation";

    /**
     * analyses and annotates the given text and returns the result.
     * @param text  the text
     * @return      the annotated text
     */
    public Text processText(InputStream text) {
        String inputText = readInputText(text);
        Properties props = getStanfordProperties();
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(inputText);
        pipeline.annotate(document);
        return new TextImpl(document);
    }

    private String readInputText(InputStream text) {
        var scanner = new Scanner(text, StandardCharsets.UTF_8);
        scanner.useDelimiter("\\A");
        String inputText = scanner.next();
        scanner.close();
        return inputText;
    }

    private static Properties getStanfordProperties() {
        Properties allStanfordProperties = new Properties();
        allStanfordProperties.setProperty("annotators", ANNOTATORS);

        allStanfordProperties.put("parse", DEPENDENCIES_ANNOTATION);
        allStanfordProperties.put("depparse", DEPENDENCIES_ANNOTATION);
        allStanfordProperties.put("coref.algorithm", "fastneural");

        return allStanfordProperties;
    }
}
