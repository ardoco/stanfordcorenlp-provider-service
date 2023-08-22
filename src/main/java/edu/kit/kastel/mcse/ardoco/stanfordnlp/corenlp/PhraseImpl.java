/* Licensed under MIT 2022. */
package edu.kit.kastel.mcse.ardoco.stanfordnlp.corenlp;

import edu.kit.kastel.mcse.ardoco.core.api.text.Phrase;
import edu.kit.kastel.mcse.ardoco.core.api.text.PhraseType;
import edu.kit.kastel.mcse.ardoco.core.api.text.Word;
import edu.stanford.nlp.trees.Tree;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;

import java.util.List;
import java.util.Objects;

/**
 * copied from <a href="https://github.com/ArDoCo/Core/blob/783f3388617158b0da0a8f43d6cb8e20041f4a09/text-provider/src/main/java/edu/kit/kastel/mcse/ardoco/core/text/providers/corenlp/PhraseImpl.java">...</a>
 * since TextImpl was not public and minor adjustments were necessary.
 */
public class PhraseImpl implements Phrase {
    private final Tree tree;
    private final ImmutableList<Word> words;

    private final SentenceImpl parent;

    private String text = null;

    public PhraseImpl(Tree tree, ImmutableList<Word> words, SentenceImpl parent) {
        this.tree = tree;
        this.words = words;
        this.parent = parent;
    }

    @Override
    public int getSentenceNo() {
        return words.get(0).getSentenceNo();
    }

    @Override
    public String getText() {
        if (text == null) {
            text = tree.spanString();
        }
        return text;
    }

    @Override
    public PhraseType getPhraseType() {
        String type = tree.label().toString();
        return PhraseType.get(type);
    }

    @Override
    public ImmutableList<Word> getContainedWords() {
        return words;
    }

    @Override
    public ImmutableList<Phrase> getSubPhrases() {
        MutableList<Phrase> subPhrases = Lists.mutable.empty();
        for (var subTree : tree) {
            if (subTree.isPhrasal() && tree.dominates(subTree)) {
                ImmutableList<Word> wordsForPhrase = Lists.immutable.withAll(parent.getWordsForPhrase(subTree));
                PhraseImpl currPhrase = new PhraseImpl(subTree, wordsForPhrase, parent);
                subPhrases.add(currPhrase);
            }
        }
        return subPhrases.toImmutable();
    }

    /**
     * Returns only those subphrases that are a direct child of this parent phrase.
     * @return  the child phrases
     */
    public ImmutableList<Phrase> getChildPhrases() {
        List<Tree> childTrees = tree.getChildrenAsList();
        List<PhraseImpl> childPhrases = childTrees.stream()
                .filter(Tree::isPhrasal).map(x -> new PhraseImpl(x, Lists.immutable.withAll(parent.getWordsForPhrase(x)), parent)).toList();
        return Lists.immutable.withAll(childPhrases);
    }

    @Override
    public boolean isSuperPhraseOf(Phrase other) {
        if (other instanceof PhraseImpl otherPhrase) {
            return tree.dominates(otherPhrase.tree);
        } else {
            var currText = getText();
            var otherText = other.getText();
            return currText.contains(otherText) && currText.length() != otherText.length();
        }
    }

    @Override
    public boolean isSubPhraseOf(Phrase other) {
        if (other instanceof PhraseImpl otherPhrase) {
            return otherPhrase.tree.dominates(this.tree);
        } else {
            var currText = getText();
            var otherText = other.getText();
            return otherText.contains(currText) && currText.length() != otherText.length();
        }
    }

    @Override
    public ImmutableMap<Word, Integer> getPhraseVector() {
        MutableMap<Word, Integer> phraseVector = Maps.mutable.empty();

        var grouped = getContainedWords().groupBy(Word::getText).toMap();
        grouped.forEach((key, value) -> phraseVector.put(value.getAny(), value.size()));

        return phraseVector.toImmutable();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSentenceNo(), this.getText(), this.getPhraseType(), this.getContainedWords().get(0).getPosition());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Phrase other = (Phrase) obj;
        return Objects.equals(this.getSentenceNo(), other.getSentenceNo()) && Objects.equals(this.getText(), other.getText()) && Objects.equals(this
                .getPhraseType(), other.getPhraseType()) && Objects.equals(this.getContainedWords().get(0).getPosition(), other.getContainedWords()
                        .get(0)
                        .getPosition());
    }

    @Override
    public String toString() {
        return "Phrase{" + "text='" + getText() + '\'' + '}';
    }
}
