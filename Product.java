package finalProject;

/*
 * Eric Mueller cs50x student LaunchCode STL spring2016
 * program Product.java for FINAL PROJECT (Inventory Management Program) for course
 */

public class Product {
	// has-a field declarations
	private int vItemID;
	private String vItemName;
	private String vCategoryName;
	private String vDateArrival;
	private int vQuantity;
	private float vCost;
	private float vRetailPrice;
	
	// constructor 1
	public Product(int itemID, String itemName, String category, String date, int quantity, float cost, float price) {
		this.vItemID = itemID;
		this.vItemName = itemName;
		this.vCategoryName = category;
		this.vDateArrival = date;
		this.vQuantity = quantity;
		this.vCost = cost;
		this.vRetailPrice = price;
	}
	// constructor 2
	public Product(String itemName, int quantity, float cost, float price) {
		this.vItemName = itemName;
		this.vCategoryName = "Misc";
		this.vDateArrival = "06/14/2016";
		this.vQuantity = quantity;
		this.vCost = cost;
		this.vRetailPrice = price;
	}
	
	public int getvItemID() {
		return vItemID;
	}

	public void setvItemID(int vItemID) { // use later
		this.vItemID = vItemID;
	}

	public String getvItemName() {
		return vItemName;
	}

	public void setvItemName(String vItemName) {
		this.vItemName = vItemName;
	}

	public String getvCategoryName() {
		return vCategoryName;
	}

	public void setvCategoryName(String vCategoryName) {
		this.vCategoryName = vCategoryName;
	}
	
	public String getvDateArrival() {
		return vDateArrival;
	}
	
	public void setvDateArrival(String vDate) {
		this.vDateArrival = vDate;
	}

	public int getvQuantity() {
		return vQuantity;
	}

	public void setvQuantity(int vQuantity) {
		this.vQuantity = vQuantity;
	}

	public float getvCost() {
		return vCost;
	}

	public void setvCost(float vCost) {
		this.vCost = vCost;
	}

	public float getvRetailPrice() {
		return vRetailPrice;
	}

	public void setvRetailPrice(float vRetailPrice) {
		this.vRetailPrice = vRetailPrice;
	}

	@Override
	public String toString() {
		return "Product [ItemID=" + vItemID + ", ItemName=" + vItemName + ", CategoryName=" + vCategoryName
				+ ", Quantity=" + vQuantity + ", Cost="+ vCost + ", RetailPrice=" + vRetailPrice + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
