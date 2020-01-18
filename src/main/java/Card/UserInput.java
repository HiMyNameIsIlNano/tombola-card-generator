package Card;

public class UserInput {

    private int cardsAmount;
    private String fileName;

    public UserInput(int cardsAmount, String fileName) {
        this.cardsAmount = cardsAmount;
        this.fileName = fileName;
    }

    public int getCardsAmount() {
        return cardsAmount;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return "UserInput{cardsAmount=" + cardsAmount + ", fileName='" + fileName + '\'' + '}';
    }
}
