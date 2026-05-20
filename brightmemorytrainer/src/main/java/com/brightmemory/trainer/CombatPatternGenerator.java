package com.brightmemory.trainer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Generates combat-style pattern sequences that mimic the combo system
 * in Bright Memory. Patterns have thematic names and varying difficulty.
 */
public class CombatPatternGenerator {

    private final Map<String, List<String>> predefinedPatterns;
    private final List<String> patternNames;

    /**
     * Initializes a set of predefined combat patterns with associated symbol sequences.
     */
    public CombatPatternGenerator() {
        predefinedPatterns = new LinkedHashMap<>();
        predefinedPatterns.put("Lightning Strike", List.of("LIGHTNING", "SWORD", "SWORD"));
        predefinedPatterns.put("Frost Barrier", List.of("ICE", "SHIELD", "SHIELD"));
        predefinedPatterns.put("Inferno Rush", List.of("FIRE", "DASH", "SWORD"));
        predefinedPatterns.put("Storm Combo", List.of("LIGHTNING", "FIRE", "DASH", "SWORD"));
        predefinedPatterns.put("Elemental Fury", List.of("FIRE", "ICE", "LIGHTNING", "SWORD", "SHIELD"));
        predefinedPatterns.put("Shadow Step", List.of("DASH", "DASH", "SWORD", "ICE"));
        predefinedPatterns.put("Overload", List.of("LIGHTNING", "LIGHTNING", "FIRE", "FIRE", "DASH"));
        patternNames = new ArrayList<>(predefinedPatterns.keySet());
    }

    /**
     * Returns a random predefined pattern from the library.
     *
     * @return a Map.Entry with pattern name and its symbol sequence
     */
    public Map.Entry<String, List<String>> getRandomPattern() {
        int index = ThreadLocalRandom.current().nextInt(patternNames.size());
        String name = patternNames.get(index);
        return new AbstractMap.SimpleEntry<>(name, new ArrayList<>(predefinedPatterns.get(name)));
    }

    /**
     * Retrieves a specific pattern by name.
     *
     * @param name the pattern name (case-sensitive)
     * @return the sequence of symbols, or empty list if not found
     */
    public List<String> getPattern(String name) {
        return predefinedPatterns.getOrDefault(name, List.of());
    }

    /**
     * Lists all available pattern names.
     *
     * @return unmodifiable list of pattern names
     */
    public List<String> getPatternNames() {
        return Collections.unmodifiableList(patternNames);
    }

    /**
     * Generates a random sequence of symbols of the given length (for custom challenges).
     *
     * @param length desired length (1-20)
     * @return list of randomly chosen symbols
     */
    public List<String> generateRandomSequence(int length) {
        if (length < 1 || length > 20) {
            throw new IllegalArgumentException("Length must be between 1 and 20");
        }
        List<String> symbols = List.of("SWORD", "SHIELD", "FIRE", "ICE", "LIGHTNING", "DASH");
        return ThreadLocalRandom.current()
                .ints(length, 0, symbols.size())
                .mapToObj(symbols::get)
                .collect(Collectors.toList());
    }
}
