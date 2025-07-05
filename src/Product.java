public class Product implements Shippable {
    public String Name;
    public double Price;
    public double Quantity;
    public boolean Expired;
    public boolean RequireShipping;
    public double Weight;
   public  Product(String name,double price,double quantity ,boolean isExpire,boolean requireShipping ,double weight){
       this.Name=name;
       this.Price=price;
       this.Quantity=quantity;
       this.Expired=isExpire;
       this.RequireShipping=requireShipping;
       this.Weight=weight;
   }
    @Override
    public String getName() {
        return Name;
    }

    @Override
    public double getWeight() {
        if (!RequireShipping) {
            throw new UnsupportedOperationException("This product is not shippable");
        }
        return Weight;
    }
}
