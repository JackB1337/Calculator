import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL_DB 
{	
	static final String DB_URL = "jdbc:mysql://localhost/personphone"; // personphone - Это название БД
	static final String USER   = "root";                               // ЛОГИН вход в PHPmaneger (тут удобно создавать БД)
	static final String PASS   = "root";                             // ПАРОЛЬ вход в PHPmaneger (доступ к БД)

	static final String TBL_PERSON = "person";                         // В коде пока нигде не используется
	static final String TBL_PHONE  = "phone";  
	static final String TBL_TYPE  = "type"; 

	Connection conn = null;        
	Statement     s = null;
	Statement    s1 = null;
	Statement    s2 = null;
	Statement    s3 = null;

	public MySQL_DB()                                                  // Конструктор класса в нем сразу определяем подключение к БД MySQL
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");                    // Подключили драйвер
			conn = DriverManager.getConnection(DB_URL, USER, PASS);    // Тут указали БД, логин, пароль
			s  = conn.createStatement();
			s1 = conn.createStatement();
			s2 = conn.createStatement();
		} 
		catch (ClassNotFoundException | SQLException e1) {e1.printStackTrace();}
		System.out.println("connection with MySQL completed");              
	}

	public void close()                                                 // Функция котрорая закрывает все соединения с БД
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


	/**************************************** РАБОТАЕТ *******************************************/
	public void create(Person p)                           // В метод create получили персону 
	{
		String sql = "INSERT INTO person (person_ID, FName, LName, Age) VALUES("+p.person_ID+",'"+p.FName+"','"+p.LName+"', "+p.Age+")";   // ОДИНАРНЫЕ КАВЫЧКИ '' для mySQl базы даных не играют никакого значения! А в Н2 нужно соблюдать!!!
		try 
		{                                                      
			s.executeUpdate(sql);                             
		}catch(Exception ex){ex.printStackTrace();}System.out.println("В DB MySQL передан следующий запрос Create: " +sql);  // Дополнительный вывод на консоль не обязательный! Но желательный, по идее=)


		String[] phone_numbers = p.Phone.trim().split(",");                 // Создаем массив номеров, введенных в диалоге
		if(phone_numbers.length == 1)                                       // Если введен ОДИН НОМЕР ТЕЛЕФОНА                                 
		{
			String sql1 = "INSERT INTO `phone`(`phone_ID`, `person_ID`, `phones`) VALUES ("+null+","+p.person_ID+","+phone_numbers[0]+")";   /// "+p.Phone+"
			try 
			{                                                      
				s.executeUpdate(sql1);                             
			} catch(Exception ex){ex.printStackTrace();}System.out.println("В DB MySQL передан следующий запрос Create: " +sql1);  // Дополнительный вывод на консоль не обязательный! Но желательный, по идее=)	
		}
		else
		{
			for (int i = 0; i < phone_numbers.length; i++)                 // Если введены МНОГО НОМЕРОВ         
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
		}catch(Exception ex){ex.printStackTrace();}System.out.println("В DB MySQL передан следующий запрос Create: " +sql);  // Дополнительный вывод на консоль не обязательный! Но желательный, по идее=)

	}

	/**************************************** РАБОТАЕТ *******************************************/
	public void update(Person p )                          // Получили персону
	{
	String[] phone_numbers = p.Phone.trim().split("\\s+");       // Создаем массив номеров, введенных в диалоге, разбиваем по пробелам
		
	//System.out.println(phone_numbers[0] +" "+ phone_numbers[1] );
	
		try
		{                                                     
			s.execute("UPDATE person SET FName = '"+p.FName+"', LName = '"+p.LName+"', Age = "+p.Age+" WHERE person_ID="+p.person_ID+";");                             
		}catch(Exception ex){ex.printStackTrace();}//System.out.println("В  DB MySQL передан следующий запрос Update: " +sql);		
		
		
		String sql2 = "DELETE FROM phone WHERE person_ID = "+p.person_ID;    // В BD MySQL колонка называется ID,а в Н2 называется ID_Person, ВНИМАТЕЛЬНО!
		try
		{                                                    
			s1.execute(sql2);                 
		}catch(Exception ex){ex.printStackTrace();}
		
		if(phone_numbers.length == 1)                           // Проверка на один телефон в массиве
		{	
			System.out.println(phone_numbers[0]);
			try
			{                                                    
				s2.execute("INSERT INTO `phone`(`phone_ID`, `person_ID`, `phones`) VALUES (null,"+p.person_ID+","+phone_numbers[0]+")");   
			}catch(Exception ex){ex.printStackTrace();}			
		}
		
		else
		{			
			for (int i = 0; i < phone_numbers.length; i++)      // Если введены МНОГО НОМЕРОВ         
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



	/**************************************** РАБОТАЕТ *******************************************/
	public void delete(Person p)                           
	{
		String sql = "DELETE FROM person WHERE person_ID = "+p.person_ID;    // В BD MySQL колонка называется person_ID,а в Н2 называется ID_Person, ВНИМАТЕЛЬНО!
		try
		{                                                    
			s.execute(sql);                 
		}catch(Exception ex){ex.printStackTrace();}System.out.println("В DB MySQL передан следующий запрос Delete: " +sql);	

		String sql1 = "DELETE FROM phone WHERE person_ID = "+p.person_ID;   
		try
		{                       
			s.execute(sql1);              // Может тут использовать просто execute      
		}catch(Exception ex){ex.printStackTrace();}System.out.println("В DB MySQL передан следующий запрос Delete: " +sql);	
		String sql2 = "DELETE FROM type WHERE phone_ID = "+p.Phone;   
		try
		{                       
			s.execute(sql2);              // Может тут использовать просто execute      
		}catch(Exception ex){ex.printStackTrace();}System.out.println("В DB MySQL передан следующий запрос Delete: " +sql);	
	
	}

	/**************************************** РАБОТАЕТ *******************************************/
	public String read()                                    // Метод read возвращает строку html тегов
	{
		String str = "<table border="+"1"+" bgcolor=aqua>"; // ????????????????? bgcolor=aqua> удалить!!!
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
			s.execute("SELECT * FROM `person`;");            // Отправка запроса к БД !personphone! к таблице  !person!     
			ResultSet rs = s.getResultSet();                           // ПОЛУЧАЕМ данные из таблицы в переменную rs 

			while (rs.next())                                // Пока в БД есть данные достаем их и ложим в переменную str
			{
				str += "<tr>"; 

				str += "<td>"+ Integer.toString(rs.getInt(1))+"</td>";                 
				str += "<td>"+ rs.getString(2)+"</td>";    
				str += "<td>"+ rs.getString(3)+"</td>";    
				str += "<td>"+ Integer.toString(rs.getInt(4))+"</td>";    


				String count_numbers = "";   // Переменная для подсчета номеров у одной персоны
				String type = "";

				s1.execute("SELECT COUNT(*) FROM `phone` WHERE  person_ID = "+rs.getInt(1)+";");  // Запрос на подсчет количества номеров у обьекта 
				ResultSet rs1 = s1.getResultSet();
				while (rs1.next()) 
				{
					count_numbers +="("+rs1.getInt(1)+")"+" ";
				}

				s2.execute("SELECT phones FROM phone WHERE person_ID = "+rs.getInt(1)+";");    // Выбор номеров телефонов из таблицы phone 
				ResultSet rs2 = s2.getResultSet();
				while (rs2.next()) 
				{
					count_numbers += Integer.toString(rs2.getInt(1))+" ";
				}
				s3.execute("SELECT * FROM `type` WHERE  phone = "+rs.getInt(1)+";");  // Запрос на подсчет количества номеров у обьекта 
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
		return str;                                  // Отправляем строку html тегов
	}
}




/* В методе read  было это для записи в XML файл
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

