package stanfordnlp;

import edu.kit.kastel.mcse.ardoco.core.api.text.DependencyTag;
import io.github.ardoco.textproviderjson.dto.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TestUtil {

    private TestUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static TextDTO getExpectedDto() throws IOException {
        WordDTO word1 = new WordDTO();
        word1.setId(1);
        word1.setSentenceNo(1);
        word1.setLemma("this");
        word1.setText("This");
        word1.setPosTag(PosTag.forValue("DT"));
        IncomingDependencyDTO incomingDependency1 = new IncomingDependencyDTO();
        incomingDependency1.setDependencyTag(DependencyTag.NSUBJ);
        incomingDependency1.setSourceWordId(3);
        word1.setIncomingDependencies(new ArrayList<>(List.of(incomingDependency1)));

        WordDTO word2 = new WordDTO();
        word2.setId(2);
        word2.setSentenceNo(1);
        word2.setLemma("be");
        word2.setText("is");
        word2.setPosTag(PosTag.forValue("VBZ"));
        IncomingDependencyDTO incomingDependency2 = new IncomingDependencyDTO();
        incomingDependency2.setDependencyTag(DependencyTag.COP);
        incomingDependency2.setSourceWordId(3);
        word2.setIncomingDependencies(new ArrayList<>(List.of(incomingDependency2)));

        WordDTO word3 = new WordDTO();
        word3.setId(3);
        word3.setSentenceNo(1);
        word3.setLemma("Marie");
        word3.setText("Marie");
        word3.setPosTag(PosTag.forValue("NNP"));
        OutgoingDependencyDTO outgoingDependencyDTO1 = new OutgoingDependencyDTO();
        outgoingDependencyDTO1.setTargetWordId(1);
        outgoingDependencyDTO1.setDependencyTag(DependencyTag.NSUBJ);
        OutgoingDependencyDTO outgoingDependencyDTO2 = new OutgoingDependencyDTO();
        outgoingDependencyDTO2.setTargetWordId(2);
        outgoingDependencyDTO2.setDependencyTag(DependencyTag.COP);
        OutgoingDependencyDTO outgoingDependencyDTO3 = new OutgoingDependencyDTO();
        outgoingDependencyDTO3.setTargetWordId(4);
        outgoingDependencyDTO3.setDependencyTag(DependencyTag.PUNCT);
        word3.setOutgoingDependencies(new ArrayList<>(List.of(outgoingDependencyDTO1, outgoingDependencyDTO2, outgoingDependencyDTO3)));

        WordDTO word4 = new WordDTO();
        word4.setId(4);
        word4.setSentenceNo(1);
        word4.setLemma(".");
        word4.setText(".");
        word4.setPosTag(PosTag.forValue("."));
        IncomingDependencyDTO incomingDependency3 = new IncomingDependencyDTO();
        incomingDependency3.setDependencyTag(DependencyTag.PUNCT);
        incomingDependency3.setSourceWordId(3);
        word4.setIncomingDependencies(new ArrayList<>(List.of(incomingDependency3)));

        List<WordDTO> words = new ArrayList<>(List.of(word1, word2, word3, word4));

        SentenceDTO sentence1 = new SentenceDTO();
        sentence1.setSentenceNo(1);
        sentence1.setText("This is Marie.");
        sentence1.setConstituencyTree("(ROOT (S (NP (DT This)) (VP (VBZ is) (NP (NNP Marie))) (. .)))");
        sentence1.setWords(words);

        List<SentenceDTO> sentences = new ArrayList<>();
        sentences.add(sentence1);

        TextDTO text = new TextDTO();
        text.setSentences(sentences);

        return text;
    }
}
