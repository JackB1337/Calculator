import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class crud       http://devcolibri.com/4284 !!!!!!!!! http://www.java2s.com/Code/Java/Servlets/JDBCandServlet.htm
 */
@WebServlet("/crud")                                                   // ������ ����������� ��������� !!! ������!!!
public class CRUD extends HttpServlet                                  // ��� ����� ��������� �������� 
{
	private static final long serialVersionUID = 1L;

	public CRUD() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("txt/html");
		PrintWriter out = response.getWriter();

		MySQL_DB db = new MySQL_DB();                                       // ������ ������ �������
		Person p = new Person(); 	                                        // �� ��������� ����� ������ ��������� �������    

		switch(request.getParameter("op"))                                  // �� ����� crud_servlet.js ������ ������ ������� GET, ���������� OP ��������� �����-�� ����� (C,R,U,D) 
		{
		case "create":
			p.FName     = request.getParameter("fname");                        // ��������� ������ ������� ������ ������� GET �� ����� crud_servlet
			p.LName     = request.getParameter("lname");
			p.person_ID = Integer.valueOf(request.getParameter("id" ));
			p.Age       = Integer.valueOf(request.getParameter("age"));
			p.Phone     = request.getParameter("phone"); 
			p.Type     = request.getParameter("type");   // ???????
			System.out.println("create ===>"+p.person_ID+" "+p.FName+" "+p.LName+" "+p.Age+" "+p.Phone +" " + p.Type);

			db.create(p);                                                   // � ����� create(���������� ����� �������)
			out.write(db.read());                                           // �������� ����� ��������(� ��� �������� ������ �����)
			out.flush();                                                    // ��������� �������� ������
			out.close();                                                    
			break;

		case "read":
			out.write(db.read());               /** out.write()- ���������� ������ js �����, �� ������� �������� ����� read()- ������� ���������� ������ html ����� */
			out.flush();
			out.close();
			//db.close();   // ���������� �� ����� ��� ������???????
			break;

		case "update":
			p.FName     = request.getParameter("fname");                        // ��������� ������ ������� ������ ������� GET �� ����� crud_servlet
			p.LName     = request.getParameter("lname");
			p.person_ID = Integer.valueOf(request.getParameter("id" ));
			p.Age       = Integer.valueOf(request.getParameter("age"));
			p.Phone     = request.getParameter("phone"); 
			p.Type     = request.getParameter("type");
			System.out.println("update ===>"+p.person_ID+" "+p.FName+" "+p.LName+" "+p.Age+" "+p.Phone +" " + p.Type);

			db.update(p);
			out.write(db.read());
			out.flush();
			out.close();
			break;

		case "delete":
			p.person_ID  = Integer.valueOf(request.getParameter("id" ));     // ��� ������� id - ������ ������ �����
			
			System.out.println("delete ===>"+p.person_ID+" "+p.FName+" "+p.LName+" "+p.Age+" "+p.Phone +" " + p.Type);

			db.delete(p);                      // � ����� �������� ������� ���
			out.write(db.read());              // � ����� �������� �������
			out.flush();
			out.close();
			break;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}

