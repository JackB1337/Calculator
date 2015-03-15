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
@WebServlet("/crud")                                                   // СПОСОБ РЕГИСТРАЦИИ СЕРВЛЕТОВ !!! ЛУЧШИЙ!!!
public class CRUD extends HttpServlet                                  // ЭТО КЛАСС НАСЛЕДНИК СЕРВЛЕТА 
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

		MySQL_DB db = new MySQL_DB();                                       // Обьект класса создали
		Person p = new Person(); 	                                        // На основании этого класса создаются обьекты    

		switch(request.getParameter("op"))                                  // Из файла crud_servlet.js пришли данные методом GET, переменной OP присвоено какое-то слово (C,R,U,D) 
		{
		case "create":
			p.FName     = request.getParameter("fname");                        // Считываем данные которые пришли методом GET из файла crud_servlet
			p.LName     = request.getParameter("lname");
			p.person_ID = Integer.valueOf(request.getParameter("id" ));
			p.Age       = Integer.valueOf(request.getParameter("age"));
			p.Phone     = request.getParameter("phone"); 
			p.Type     = request.getParameter("type");   // ???????
			System.out.println("create ===>"+p.person_ID+" "+p.FName+" "+p.LName+" "+p.Age+" "+p.Phone +" " + p.Type);

			db.create(p);                                                   // В метод create(отправляем новую персону)
			out.write(db.read());                                           // Вызываем метод отправки(а тут получаем строку ответ)
			out.flush();                                                    // Завершаем передачу данных
			out.close();                                                    
			break;

		case "read":
			out.write(db.read());               /** out.write()- отправляет данные js файлу, но сначала вызывает метод read()- коротый возвращает строку html тегов */
			out.flush();
			out.close();
			//db.close();   // Закоментил НО ЗАЧЕМ ЭТА СТРОКА???????
			break;

		case "update":
			p.FName     = request.getParameter("fname");                        // Считываем данные которые пришли методом GET из файла crud_servlet
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
			p.person_ID  = Integer.valueOf(request.getParameter("id" ));     // Тут считали id - тоесть пришла цифра
			
			System.out.println("delete ===>"+p.person_ID+" "+p.FName+" "+p.LName+" "+p.Age+" "+p.Phone +" " + p.Type);

			db.delete(p);                      // В метод передали персону всю
			out.write(db.read());              // И сразу обновили таблицу
			out.flush();
			out.close();
			break;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}

