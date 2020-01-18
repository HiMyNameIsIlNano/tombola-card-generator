package Card.generator;

import Card.CardConfig;

import java.util.*;
import java.util.stream.Collectors;

public class SortedNumbersPerBucketGenerator implements CardGenerator {

    private static final Random generator = new Random();

    private CardConfig cardConfig;

    @Override
    public Map<Integer, List<Integer>> generate(CardConfig cardConfig) {
        this.cardConfig = cardConfig;
        return generateNumbers();
    }

    private Map<Integer, List<Integer>> generateNumbers() {
        Map<Integer, List<Integer>> result = getAndInitializeMap();
        return randomNumbersPerBucket(result);
    }

    private Map<Integer, List<Integer>> randomNumbersPerBucket(Map<Integer, List<Integer>> emptyMap) {
        Map<Integer, List<Integer>> sortedResult = new HashMap<>();
        int maxValue = cardConfig.getMaxValue();
        Set<Integer> alreadyGeneratedNumbers = new HashSet<>();
        int maxCountOfNumberPerBucket = cardConfig.getMaxCountOfNumberPerBucket();

        List<Integer> randomNumberBuckets;
        for (int i = 0; i < cardConfig.getNumbersPerCard(); i++) {
            int newRandom;
            do {
                newRandom = generator.nextInt(maxValue) + 1;
                int numberBucket = getBucketFromNumber(newRandom);
                randomNumberBuckets = emptyMap.get(numberBucket);
            } while (alreadyGeneratedNumbers.contains(newRandom) || randomNumberBuckets.size() >= maxCountOfNumberPerBucket);

            randomNumberBuckets.add(newRandom);
            alreadyGeneratedNumbers.add(newRandom);
        }

        for (Map.Entry<Integer, List<Integer>> integerListEntry : emptyMap.entrySet()) {
            List<Integer> sortedNumbersPerBucket = integerListEntry.getValue().stream()
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toList());
            sortedResult.put(integerListEntry.getKey(), sortedNumbersPerBucket);
        }

        return sortedResult;
    }

    private Map<Integer, List<Integer>> getAndInitializeMap() {
        Map<Integer, List<Integer>> result = new HashMap<>();
        for (int i = 0; i < cardConfig.getColumnNumber(); i++) {
            result.put(i, new ArrayList<>());
        }
        return result;
    }

    private int getBucketFromNumber(int generatedNumber) {
        return generatedNumber == cardConfig.getMaxValue() ? cardConfig.getColumnNumber() - 1 : generatedNumber / 10;
    }
}
