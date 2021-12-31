package new11;
import java.sql.*;
import java.lang.Math;   
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class First_Ex {
	public static void main(String[] args) {
		try {
			Statement st=null;
			ResultSet rs=null;
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/vikky", "root", "");
			
			System.out.println("Simple CURD/S Operation:");
			Scanner s=new Scanner(System.in);
			System.out.println("Enter Ur Options:");
			String options=s.next();
			st=con.createStatement();
			 List<person> persons = 	new ArrayList();
			String contd;
			int min = 1;  
			int max = 200; 
			PreparedStatement statement =null;
			   switch(options) {
			   case "select":
				   rs=st.executeQuery("select * from data");
					  while(rs.next()) {
					  System.out.println("Person:"+rs.getInt("personID")
					  +"Name:"+rs.getString("Lastname")
					  +"Price:"+rs.getDouble("pprice")
					  +"Quantity:"+rs.getInt("pqty"));
					   }
					  break;
			   case "insert":
				   do {
				   System.out.println("Enter person Details:");
				  
				   System.out.println("Enter person ID:");
					int personID=s.nextInt();
					
					System.out.println("Enter person lastname:");
					String lastname=s.next();
					
					System.out.println("Enter the number of entry concert tickets: ");
					int pqty=s.nextInt();
					
					System.out.println("Enter the ticket price (3500/4500): ");
					double pprice=s.nextInt();
					
				  
					String sql = "INSERT INTO data (personID,Lastname,pprice,pqty) VALUES (?, ?, ?, ?)";
				   statement = con.prepareStatement(sql);
				   statement.setInt(1, personID);
				   statement.setString(2, lastname);
				   statement.setDouble(3, pprice);
				   statement.setInt(4, pqty);
				   int rowsInserted = statement.executeUpdate();
				  
				   person p =new person();
					
					p.setPersonID(personID);
					
					p.setLastname(lastname);
					p.setPprice(pprice);
					
					//p.setFirstname(firstname);
					
					p.setPqty(pqty);
					
					persons.add(p);
				   if (rowsInserted > 0) {
					
				      System.out.println("A new person was inserted successfully!");
				     
				   }
					 
				      System.out.println("Do you want to continue (yes/no)");
				      contd=s.next();
				   
				   }while(contd.equalsIgnoreCase("yes"));
				   for(person p:persons) {
						double finalval=p.getPprice() * p.getPqty();
						
						int b = (int)(Math.random()*(max-min+1)+min); 
						
						System.out.println();
						System.out.println("person entered:"+
						"\n\t Person ID: "+p.getPersonID()
						+"\n\t Person name: "+p.getLastname()
						+"\n\t person fee: "+p.getPqty()
						+"\n\t total cost:"+finalval
						+"\n\t your seat number:"+b);
					}
				   
				   break;
			   case "delete":
				   System.out.println("Enter the admin password:");
				   String pass=s.next();
				   System.out.println(pass);
				   
				   if(pass.equals("admin")) {
				   
				   System.out.println("Enter Person Name For Delete:");
				   String pnam=s.next();
				   String q="delete from data where Lastname=?";
				  statement = con.prepareStatement(q);
				   statement.setString(1,pnam);
				   int rowsDeleted = statement.executeUpdate();
				   if (rowsDeleted > 0) {
				       System.out.println("A product was Deleted successfully!");
				   }
				   }
				   else {
					   System.out.println("Wrong");
				   }
				   break;
			   case "update":
				   System.out.println("Enter the admin password:");
				   String pass1=s.next();
				   
				   if(pass1.equals("admin")) {
				   System.out.println("Enter person Name For Update:");
				   String p2=s.next();
				   System.out.println("Enter tickets For Update:");
				   int p1=s.nextInt();
				   boolean up=st.execute("update data set pqty="+p1+" where Lastname='"+p2+"'");
				   if(!up) {
				   	System.out.println("Updated");
				   	System.out.println("revised price:"+p1*3500);		  
				   	}else {
				   	System.out.println("Not Updated");
				   }
				   }
				   else {
					   System.out.println("Wrong");
				   }
				   break;
				   
			   case "find":
				   System.out.println("Enter Person name to Find:");
				   String pname1=s.next();
				   String q1="select * from data where Lastname=?";
				   statement = con.prepareStatement(q1);
				   statement.setString(1,pname1);
				   ResultSet rs1=statement.executeQuery();
					  if(rs1.next()) {
					  System.out.println("Person:"+rs1.getInt("personID")
					  +"Name:"+rs1.getString("Lastname")
					  +"Price:"+rs1.getDouble("pprice")
					  +"QTY:"+rs1.getInt("pqty"));
					   }
					  break;
			   
			 default:
				 System.out.println("Choose Any One Option");
		
			}
			con.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
