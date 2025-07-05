import java.util.ArrayList;
import java.util.List;
public class Cart {

    public ArrayList<CartProduct> items = new ArrayList<>();
    public  void Add(Product product,int quantity ){
        items.add(new CartProduct(product, quantity));
    }
    public ArrayList<CartProduct> getItems() {
        return items;
    }

    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (CartProduct item : items) {
            subtotal += item.product.Price * item.quantity;
        }
        return subtotal;
    }
    public boolean isEmpty() {
        return items.isEmpty();
    }

}
