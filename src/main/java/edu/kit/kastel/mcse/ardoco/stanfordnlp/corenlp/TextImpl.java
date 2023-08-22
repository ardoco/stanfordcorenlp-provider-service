/* Licensed under MIT 2022. */
package edu.kit.kastel.mcse.ardoco.stanfordnlp.corenlp;

import edu.kit.kastel.mcse.ardoco.core.api.text.Sentence;
import edu.kit.kastel.mcse.ardoco.core.api.text.Text;
import edu.kit.kastel.mcse.ardoco.core.api.text.Word;
import edu.stanford.nlp.pipeline.CoreDocument;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

/**
 * copied from <a href="https://github.com/ArDoCo/Core/blob/783f3388617158b0da0a8f43d6cb8e20041f4a09/text-provider/src/main/java/edu/kit/kastel/mcse/ardoco/core/text/providers/corenlp/TextImpl.java">...</a>
 * since TextImpl was not public and minor adjustments were necessary.
 */
public class TextImpl implements Text {

    final CoreDocument coreDocument;
    private ImmutableList<Sentence> sentences = Lists.immutable.empty();
    private ImmutableList<Word> words = Lists.immutable.empty();

    public TextImpl(CoreDocument coreDocument) {
        this.coreDocument = coreDocument;
    }

    @Override
    public ImmutableList<Word> words() {
        if (words.isEmpty()) {
            iterateDocumentForWordsAndSentences();
        }
        return words;
    }

    @Override
    public ImmutableList<Sentence> getSentences() {
        if (sentences.isEmpty()) {
            iterateDocumentForWordsAndSentences();
        }
        return sentences;
    }

    private void iterateDocumentForWordsAndSentences() {
        MutableList<Sentence> sentenceList = Lists.mutable.empty();
        MutableList<Word> wordList = Lists.mutable.empty();

        var coreSentences = coreDocument.sentences();
        int wordIndex = 0;
        for (int i = 0; i < coreSentences.size(); i++) {
            var coreSentence = coreSentences.get(i);
            var sentence = new SentenceImpl(coreSentence, i, this);
            sentenceList.add(sentence);

            for (var token : coreSentence.tokens()) {
                var word = new WordImpl(token, wordIndex, this);
                wordList.add(word);
                wordIndex++;
            }
        }

        sentences = sentenceList.toImmutable();
        words = wordList.toImmutable();
    }

}
