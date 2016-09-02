package finalProject;
/*
 * Eric Mueller cs50x student LaunchCode STL spring2016
 * program ProductMenu.java for FINAL PROJECT (Inventory Management Program) for course
 * 
 */
import java.util.ArrayList;
import java.util.Scanner;

public class ProductMenu {
	private ArrayList<Product> products;
	private Scanner s;
	
	public static void main(String[] args) {
		int x = 0;
		ProductMenu pm = new ProductMenu();
		do {
			x = pm.startMenu();
			pm.processInput(x);
		} while (x != 6);
		//s.close(); not sure why I do not need here/ compiler not like
	}
	
	public ProductMenu() {
		s = new Scanner(System.in);
		//Scanner testS = new Scanner(new FileReader(fileX.txt))
		products = new ArrayList<Product>();
	}
	
	public int startMenu()  {
		System.out.println("*** Welcome to the Inventory Management Application! ***"+ '\n');
		System.out.println("	1. Create a new Product.");
		System.out.println("	2. Display a list of current Products.");
		System.out.println("	3. Get current information of a product.");
		System.out.println("	4. Change the retail price of a product.");
		System.out.println("	5. Get past sales of a product.");
		System.out.println("	6. Exit"+'\n');
		int vSelection = s.nextInt();
		while(vSelection <= 0 || vSelection > 6) {
			System.out.println("Invalid selection, Please try again: ");
		}
		return vSelection;
	}
	
	public void processInput(int vSelection) {
		if (vSelection == 1) //create new product
		{
			createProduct();
		}
		else if (vSelection == 2) // display a list
		{
			displayProducts();
		}
		else if (vSelection == 3) // current info of a product
		{
			displayProducts();
			//System.out.println("Please enter Item Number: ");
			//String iNum = s.next();
			Product p = selectProduct();
			System.out.println("This is the current information on this product: ");
			System.out.println(p.toString());
			System.out.println("Here is the Products status after the move: ");
			System.out.println(p);
			System.out.println(toString());
		}
		else if (vSelection == 4) // change the retail price
		{
			displayProducts();
			Product p = selectProduct();
			System.out.print("Please enter the new retail price:$");
			float newPrice = s.nextFloat();
			System.out.println("");
			while(newPrice < 1.0) {
				System.out.println("Invaalid value! Please enter a price above $1.00: ");
				newPrice = s.nextFloat();
			}
			p.setvRetailPrice(newPrice);
		}
	}

	private void displayProducts() {
		for(int i = 0; i < products.size(); i++)
		{
			System.out.println((i+1) + ".)" + products.get(i));
		}
		
	}

	private void createProduct() {
		System.out.print("Please enter a name for new Product: ");
		String name = s.next();
		System.out.println("");
		System.out.print("Please enter quantity:");
		int quant = s.nextInt();
		System.out.println("");
		while (quant < 1) {
			System.out.print("Invalid value, please enter a positive number: ");
			quant = s.nextInt();
			System.out.println("");
		}
		System.out.print("Please enter wholesale cost per unit: $");
		float cost = s.nextFloat();
		System.out.println("");
		while (cost < 1.00) {
			System.out.println("Invalid value, please enter an amount above $1.00: ");
			cost = s.nextFloat();
		}
		System.out.print("Please enter the retail price per unit: $");
		float price = s.nextFloat();
		System.out.println("");
		while (price < 1.00) {
			System.out.println("Invalid value, please enter an amount above $1.00: ");
			price = s.nextFloat();
		}
		products.add(new Product(name, quant, cost, price));
	}

	private Product selectProduct()
	{
		System.out.println("Please select a Product: ");
		int selection = s.nextInt();
		while(selection < 1 || selection > products.size())
		{
			System.out.println("Invalid choice, please try again: ");
			selection = s.nextInt();
		}
		return products.get(selection - 1);
	}

}
