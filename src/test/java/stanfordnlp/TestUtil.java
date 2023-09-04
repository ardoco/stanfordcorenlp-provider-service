package stanfordnlp;

import edu.kit.kastel.mcse.ardoco.core.api.text.DependencyTag;
import edu.kit.kastel.mcse.ardoco.core.textproviderjson.dto.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TestUtil {

    private TestUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static TextDto getExpectedDto() throws IOException {
        WordDto word1 = new WordDto();
        word1.setId(1);
        word1.setSentenceNo(1);
        word1.setLemma("this");
        word1.setText("This");
        word1.setPosTag(PosTag.forValue("DT"));
        IncomingDependencyDto incomingDependency1 = new IncomingDependencyDto();
        incomingDependency1.setDependencyTag(DependencyTag.NSUBJ);
        incomingDependency1.setSourceWordId(3);
        word1.setIncomingDependencies(new ArrayList<>(List.of(incomingDependency1)));

        WordDto word2 = new WordDto();
        word2.setId(2);
        word2.setSentenceNo(1);
        word2.setLemma("be");
        word2.setText("is");
        word2.setPosTag(PosTag.forValue("VBZ"));
        IncomingDependencyDto incomingDependency2 = new IncomingDependencyDto();
        incomingDependency2.setDependencyTag(DependencyTag.COP);
        incomingDependency2.setSourceWordId(3);
        word2.setIncomingDependencies(new ArrayList<>(List.of(incomingDependency2)));

        WordDto word3 = new WordDto();
        word3.setId(3);
        word3.setSentenceNo(1);
        word3.setLemma("Marie");
        word3.setText("Marie");
        word3.setPosTag(PosTag.forValue("NNP"));
        OutgoingDependencyDto outgoingDependencyDto1 = new OutgoingDependencyDto();
        outgoingDependencyDto1.setTargetWordId(1);
        outgoingDependencyDto1.setDependencyTag(DependencyTag.NSUBJ);
        OutgoingDependencyDto outgoingDependencyDto2 = new OutgoingDependencyDto();
        outgoingDependencyDto2.setTargetWordId(2);
        outgoingDependencyDto2.setDependencyTag(DependencyTag.COP);
        OutgoingDependencyDto outgoingDependencyDto3 = new OutgoingDependencyDto();
        outgoingDependencyDto3.setTargetWordId(4);
        outgoingDependencyDto3.setDependencyTag(DependencyTag.PUNCT);
        word3.setOutgoingDependencies(new ArrayList<>(List.of(outgoingDependencyDto1, outgoingDependencyDto2, outgoingDependencyDto3)));

        WordDto word4 = new WordDto();
        word4.setId(4);
        word4.setSentenceNo(1);
        word4.setLemma(".");
        word4.setText(".");
        word4.setPosTag(PosTag.forValue("."));
        IncomingDependencyDto incomingDependency3 = new IncomingDependencyDto();
        incomingDependency3.setDependencyTag(DependencyTag.PUNCT);
        incomingDependency3.setSourceWordId(3);
        word4.setIncomingDependencies(new ArrayList<>(List.of(incomingDependency3)));

        List<WordDto> words = new ArrayList<>(List.of(word1, word2, word3, word4));

        SentenceDto sentence1 = new SentenceDto();
        sentence1.setSentenceNo(1);
        sentence1.setText("This is Marie.");
        sentence1.setConstituencyTree("(ROOT (S (NP (DT This)) (VP (VBZ is) (NP (NNP Marie))) (. .)))");
        sentence1.setWords(words);

        List<SentenceDto> sentences = new ArrayList<>();
        sentences.add(sentence1);

        TextDto text = new TextDto();
        text.setSentences(sentences);

        return text;
    }

    public static String getJsonExample() {
        return "{\"sentences\": [{" +
                "\"sentenceNo\": 1," +
                "\"text\": \"Hello World!\"," +
                "\"constituencyTree\": \"(ROOT (FRAG (INTJ (UH Hello)) (NP (NNP World)) (. !)))\"," +
                "\"words\": [" +
                "{\"sentenceNo\": 1," +
                "\"id\": 1," +
                "\"text\": \"Hello\"," +
                "\"lemma\": \"hello\"," +
                "\"posTag\": \"UH\"," +
                "\"outgoingDependencies\": []," +
                "\"incomingDependencies\": [" +
                "{" +
                "\"sourceWordId\": 2," +
                "\"dependencyType\": \"DISCOURSE\"}" +
                "]}," +
                "{\"sentenceNo\": 1," +
                "\"id\": 2," +
                "\"text\": \"World\"," +
                "\"lemma\": \"World\"," +
                "\"posTag\": \"NNP\"," +
                "\"outgoingDependencies\": [" +
                "{" +
                "\"targetWordId\": 1," +
                "\"dependencyType\": \"DISCOURSE\"}," +
                "{" +
                "\"targetWordId\": 3," +
                "\"dependencyType\": \"PUNCT\"}" +
                "]," +
                "\"incomingDependencies\": []}" +
                "," +
                "{\"sentenceNo\": 1," +
                "\"id\": 3," +
                "\"text\": \"!\"," +
                "\"lemma\": \"!\"," +
                "\"posTag\": \".\"," +
                "\"outgoingDependencies\": []," +
                "\"incomingDependencies\": [" +
                "{" +
                "\"sourceWordId\": 2," +
                "\"dependencyType\": \"PUNCT\"}" +
                "]}]}]}";
    }
}
