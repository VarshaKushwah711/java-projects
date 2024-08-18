package Project3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DMart {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
    
        try
        {
            System.out.println("Enter the total purchase amount: ");
            double Amount = sc.nextDouble();
            double finalAmount = Amount;
            if(Amount >= 3000 && Amount < 5000)
            {
                System.out.println("Congratulations!! You get a flat Rs.500 discount");
                finalAmount = Amount-500;
            }
            else if(Amount >= 5000 && Amount < 10000)
            {
                System.out.println("Congratulations!! You get a 30% discount on your total bill");
                finalAmount = Amount - (Amount*0.30);            
            }
            else if(Amount >= 10000 && Amount < 15000)
            {
                System.out.println("Congratulations!! You get a free mixer");
            }
            else if (Amount >= 15000)
            {
                System.out.println("Congratulations!! You get a free suitcase");
            }
            else
            {
                System.out.println("No discount or rewards applicable!!");
            }
            // Display the final amount after applying any discount
            System.out.printf("The final amount to be paid is: Rs. "+ finalAmount);
        }
        catch(InputMismatchException e)
        {
            //System.out.println(e);
            System.out.println("Invalid input. Please enter a numeric value.");
        }
        finally{
            sc.close();
        }
    } 
}
