package core.basesyntax.sevrice.impl;

import static org.junit.Assert.fail;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.sevrice.CsvFruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvFruitTransactionParserImplTest {
    private static final int FIRST_ELEMENT_INDEX = 0;
    private static final String INCORRECT_INFO = "Balance,pig,-100500";
    private static final String APPLE_BALANCE = "b,apple,100";
    private static final Fruit APPLE = new Fruit("apple");
    private static final FruitTransaction APPLE_BALANCE_OPERATION
            = new FruitTransaction(FruitTransaction.Operation.BALANCE,
            APPLE, 100);
    private static final String FIRST_STRING = "this,must,deleted";
    private final List<FruitTransaction> transactions = new ArrayList<>();
    private final List<String> testList = new ArrayList<>();
    private final CsvFruitTransactionParser csvFruitTransactionParser
            = new CsvFruitTransactionParserImpl();

    public CsvFruitTransactionParserImplTest() {
    }

    @Before
    public void setUp() {
        testList.add(FIRST_STRING);
        transactions.add(APPLE_BALANCE_OPERATION);
    }

    @Test
    public void usedCorrectInfo_OK() {
        testList.add(APPLE_BALANCE);
        List<FruitTransaction> actual = csvFruitTransactionParser.parse(testList);
        List<FruitTransaction> expected = transactions;
        Assert.assertEquals(expected.get(FIRST_ELEMENT_INDEX), actual.get(FIRST_ELEMENT_INDEX));
    }

    @Test
    public void usedIncorrectInfo_NotOk() {
        testList.add(INCORRECT_INFO);
        try {
            csvFruitTransactionParser.parse(testList);
        } catch (RuntimeException e) {
            return;
        }
        fail("You should check on correct input data");
    }

    @Test
    public void nullList_notOk() {
        List<String> nullList = null;
        try {
            csvFruitTransactionParser.parse(nullList);
        } catch (RuntimeException e) {
            return;
        }
        fail("If input data is equal to null Runtime exception must be thrown");
    }

    @After
    public void tearDown() {
        testList.clear();
        transactions.clear();
    }
}
