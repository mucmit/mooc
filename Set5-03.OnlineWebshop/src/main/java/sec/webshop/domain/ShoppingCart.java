package sec.webshop.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart implements Serializable {

    private Map<Item, Long> itemCounts;

    public ShoppingCart() {
        this.itemCounts = new TreeMap<>();
    }

    public void addToCart(Item product) {
        itemCounts.putIfAbsent(product, 0L);
        itemCounts.put(product, itemCounts.get(product) + 1);
    }

    public void removeFromCart(Item product) {
        if (!itemCounts.containsKey(product)) {
            return;
        }

        itemCounts.put(product, itemCounts.get(product) - 1);
    }

    public Map<Item, Long> getItems() {
        return itemCounts;
    }

    public double getSum() {
        return itemCounts.keySet().stream()
                .mapToDouble(item -> item.getPrice() * itemCounts.get(item))
                .sum();
    }
}
