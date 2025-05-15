import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SILab2Test {

    @Test
    public void testEveryStatement() {
        // Тест случај 1: allItems == null → RuntimeException
        assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, "1234567890123456"));

        // Тест случај 2: item со null име → RuntimeException
        List<Item> items = new ArrayList<>();
        items.add(new Item(null, 1, 100, 0));
        assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, "1234567890123456"));

        // Тест случај 3: item со цена > 300, discount > 0, quantity <= 10 (грана sum -= 30)
        items.clear();
        items.add(new Item("item1", 5, 400, 0.1));
        double result = SILab2.checkCart(items, "1234567890123456");
        assertTrue(result < 400 * 5);

        // Тест случај 4: item со цена <= 300, discount = 0, quantity <= 10
        items.clear();
        items.add(new Item("item2", 3, 200, 0));
        result = SILab2.checkCart(items, "1234567890123456");
        assertEquals(200 * 3, result);

        // Тест случај 5: cardNumber == null или должина != 16 → RuntimeException
        assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, null));
        assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, "123"));

        // Тест случај 6: cardNumber има недозволени карактери → RuntimeException
        assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, "1234abcd5678efgh"));

        // Тест случај 7: валиден cardNumber со цифри
        result = SILab2.checkCart(items, "1234567890123456");
        assertEquals(200 * 3, result);
    }

    @Test
    public void testMultipleCondition() {
        List<Item> items = new ArrayList<>();

        // 1. price <= 300, discount == 0, quantity <= 10 -> услов false
        items.clear();
        items.add(new Item("item", 5, 100, 0));
        double result = SILab2.checkCart(items, "1234567890123456");
        assertEquals(100 * 5, result);

        // 2. price > 300 (true, false, false)
        items.clear();
        items.add(new Item("item", 5, 350, 0));
        result = SILab2.checkCart(items, "1234567890123456");
        assertTrue(result < 350 * 5);

        // 3. discount > 0 (false, true, false)
        items.clear();
        items.add(new Item("item", 5, 100, 0.1));
        result = SILab2.checkCart(items, "1234567890123456");
        assertTrue(result < 100 * 5);

        // 4. quantity > 10 (false, false, true)
        items.clear();
        items.add(new Item("item", 11, 100, 0));
        result = SILab2.checkCart(items, "1234567890123456");
        assertTrue(result < 100 * 11);

        // 5. price > 300 и discount > 0 (true, true, false)
        items.clear();
        items.add(new Item("item", 5, 350, 0.1));
        result = SILab2.checkCart(items, "1234567890123456");
        assertTrue(result < 350 * 5);

        // 6. price > 300 и quantity > 10 (true, false, true)
        items.clear();
        items.add(new Item("item", 11, 350, 0));
        result = SILab2.checkCart(items, "1234567890123456");
        assertTrue(result < 350 * 11);

        // 7. discount > 0 и quantity > 10 (false, true, true)
        items.clear();
        items.add(new Item("item", 11, 100, 0.1));
        result = SILab2.checkCart(items, "1234567890123456");
        assertTrue(result < 100 * 11);

        // 8. price > 300 и discount > 0 и quantity > 10 (true, true, true)
        items.clear();
        items.add(new Item("item", 11, 350, 0.1));
        result = SILab2.checkCart(items, "1234567890123456");
        assertTrue(result < 350 * 11);
    }
}
