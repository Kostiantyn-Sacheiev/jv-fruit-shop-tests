package core.basesyntax.sevrice.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.sevrice.CsvFruitTransactionParser;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFruitTransactionParserImpl implements CsvFruitTransactionParser {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final String DELIMITER = ",";

    @Override
    public List<FruitTransaction> parse(List<String> dataFromFile) {
        if (dataFromFile == null) {
            throw new RuntimeException("Input data be null");
        }
        return dataFromFile.stream()
                .skip(1)
                .map(e -> e.split(DELIMITER))
                .map(this::createTransaction)
                .collect(Collectors.toList());
    }

    private FruitTransaction createTransaction(String[] splitedString) {
        FruitTransaction transaction;
        try {
            transaction = new FruitTransaction(
                    FruitTransaction.Operation.getByOperation(splitedString[OPERATION_INDEX]),
                    new Fruit(splitedString[FRUIT_INDEX]),
                    Integer.parseInt(splitedString[AMOUNT_INDEX]));
        } catch (RuntimeException e) {
            throw new RuntimeException("Data from file not correct for this operation", e);
        }
        return transaction;
    }
}