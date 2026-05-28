package expensetracker;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
class Expense {
	private double amount;
	private String category;
	private LocalDate date;
	
	public Expense(double amount,String category,LocalDate date) {
		this.amount=amount;
		this.category=category;
		this.date=date;
		}
	public double getAmount() {
		return amount;
	}
	public String getCategory() {
		return category;
	}
	public LocalDate getDate() {
		return date;
	}
	public String toString() {
		return amount + " | " + category + " | " + date;	
		}
}
 class ExpenseManager {
    private ArrayList <Expense> expenses = new ArrayList<>();
	Scanner sc = new Scanner(System.in);
	  public void addExpense() {
	 	System.out.println("ENTER DETAILS OF THE EXPENSE");
		System.out.println("ENTER THE AMOUNT: ");
		double amount = sc.nextDouble();
        System.out.print("ENTER THE CATEGORY: ");
        String category = sc.next();
        System.out.print("ENTER THE DATE (yyyy-mm-dd): ");
        String dateInput = sc.next();
        LocalDate date = LocalDate.parse(dateInput);
        // Creating Expense object
        Expense e = new Expense(amount, category, date);
        expenses.add(e);//stored in arraylist
        System.out.println("EXPENSE IS ADDED SUCCESSFULLY!");
     }
	  public void dispExpense() {
		  if(expenses.isEmpty()) {
			  System.out.println("\nNO EXPENSES FOUND");
			  return;
		  }
          System.out.println("\n===== ALL EXPENSES =====");// Displaying all expenses
    for (Expense e : expenses) {
        System.out.println(e);
    }
}
	  public void monthlyReport() {
		  System.out.println("ENTER MONTH(1-12): ");
		  int mon=sc.nextInt();
		  System.out.println("ENTER YEAR: ");
		  int year=sc.nextInt();
		  double total=0;
		  for(Expense e : expenses) {
			  if(e.getDate().getMonthValue() == mon && e.getDate().getYear() == year) {
				  total += e.getAmount();
			  }
		  }
		  System.out.println("TOTAL EXPENSE FOR THE MONTH " + mon +"IN" +year +"=" + total);
	  }
public void highestCategory() {
if(expenses.isEmpty())
{
System.out.println("NO EPENSES FOUND");
return;
}
HashMap<String,Double> categoryTotals = new HashMap<>();
for(Expense e : expenses) {
String category=e.getCategory();
double amount=e.getAmount();
if (categoryTotals.containsKey(category)) {
    categoryTotals.put(category,categoryTotals.get(category) + amount);
} else {
    categoryTotals.put(category, amount);
}
}
String highestCategory = "";
double max = 0;
for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
if (entry.getValue() > max) {
    max = entry.getValue();
    highestCategory = entry.getKey();
}
}
System.out.println("HIGHEST  EXPENSE CATEGORY: " + highestCategory);
System.out.println("TOTAL AMOUNT: " + max);
}
public void saveToFile() {
try {
PrintWriter pw = new PrintWriter(new FileWriter("expenses.txt"));
for (Expense e : expenses) {
    pw.println(e.getAmount() + "," + e.getCategory() + "," +e.getDate());
}
pw.close();
System.out.println("EXPENSES SAVED SUCCESSFULLY!");
} catch (IOException e) {
System.out.println("ERROR SAVING FILE.");
}
}
public void loadFromFile() {
try {
BufferedReader br = new BufferedReader(new FileReader("expenses.txt"));
String line;
while ((line = br.readLine()) != null) {
    String[] data = line.split(",");
    double amount = Double.parseDouble(data[0]);
    String category = data[1];
    LocalDate date =LocalDate.parse(data[2]);
    Expense e = new Expense(amount, category, date);
    expenses.add(e);
}
br.close();
System.out.println("EXPENSES LOADED SUCCESSFULLY!");
} 
catch (IOException e) {
System.out.println("ERROR LOADING FILE.");
}
}
}			 
public class Main {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== EXPENSE TRACKER MENU =====");
            System.out.println("1. Add Expense");
            System.out.println("2. Display Expenses");
            System.out.println("3. Monthly Report");
            System.out.println("4. Highest Category");
            System.out.println("5. Save Expenses");
            System.out.println("6. Load Expenses");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    manager.addExpense();
                    break;
                case 2:
                    manager.dispExpense();
                    break;
                case 3:
                	manager.monthlyReport();
                	break;
                case 4:
                    manager.highestCategory();
                    break;
                case 5:
                    manager.saveToFile();
                    break;
                case 6:
                    manager.loadFromFile();
                    break;
                case 7:
                    System.out.println("Exiting Program...");
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } while (choice != 7);
        sc.close();
    }
}