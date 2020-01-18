package Card;

public class CardConfig {

    private int rowNumber;
    private int columnNumber;
    private int numbersPerCard;
    private int maxValue;

    CardConfig() {
    }

    public CardConfig(int rowNumber, int columnNumber, int numbersPerCard, int maxValue) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.numbersPerCard = numbersPerCard;
        this.maxValue = maxValue;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getNumbersPerCard() {
        return numbersPerCard;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMaxCountOfNumberPerBucket() {
        return rowNumber;
    }

}
