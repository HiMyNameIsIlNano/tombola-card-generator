package Card.generator;

import Card.CardConfig;

import java.util.*;

public class UnsortedNumbersPerBucketGenerator implements CardGenerator {

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
        Map<Integer, List<Integer>> result = new HashMap<>();
        int maxValue = cardConfig.getMaxValue();
        Set<Integer> alreadyGeneratedNumbers = new HashSet<>();
        int maxCountOfNumberPerBucket = cardConfig.getMaxCountOfNumberPerBucket();

        List<Integer> randomNumbersPerBucket;
        for (int i = 0; i < cardConfig.getNumbersPerCard(); i++) {
            int newRandom;
            do {
                newRandom = generator.nextInt(maxValue) + 1;
                int bucketId = getBucketFromNumber(newRandom);
                randomNumbersPerBucket = emptyMap.get(bucketId);
            } while (alreadyGeneratedNumbers.contains(newRandom) || randomNumbersPerBucket.size() >= maxCountOfNumberPerBucket);

            randomNumbersPerBucket.add(newRandom);
            alreadyGeneratedNumbers.add(newRandom);
        }

        for (Map.Entry<Integer, List<Integer>> integerListEntry : emptyMap.entrySet()) {
            result.put(integerListEntry.getKey(), integerListEntry.getValue());
        }

        return result;
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
