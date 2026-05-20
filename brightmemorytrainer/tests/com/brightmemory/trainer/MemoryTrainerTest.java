package com.brightmemory.trainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Unit tests for the MemoryTrainer class.
 */
public class MemoryTrainerTest {

    private MemoryTrainer trainer;

    @BeforeEach
    public void setUp() {
        trainer = new MemoryTrainer(5);
    }

    @Test
    public void testGenerateNewSequence_lengthEqualsLevel() {
        List<String> seq = trainer.generateNewSequence();
        assertEquals(1, seq.size(), "Level 1 sequence should have length 1");
    }

    @Test
    public void testSequenceContainsValidSymbols() {
        List<String> seq = trainer.generateNewSequence();
        List<String> allowed = List.of("SWORD", "SHIELD", "FIRE", "ICE", "LIGHTNING", "DASH");
        for (String s : seq) {
            assertTrue(allowed.contains(s), "Symbol " + s + " should be valid");
        }
    }

    @Test
    public void testValidateSequence_correctInput() {
        List<String> seq = trainer.generateNewSequence();
        assertTrue(trainer.validateSequence(seq), "Exact match should return true");
    }

    @Test
    public void testValidateSequence_wrongInput() {
        trainer.generateNewSequence();
        List<String> wrong = List.of("INVALID");
        assertFalse(trainer.validateSequence(wrong), "Wrong input should return false");
    }

    @Test
    public void testValidateSequence_nullInput() {
        assertFalse(trainer.validateSequence(null), "Null input should return false");
    }

    @Test
    public void testValidateSequence_wrongLength() {
        trainer.generateNewSequence();
        List<String> tooLong = List.of("SWORD", "SHIELD");
        assertFalse(trainer.validateSequence(tooLong), "Wrong length should return false");
    }

    @Test
    public void testProcessAttempt_correct_increasesLevelAndScore() {
        List<String> seq = trainer.generateNewSequence();
        AttemptResult result = trainer.processAttempt(seq);
        assertTrue(result.isCorrect());
        assertEquals(2, result.getNewLevel(), "Level should increase to 2");
        assertEquals(10, result.getScore(), "Score should be 10 (level 1 * 10)");
        assertEquals(1, result.getStreak(), "Streak should be 1");
    }

    @Test
    public void testProcessAttempt_incorrect_resetsStreak() {
        // First correct attempt
        List<String> seq = trainer.generateNewSequence();
        trainer.processAttempt(seq);
        // Now incorrect attempt
        List<String> wrong = List.of("FIRE");
        AttemptResult result = trainer.processAttempt(wrong);
        assertFalse(result.isCorrect());
        assertEquals(0, result.getStreak(), "Streak should reset to 0");
    }

    @Test
    public void testReset() {
        trainer.generateNewSequence();
        trainer.processAttempt(List.of("SWORD")); // Level 1, any correct
        trainer.reset();
        assertEquals(1, trainer.getCurrentLevel());
        assertEquals(0, trainer.getScore());
        assertEquals(0, trainer.getStreak());
    }

    @Test
    public void testLevelCap() {
        MemoryTrainer smallTrainer = new MemoryTrainer(2);
        // Level 1
        List<String> seq1 = smallTrainer.generateNewSequence();
        smallTrainer.processAttempt(seq1);
        // Level 2
        List<String> seq2 = smallTrainer.generateNewSequence();
        smallTrainer.processAttempt(seq2);
        // Should stay at level 2 (max)
        assertEquals(2, smallTrainer.getCurrentLevel());
        List<String> seq3 = smallTrainer.generateNewSequence();
        assertEquals(2, seq3.size(), "Sequence should be capped at max length");
    }
}
