package menu_server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import propertys.Menu_Property;
import propertys.MessageVO;
import propertys.ShoppingTrolleyProperties;
import serviceImpl.MenuServiceImpl;
import serviceImpl.ShoppingTrolleyServiceImpl;

/**
 * Servlet implementation class MenuViewServlet
 */
@WebServlet("/menuViewServlet")
public class MenuViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
	//菜品逻辑层
	private MenuServiceImpl menuServiceImpl;
	//购物车逻辑层
	private ShoppingTrolleyServiceImpl shoppingTrolleyServiceImpl;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuViewServlet() {
        super();
        // TODO Auto-generated constructor stub
        
        menuServiceImpl = new MenuServiceImpl();
        shoppingTrolleyServiceImpl = new ShoppingTrolleyServiceImpl();
       
    }

      
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//设置服务端编码
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		
		String mothed = request.getParameter("mothed");
		String msg = null;
		if("menuAdd".equals(mothed)) {
			//菜品增加
			msg=menuAdd(request, response);
		}else if("menuUpdate".equals(mothed)) {
			//菜品修改
			msg=menuUPdate(request, response);
		}else if("menuDelete".equals(mothed)) {
			//菜品删除（软删除）
			msg=menuDelete(request, response);
		}else if("userMenuQuery".equals(mothed)){
			//查询菜品信息（会员用户）
			msg=userMenuQuery(request, response);
		}else if("userAddShoppingTrolley".equals(mothed)){
			//购物车添加（会员用户）
			msg = userAddShoppingTrolley(request, response);
		}else if("userShippingTrolleyQuery".equals(mothed)){
			//查询购物车（会员用户）
			msg = userShippingTrolleyQuery(request, response);
		}else {
			//菜品查询
			msg = menuQuery(request, response);
		}
		//给前台做出响应
		response.getWriter().print(msg);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
		
	/**
	 * 菜品增加
	 * @param request
	 * @param response
	 * @return
	 */
	public String menuAdd(HttpServletRequest request , HttpServletResponse response) {
		String mu = request.getParameter("menuLis");
		
		//将JSON字符串转为JSON对象
		JSONObject json = JSONObject.parseObject(mu);
		//将JSON对象转为JAVA对象
		Menu_Property mp = JSONObject.toJavaObject(json, Menu_Property.class);
		
		if (menuServiceImpl.check(mp.getName())) {
			return "该餐品名已存在";
		}	
		menuServiceImpl.add(mp);
		System.out.println("--"+mp.toString());
		
		return "添加成功！";
	}
	
	/**
	 * 菜品修改
	 * @param request
	 * @param response
	 * @return
	 */
	public String menuUPdate(HttpServletRequest request , HttpServletResponse response) {
		
		String mu = request.getParameter("menuLis");
		
		//将JSON字符串转为JSON对象
		JSONObject json = JSONObject.parseObject(mu);
		//将JSON对象转为JAVA对象
		Menu_Property mp = JSONObject.toJavaObject(json, Menu_Property.class);
		String msg=menuServiceImpl.updateMU(mp);
		return msg;
	}
	
	/**
	 * 查询菜品数据
	 * @param request
	 * @param response
	 * @return
	 */
	public String menuQuery(HttpServletRequest request , HttpServletResponse response) {
		
		//获取页数
		int page = Integer.valueOf(request.getParameter("page"));
		//要展示的行数
		int rows = Integer.valueOf(request.getParameter("rows"));
		
		//菜品名
		String name = request.getParameter("name");
		String isTop = request.getParameter("isTop");
		String stated = request.getParameter("menuState");
		
		
		//获取要在要排序的字段
		String sorta = request.getParameter("sort");
		//获取要排序的格式
		String order = request.getParameter("order");
		
		MessageVO<Object> mv = new MessageVO<>();
		
		//查询菜品所有数据
		//List <Menu_Property>lis=menuStoreImpl.menuQuery();
	
		//分页展示及根据 name , state , isTop查询  
		List <Menu_Property>lis=menuServiceImpl.menuPages(page, rows ,name,isTop,stated,sorta,order);
		
		//获取数据的总条数
		mv.setTotal(menuServiceImpl.totals(name,isTop,stated));
		
		mv.setRows(lis);
						
		//将JAVA对象转为字符串 发送给前台
		String orderby = JSONObject.toJSONString(mv);
		return orderby;
	}
	
	/**
	 * 根据Id删除（  软删除     将状态改为删除）
	 * @param request
	 * @param response
	 * @return
	 */
	public String menuDelete(HttpServletRequest request , HttpServletResponse response) {
		//获取菜品ID
		int menuId = Integer.parseInt(request.getParameter("menuId"));
		menuServiceImpl.menuDelete(menuId);
		return "删除成功！";
	}
	
	/**
	 * 查询菜品信息（会员用户）（分页）
	 * @param request
	 * @param response
	 * @return
	 */
	public String userMenuQuery(HttpServletRequest request , HttpServletResponse response) {
		MessageVO<Object> vo = new MessageVO<>();
		vo.setRows(menuServiceImpl.queryOne());
		//查询总记录条数
		vo.setTotal(menuServiceImpl.totalOne());
		//将java对象转为字符串JSON对象
		String msg = JSONObject.toJSONString(vo);
		return msg;
	}
	
	/**
	 * 添加购物车 （会员用户）
	 * @param request
	 * @param response
	 * @return
	 */
	public String userAddShoppingTrolley (HttpServletRequest request , HttpServletResponse response) {
		
		String menuMsg = request.getParameter("list");		
		String msg = null;
		List<ShoppingTrolleyProperties> arrayList = new ArrayList<>();
		//将JSON数组转为java数组
		JSONArray jsonArray = JSON.parseArray(menuMsg);
		arrayList = jsonArray.toJavaList(ShoppingTrolleyProperties.class); 
		
		for (int i = 0; i < arrayList.size(); i++) {
			ShoppingTrolleyProperties sp=arrayList.get(i);
			sp.setMenuID(sp.getId());
			System.out.println(sp.toString());
			if(sp.getAmount()==0) {
				return "菜品的数量不能为0";
			}else {
				msg=shoppingTrolleyServiceImpl.add(sp);				
			}				
		}
		return msg ;
	}
	
	/**
	 * 查询购物车（会员用户）
	 * @param request
	 * @param response
	 * @return
	 */
	public String userShippingTrolleyQuery(HttpServletRequest request , HttpServletResponse response) {
		//获取用户id
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		MessageVO<Object> mv = new MessageVO<>();
		mv.setRows(shoppingTrolleyServiceImpl.queryUserID(userId));
		String msg= JSON.toJSONString(mv);
		return msg;
	}
}
