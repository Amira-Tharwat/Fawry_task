import java.util.List;
import java.util.ArrayList;

public class ShippingService {
    public void shipItems(List<Shippable> items) {
        System.out.println("\n** Shipment notice **");
        double totalWeight = 0.0;


        List<String> names = new ArrayList<>();

        for (Shippable item : items) {

            if (names.contains(item.getName())) {
                continue;
            }


            int quantity = 0;
            double itemWeight = 0;
            for (Shippable i : items) {
                if (i.getName().equals(item.getName())) {
                    quantity++;
                    itemWeight = i.getWeight();
                }
            }

            double itemTotalWeight = itemWeight * quantity;
            System.out.println(quantity + "x " + item.getName() + " " + itemWeight + "g");
            totalWeight += itemTotalWeight;

            names.add(item.getName());
        }

        System.out.println("Total package weight " + (totalWeight / 1000) + "kg");
    }
}