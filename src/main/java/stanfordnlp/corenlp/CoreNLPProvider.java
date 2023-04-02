/* Licensed under MIT 2022. */
package stanfordnlp.corenlp;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import stanfordnlp.corenlp.api.Text;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class CoreNLPProvider {
    private static final String ANNOTATORS = "tokenize,ssplit,pos,parse,depparse,lemma"; // further: ",ner,coref"
    private static final String DEPENDENCIES_ANNOTATION = "EnhancedPlusPlusDependenciesAnnotation";
    private Text annotatedText;

    // Needed for Configuration Generation
    @SuppressWarnings("unused")
    public CoreNLPProvider() {
    }

    private static Properties getStanfordProperties(Properties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties are null");
        }
        var allStanfordProperties = new Properties(properties);
        allStanfordProperties.setProperty("annotators", ANNOTATORS);

        allStanfordProperties.put("parse", DEPENDENCIES_ANNOTATION);
        allStanfordProperties.put("depparse", DEPENDENCIES_ANNOTATION);
        allStanfordProperties.put("coref.algorithm", "fastneural");

        return allStanfordProperties;
    }

    public synchronized Text getAnnotatedText(InputStream text) {
        if (annotatedText == null) {
            annotatedText = processText(text);
        }
        return annotatedText;
    }

    private Text processText(InputStream text) {
        var inputText = readInputText(text);
        Properties props = getStanfordProperties(new Properties());
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
}
