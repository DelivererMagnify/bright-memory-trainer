package com.brightmemory.trainer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Core memory training engine that simulates pattern recall challenges
 * inspired by the combat sequence memorization in Bright Memory.
 * 
 * The trainer generates sequences of directional inputs (like combo attacks)
 * and measures the user's ability to recall and repeat them accurately.
 */
public class MemoryTrainer {

    private final int maxSequenceLength;
    private final List<String> availableSymbols;
    private List<String> currentSequence;
    private int currentLevel;
    private int score;
    private int streak;

    /**
     * Creates a new memory trainer with default symbols (inspired by attack patterns).
     *
     * @param maxSequenceLength the maximum length a sequence can reach (e.g., 12)
     */
    public MemoryTrainer(int maxSequenceLength) {
        this.maxSequenceLength = maxSequenceLength;
        this.availableSymbols = List.of("SWORD", "SHIELD", "FIRE", "ICE", "LIGHTNING", "DASH");
        this.currentSequence = new ArrayList<>();
        this.currentLevel = 1;
        this.score = 0;
        this.streak = 0;
    }

    /**
     * Generates and returns a new sequence for the current level.
     * The sequence length equals the current level (capped at maxSequenceLength).
     *
     * @return list of symbols representing the pattern to memorize
     */
    public List<String> generateNewSequence() {
        int length = Math.min(currentLevel, maxSequenceLength);
        currentSequence.clear();
        for (int i = 0; i < length; i++) {
            int index = ThreadLocalRandom.current().nextInt(availableSymbols.size());
            currentSequence.add(availableSymbols.get(index));
        }
        return Collections.unmodifiableList(currentSequence);
    }

    /**
     * Validates the user's input against the current sequence.
     *
     * @param userInput list of symbols provided by the user
     * @return true if the input matches the sequence exactly
     */
    public boolean validateSequence(List<String> userInput) {
        if (userInput == null || userInput.size() != currentSequence.size()) {
            return false;
        }
        return userInput.equals(currentSequence);
    }

    /**
     * Processes a user attempt, updates score and streak, and advances level if correct.
     *
     * @param userInput the user's attempted sequence
     * @return result object with feedback and updated state
     */
    public AttemptResult processAttempt(List<String> userInput) {
        boolean correct = validateSequence(userInput);
        if (correct) {
            score += currentLevel * 10;
            streak++;
            if (currentLevel < maxSequenceLength) {
                currentLevel++;
            }
        } else {
            streak = 0;
        }
        return new AttemptResult(correct, currentLevel, score, streak, currentSequence);
    }

    /**
     * Resets the trainer to level 1 with zero score and streak.
     */
    public void reset() {
        currentLevel = 1;
        score = 0;
        streak = 0;
        currentSequence.clear();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getScore() {
        return score;
    }

    public int getStreak() {
        return streak;
    }

    public List<String> getCurrentSequence() {
        return Collections.unmodifiableList(currentSequence);
    }
}
