package Card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Card {

    private CardConfig cardConfig;
    private Map<Integer, List<Integer>> generatedNumbers = new HashMap<>();

    private Card() {
        this.cardConfig = new CardConfig();
    }

    public CardConfig getCardConfig() {
        return cardConfig;
    }

    private void setCardConfig(CardConfig cardConfig) {
        this.cardConfig = cardConfig;
    }

    public Map<Integer, List<Integer>> getGeneratedNumbers() {
        return generatedNumbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(generatedNumbers, card.generatedNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generatedNumbers);
    }

    // Builder
    public static class CardBuilder {

        private Card card;

        private CardBuilder() {
            this.card = new Card();
        }

        public static CardBuilder Builder() {
            return new CardBuilder();
        }

        public CardBuilder withCardConfig(CardConfig seed) {
            card.setCardConfig(seed);
            return this;
        }

        public CardBuilder withGeneratedNumbers(Map<Integer, List<Integer>> data) {
            card.generatedNumbers.putAll(data);
            return this;
        }

        public Card build() {
            return this.card;
        }
    }
}