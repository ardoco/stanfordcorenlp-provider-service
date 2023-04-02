/* Licensed under MIT 2022. */
package stanfordnlp.corenlp;

import org.eclipse.collections.impl.factory.Lists;
import stanfordnlp.corenlp.api.Text;
import edu.stanford.nlp.pipeline.CoreDocument;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import stanfordnlp.corenlp.api.Sentence;
import stanfordnlp.corenlp.api.Word;

class TextImpl implements Text {

    final CoreDocument coreDocument;
    private ImmutableList<Sentence> sentences = Lists.immutable.empty();
    private ImmutableList<Word> words = Lists.immutable.empty();

    TextImpl(CoreDocument coreDocument) {
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

    @Override
    public void setSentences(ImmutableList<Sentence> sentences) {
        this.sentences = sentences;
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
