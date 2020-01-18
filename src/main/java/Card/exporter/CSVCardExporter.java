package Card.exporter;

import Card.Card;
import Card.CardConfig;
import Card.converter.MapToStringArrayConverter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVCardExporter implements CardExporter {

    private String fileName;
    private String fileExtension;
    private List<Card> cards;
    private String delimiter;

    public CSVCardExporter(String fileName, String delimiter, List<Card> cards) {
        this.fileName = fileName;
        this.fileExtension = "csv";
        this.delimiter = delimiter;
        this.cards = cards;
    }

    private String getFileName() {
        return fileName + "." + fileExtension;
    }

    private String getDelimiter() {
        return delimiter;
    }

    @Override
    public void export() {
        try {
            writeCsv();
        } catch (IOException e) {
            System.out.println(String.format("Something went wrong with the CSV export: %s", e.getMessage()));
        }
    }

    private void writeCsv() throws IOException {
        for (Card card : cards) {
            FileWriter fw = new FileWriter(this.getFileName(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(toCsv(card));
            out.close();
        }
    }

    private String toCsv(Card card) {
        CardConfig cardConfig = card.getCardConfig();
        MapToStringArrayConverter converter = new MapToStringArrayConverter(cardConfig.getRowNumber(), cardConfig.getColumnNumber());
        return Stream.of(converter.convert(card.getGeneratedNumbers()))
                .map(s -> String.format(s, "%s%02d"))
                .collect(Collectors.joining(getDelimiter()));
    }
}
