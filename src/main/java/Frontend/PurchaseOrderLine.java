package Frontend;

public class PurchaseOrderLine {
    private int partNo;
    private int qtyOrdered;
    private double priceOrdered;

    // Constructor
    public PurchaseOrderLine(int partNo, int qtyOrdered, double priceOrdered) {
        this.partNo = partNo;
        this.qtyOrdered = qtyOrdered;
        this.priceOrdered = priceOrdered;
    }

    // Getter methods
    public int getPartNo() {
        return partNo;
    }

    public int getQtyOrdered() {
        return qtyOrdered;
    }

    public double getPriceOrdered() {
        return priceOrdered;
    }

    // Setter methods (optional, if you need to modify values)
    public void setPartNo(int partNo) {
        this.partNo = partNo;
    }

    public void setQtyOrdered(int qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public void setPriceOrdered(double priceOrdered) {
        this.priceOrdered = priceOrdered;
    }

    // Optional: Override toString for better output (helpful for debugging)
    @Override
    public String toString() {
        return "Part No: " + partNo + ", Quantity Ordered: " + qtyOrdered + ", Price Ordered: " + priceOrdered;
    }
}


