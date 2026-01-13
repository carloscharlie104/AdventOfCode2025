package software.aoc.day02.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;
import software.aoc.day02.a.model.ProductRange;
import software.aoc.day02.a.service.IdValidator;
import software.aoc.day02.a.service.InventoryService;
import software.aoc.day02.a.parser.RangeParser;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day02ATest {

    private final IdValidator validator = new IdValidator();
    private final InventoryService service = new InventoryService(validator);

    @Test
    @DisplayName("Logic: Should identify repeating patterns correctly")
    void testValidatorLogic() {
        assertTrue(validator.isInvalidId(new BigInteger("11")));
        assertTrue(validator.isInvalidId(new BigInteger("22")));
        assertTrue(validator.isInvalidId(new BigInteger("123123")));
        assertTrue(validator.isInvalidId(new BigInteger("446446")));

        assertFalse(validator.isInvalidId(new BigInteger("121")));
        assertFalse(validator.isInvalidId(new BigInteger("10")));
        assertFalse(validator.isInvalidId(new BigInteger("123124")));
    }

    @Test
    @DisplayName("Range Parsing: Should parse start and end correctly")
    void testRangeParsing() {
        ProductRange range = new RangeParser().parse("1188511880-1188511890");
        assertEquals(new BigInteger("1188511880"), range.start());
        assertEquals(new BigInteger("1188511890"), range.end());
    }

    @Test
    @DisplayName("Integration: Should solve the example provided in description")
    void testExampleScenario() {
        String exampleInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
                "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
                "824824821-824824827,2121212118-2121212124";

        BigInteger result = service.calculateTotalInvalidIds(exampleInput);

        assertEquals(new BigInteger("1227775554"), result);
    }

    @Test
    void solvePuzzleDay02() {
        InputReader reader = new InputReader();

        try {
            List<String> lines = reader.readLines("src/test/resources/day02/day02FinalTest.txt");
            String fullInput = String.join(",", lines);

            BigInteger solution = service.calculateTotalInvalidIds(fullInput);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 02-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            System.out.println("Test skipped: Input file not found");
        }
    }
}
