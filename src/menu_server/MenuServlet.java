package menu_server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import StoreImpl.MenuStoreImpl;
import propertys.Menu_Property;
import propertys.MessageVO;

/**
 * Servlet implementation class menuServlet
 */
@WebServlet("/menuServlet")
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	//菜品存储层
	private MenuStoreImpl menuStoreImpl;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuServlet() {
    
        super();
        // TODO Auto-generated constructor stub
        
    	menuStoreImpl = new MenuStoreImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//sresponse.getWriter().append("Served at: ").append(request.getContextPath());
		//设置服务端编码
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTf-8");
		
		/*//迭代发送请求的参数
		Enumeration<String> parameterNames = request.g etParameterNames();
		while(parameterNames.hasMoreElements()) {	
			System.out.println(parameterNames.nextElement()+"  ");
		}*/
		
		//获取页数
		int page = Integer.valueOf(request.getParameter("page"));	
		//获取要展示的行数
		int rows = Integer.valueOf(request.getParameter("rows"));
		//获取要在要排序的字段
		String sorta = request.getParameter("sort");
		//获取要排序的格式
		String order = request.getParameter("order");
		System.out.println("sort="+sorta+"  order="+order);
		
		//获取菜名
		String menuName =request.getParameter("name");	
		//获取价格
		String menuPrice =request.getParameter("price");
		
		System.out.println("菜品name="+menuName+"==="+menuPrice);
		MessageVO<Object> mv = new MessageVO<>();
		
		//查询菜品所有数据
		//List <Menu_Property>lis=menuStoreImpl.menuQuery();
		if("name".equals(menuName)) {
			//分页展示
			List <Menu_Property> lis=menuStoreImpl.menuPricePage(page, rows ,menuPrice,sorta,order);
			
			//获取数据的总条数
			mv.setTotal(menuStoreImpl.total(menuName ,menuPrice));
			
			mv.setRows(lis);
		}else {
			//分页展示
			List <Menu_Property>lis=menuStoreImpl.menuPage(page, rows ,menuName,sorta,order);
			
			//获取数据的总条数
			mv.setTotal(menuStoreImpl.total(menuName ,menuPrice));
			
			mv.setRows(lis);
		}			
		
		//将JAVA对象转为字符串 发送给前台
		String orderby = JSONObject.toJSONString(mv);	
		
		//将订单数据传给前台
		response.getWriter().print(orderby);
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
