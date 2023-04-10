/* Licensed under MIT 2022. */
package stanfordnlp;

import edu.kit.kastel.mcse.ardoco.core.api.data.text.Text;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import stanfordnlp.corenlp.TextImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

public class CoreNLPProvider {
    private static final String ANNOTATORS = "tokenize,ssplit,pos,parse,depparse,lemma"; // further: ",ner,coref"
    private static final String DEPENDENCIES_ANNOTATION = "EnhancedPlusPlusDependenciesAnnotation";

    // Needed for Configuration Generation
    @SuppressWarnings("unused")
    public CoreNLPProvider() {
    }

//    public static void main(String[] args) {
//        String text = "hello";
//        new CoreNLPProvider().processText(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
//    }

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
