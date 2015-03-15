
public class Person 
{
	int person_ID;
	String FName;
	String LName;
	int Age;
	String  Phone = " ";
	String Type = " ";
	
	public Person() {}                                         

	public Person(int Id, String FName, String LName, int Age, String Phone,String Type)  
	{
		this.person_ID = Id;                           
		this.LName = LName;
		this.FName = FName;
		this.Age   = Age;
		this.Phone = Phone;
		this.Type = Type;
	}
	
	public int    getId()    {return person_ID ;}                 
	public String getFName() {return FName;}
	public String getLName() {return LName;}
	public int    getAge()   {return Age  ;}	
	public String getPhone() {return Phone ;}
	public String getType() {return Type ;}

	
	public void setId   (int id)       {person_ID = id;}            // Для XML файла важно что КАК прописанны поля в Person и КАК прописанно в XML фале!!!
	public void setFName(String fName) {FName = fName;}
	public void setLName(String lName) {LName = lName;}
	public void setAge  (int age)      {Age   = age  ;}
	public void setPhone(String phone) {Phone = phone;}
	public void setType(String type) {Type = type;}
}
