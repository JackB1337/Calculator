import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL_DB 
{	
	static final String DB_URL = "jdbc:mysql://localhost/personphone"; // personphone - ��� �������� ��
	static final String USER   = "root";                               // ����� ���� � PHPmaneger (��� ������ ��������� ��)
	static final String PASS   = "root";                             // ������ ���� � PHPmaneger (������ � ��)

	static final String TBL_PERSON = "person";                         // � ���� ���� ����� �� ������������
	static final String TBL_PHONE  = "phone";  
	static final String TBL_TYPE  = "type"; 

	Connection conn = null;        
	Statement     s = null;
	Statement    s1 = null;
	Statement    s2 = null;
	Statement    s3 = null;

	public MySQL_DB()                                                  // ����������� ������ � ��� ����� ���������� ����������� � �� MySQL
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");                    // ���������� �������
			conn = DriverManager.getConnection(DB_URL, USER, PASS);    // ��� ������� ��, �����, ������
			s  = conn.createStatement();
			s1 = conn.createStatement();
			s2 = conn.createStatement();
		} 
		catch (ClassNotFoundException | SQLException e1) {e1.printStackTrace();}
		System.out.println("connection with MySQL completed");              
	}

	public void close()                                                 // ������� �������� ��������� ��� ���������� � ��
	{
		try 
		{
			s.close();
			conn.close();
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
		finally
		{
			s    = null;
			conn = null;
		}	
	}


	/**************************************** �������� *******************************************/
	public void create(Person p)                           // � ����� create �������� ������� 
	{
		String sql = "INSERT INTO person (person_ID, FName, LName, Age) VALUES("+p.person_ID+",'"+p.FName+"','"+p.LName+"', "+p.Age+")";   // ��������� ������� '' ��� mySQl ���� ����� �� ������ �������� ��������! � � �2 ����� ���������!!!
		try 
		{                                                      
			s.executeUpdate(sql);                             
		}catch(Exception ex){ex.printStackTrace();}System.out.println("� DB MySQL ������� ��������� ������ Create: " +sql);  // �������������� ����� �� ������� �� ������������! �� �����������, �� ����=)


		String[] phone_numbers = p.Phone.trim().split(",");                 // ������� ������ �������, ��������� � �������
		if(phone_numbers.length == 1)                                       // ���� ������ ���� ����� ��������                                 
		{
			String sql1 = "INSERT INTO `phone`(`phone_ID`, `person_ID`, `phones`) VALUES ("+null+","+p.person_ID+","+phone_numbers[0]+")";   /// "+p.Phone+"
			try 
			{                                                      
				s.executeUpdate(sql1);                             
			} catch(Exception ex){ex.printStackTrace();}System.out.println("� DB MySQL ������� ��������� ������ Create: " +sql1);  // �������������� ����� �� ������� �� ������������! �� �����������, �� ����=)	
		}
		else
		{
			for (int i = 0; i < phone_numbers.length; i++)                 // ���� ������� ����� �������         
			{  
				String sql1 = "INSERT INTO `phone`(`phone_ID`, `person_ID`, `phones`) VALUES ("+null+","+p.person_ID+","+phone_numbers[i]+")";
				try 
				{
					s.executeUpdate(sql1);
				} catch (SQLException e) {e.printStackTrace();} 
			}
		}
		String sql2 = "INSERT INTO type (phone_ID,types) VALUES("+p.Phone+","+p.Type+")";
		try 
		{                                                      
			s.executeUpdate(sql2);                             
		}catch(Exception ex){ex.printStackTrace();}System.out.println("� DB MySQL ������� ��������� ������ Create: " +sql);  // �������������� ����� �� ������� �� ������������! �� �����������, �� ����=)

	}

	/**************************************** �������� *******************************************/
	public void update(Person p )                          // �������� �������
	{
	String[] phone_numbers = p.Phone.trim().split("\\s+");       // ������� ������ �������, ��������� � �������, ��������� �� ��������
		
	//System.out.println(phone_numbers[0] +" "+ phone_numbers[1] );
	
		try
		{                                                     
			s.execute("UPDATE person SET FName = '"+p.FName+"', LName = '"+p.LName+"', Age = "+p.Age+" WHERE person_ID="+p.person_ID+";");                             
		}catch(Exception ex){ex.printStackTrace();}//System.out.println("�  DB MySQL ������� ��������� ������ Update: " +sql);		
		
		
		String sql2 = "DELETE FROM phone WHERE person_ID = "+p.person_ID;    // � BD MySQL ������� ���������� ID,� � �2 ���������� ID_Person, �����������!
		try
		{                                                    
			s1.execute(sql2);                 
		}catch(Exception ex){ex.printStackTrace();}
		
		if(phone_numbers.length == 1)                           // �������� �� ���� ������� � �������
		{	
			System.out.println(phone_numbers[0]);
			try
			{                                                    
				s2.execute("INSERT INTO `phone`(`phone_ID`, `person_ID`, `phones`) VALUES (null,"+p.person_ID+","+phone_numbers[0]+")");   
			}catch(Exception ex){ex.printStackTrace();}			
		}
		
		else
		{			
			for (int i = 0; i < phone_numbers.length; i++)      // ���� ������� ����� �������         
			{
				String sql3 = "INSERT INTO `phone`(`phone_ID`, `person_ID`, `phones`) VALUES (null,"+p.person_ID+","+phone_numbers[i]+")";
				try 
				{
					s2.execute(sql3);
				} 
				catch (SQLException e) {e.printStackTrace();} 
			}
		}
	}



	/**************************************** �������� *******************************************/
	public void delete(Person p)                           
	{
		String sql = "DELETE FROM person WHERE person_ID = "+p.person_ID;    // � BD MySQL ������� ���������� person_ID,� � �2 ���������� ID_Person, �����������!
		try
		{                                                    
			s.execute(sql);                 
		}catch(Exception ex){ex.printStackTrace();}System.out.println("� DB MySQL ������� ��������� ������ Delete: " +sql);	

		String sql1 = "DELETE FROM phone WHERE person_ID = "+p.person_ID;   
		try
		{                       
			s.execute(sql1);              // ����� ��� ������������ ������ execute      
		}catch(Exception ex){ex.printStackTrace();}System.out.println("� DB MySQL ������� ��������� ������ Delete: " +sql);	
		String sql2 = "DELETE FROM type WHERE phone_ID = "+p.Phone;   
		try
		{                       
			s.execute(sql2);              // ����� ��� ������������ ������ execute      
		}catch(Exception ex){ex.printStackTrace();}System.out.println("� DB MySQL ������� ��������� ������ Delete: " +sql);	
	
	}

	/**************************************** �������� *******************************************/
	public String read()                                    // ����� read ���������� ������ html �����
	{
		String str = "<table border="+"1"+" bgcolor=aqua>"; // ????????????????? bgcolor=aqua> �������!!!
		str += "<thead>";
		str += "<tr>";
		str += "<th>person_ID</th> <th>First Name</th> <th>Last Name</th> <th>Age</th> <th>Phone</th> <th>Type</th> ";
		str += "</tr>";
		str += "</thead>";
		str += "</tbody>";
		str += "";
		str += "";

		try 
		{	
			s.execute("SELECT * FROM `person`;");            // �������� ������� � �� !personphone! � �������  !person!     
			ResultSet rs = s.getResultSet();                           // �������� ������ �� ������� � ���������� rs 

			while (rs.next())                                // ���� � �� ���� ������ ������� �� � ����� � ���������� str
			{
				str += "<tr>"; 

				str += "<td>"+ Integer.toString(rs.getInt(1))+"</td>";                 
				str += "<td>"+ rs.getString(2)+"</td>";    
				str += "<td>"+ rs.getString(3)+"</td>";    
				str += "<td>"+ Integer.toString(rs.getInt(4))+"</td>";    


				String count_numbers = "";   // ���������� ��� �������� ������� � ����� �������
				String type = "";

				s1.execute("SELECT COUNT(*) FROM `phone` WHERE  person_ID = "+rs.getInt(1)+";");  // ������ �� ������� ���������� ������� � ������� 
				ResultSet rs1 = s1.getResultSet();
				while (rs1.next()) 
				{
					count_numbers +="("+rs1.getInt(1)+")"+" ";
				}

				s2.execute("SELECT phones FROM phone WHERE person_ID = "+rs.getInt(1)+";");    // ����� ������� ��������� �� ������� phone 
				ResultSet rs2 = s2.getResultSet();
				while (rs2.next()) 
				{
					count_numbers += Integer.toString(rs2.getInt(1))+" ";
				}
				s3.execute("SELECT * FROM `type` WHERE  phone = "+rs.getInt(1)+";");  // ������ �� ������� ���������� ������� � ������� 
				ResultSet rs3 = s3.getResultSet();
				while (rs3.next()) 
				{
					type +="("+rs3.getString(2)+")"+" ";
				}

				str += "<td>"+count_numbers+"</td>";
				str += "</tr>"; 
			}
			str += "</tbody>";
			str += "</table>";
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return str;                                  // ���������� ������ html �����
	}
}




/* � ������ read  ���� ��� ��� ������ � XML ����
 * 				str += Integer.toString(p.id)+","+p.fname+","+p.lname+","+Integer.toString(p.age)+" : ";	

				 for creating the xml file
									try {
										out = new FileOutputStream("E://Programs/Eclipse/workspase/A_Lesson_34/data.xml");
									} 
									catch (FileNotFoundException e) 
									{
										e.printStackTrace();
									}
									XMLEncoder xmlEncoder = new XMLEncoder(out);
									xmlEncoder.writeObject(pp);
									xmlEncoder.flush();
									xmlEncoder.close();
 */

