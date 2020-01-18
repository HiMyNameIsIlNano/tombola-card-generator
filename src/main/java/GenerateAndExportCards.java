import Card.Card;
import Card.CardConfig;
import Card.UserInput;
import Card.exporter.PDFCardsExporter;
import Card.generator.UnsortedNumbersPerBucketGenerator;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;

public class GenerateAndExportCards {
    public static void main(String[] args) {
        UserInput input = processUserInput(args);
        try {
            List<Card> cards = generateUniqueCards(input);
            exportCardsToFile(cards, input.getFileName());
        } catch (IllegalFormatException e) {
            System.out.println(String.format("Something went wrong when reading the input parameters: %s", e.getMessage()));
        }
    }

    private static List<Card> generateUniqueCards(UserInput input) {
        CardConfig cardConfig = new CardConfig(3, 9, 15, 90);
        List<Card> cards = new ArrayList<Card>();

        for (int generatedCardCount = 0; generatedCardCount < input.getCardsAmount(); generatedCardCount++) {
            Map<Integer, List<Integer>> generatedNumbers = generateNumbers(cardConfig);
            Card card = createCard(cardConfig, generatedNumbers);
            generatedCardCount += checkIfUniqueAndUpdateGeneratedCount(cards, card);
        }

        return cards;
    }

    private static UserInput processUserInput(String[] args) {
        if (args.length == 1) {
            return new UserInput(Integer.parseInt(args[0]), "default");
        } else if (args.length == 2) {
            return new UserInput(Integer.parseInt(args[0]), args[1]);
        } else {
            UserInput defaultInput = new UserInput(100, "default");
            System.out.println(String.format("Number of input values %d. Using default configuration %s.", args.length, defaultInput.toString()));
            return defaultInput;
        }
    }

    private static void exportCardsToFile(List<Card> cards, String fileName) {
        new PDFCardsExporter(fileName, cards).export();
    }

    private static int checkIfUniqueAndUpdateGeneratedCount(List<Card> generatedCards, Card card) {
        if (checkIfUnique(generatedCards, card)) {
            generatedCards.add(card);
            return 1;
        } else {
            return -1;
        }
    }

    private static boolean checkIfUnique(List<Card> cards, Card card) {
        return !cards.contains(card);
    }

    private static Card createCard(CardConfig cardConfig, Map<Integer, List<Integer>> generatedNumbers) {
        return Card.CardBuilder.Builder()
                .withCardConfig(cardConfig)
                .withGeneratedNumbers(generatedNumbers)
                .build();
    }

    private static Map<Integer, List<Integer>> generateNumbers(CardConfig cardConfig) {
        return new UnsortedNumbersPerBucketGenerator().generate(cardConfig);
    }
}