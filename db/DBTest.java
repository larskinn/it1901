import java.sql.*;

public class DBTest
{
    public static void main(String args[])
    {
        DataAPI dataapi = new DataAPI();
        
            //String username = "gr4pizza";
            //String password = "pizza";
            //String url = "jdbc:mysql://10.0.0.42/gr4pizza";
            
            
            String username = "davidmy_gr4pizza";
            String password = "pizza";
            String url = "jdbc:mysql://mysql.stud.ntnu.no/davidmy_gr4pizza";
            
        dataapi.open(username, password, url);
        
        Customer david;
        Address addr;
        
        addr = dataapi.getAddress(1);
        david = addr.getCustomer();
        
        System.out.println("From "+url+":");
        System.out.println("Addr: "+addr.getAddressLine());
        System.out.println("PC: "+addr.getPostalCode());
        System.out.println("Name: "+david.getName());
        System.out.println("Phone: "+david.getPhone());
        
        dataapi.close();
    }
}
