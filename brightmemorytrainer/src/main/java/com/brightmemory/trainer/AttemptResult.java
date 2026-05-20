package com.brightmemory.trainer;

import java.util.Collections;
import java.util.List;

/**
 * Immutable data class representing the outcome of a single attempt
 * in the Bright Memory Trainer.
 */
public class AttemptResult {

    private final boolean correct;
    private final int newLevel;
    private final int score;
    private final int streak;
    private final List<String> correctSequence;

    /**
     * Constructs an AttemptResult.
     *
     * @param correct         whether the user's input was correct
     * @param newLevel        the level after this attempt
     * @param score           the updated score
     * @param streak          the current streak length
     * @param correctSequence the sequence that was expected (unmodifiable)
     */
    public AttemptResult(boolean correct, int newLevel, int score, int streak, List<String> correctSequence) {
        this.correct = correct;
        this.newLevel = newLevel;
        this.score = score;
        this.streak = streak;
        this.correctSequence = correctSequence == null ? List.of() : Collections.unmodifiableList(correctSequence);
    }

    public boolean isCorrect() {
        return correct;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public int getScore() {
        return score;
    }

    public int getStreak() {
        return streak;
    }

    public List<String> getCorrectSequence() {
        return correctSequence;
    }

    @Override
    public String toString() {
        return "AttemptResult{" +
                "correct=" + correct +
                ", newLevel=" + newLevel +
                ", score=" + score +
                ", streak=" + streak +
                ", correctSequence=" + correctSequence +
                '}';
    }
}
