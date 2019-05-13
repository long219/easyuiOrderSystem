package menu_server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import propertys.UserProperties;
import serviceImpl.UserServiceImpl;

/**
 * Servlet implementation class UserRegister
 */
@WebServlet("/userRegister")
public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserServiceImpl userServiceImpl = null;// 用户逻辑层对象
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegister() {
        super();
        // TODO Auto-generated constructor stub
        userServiceImpl = new UserServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//设置服务端编码
		 response.setCharacterEncoding("UTF-8");
		 response.setHeader("Content-type", "text/html;charset=UTf-8");
		 
		 String method = request.getParameter("method");
		 String msg="";
		 if("userAdd".equals(method)) {
			 //注册
			 msg=userAdd(request,response);
			 
		 }else if("enterd".equals(method)) {
			 //登录
			 msg=userRegister(request, response);
		 }else if("queryUserID".equals(method)) {
			 //根据用户ID查询用户手机号
			 msg = UserIdQueryIphone(request, response);
		 }else {
			 //根据用户名查询用户ID
			 msg=FooduserID(request, response);
		 }
		 PrintWriter pw = response.getWriter();
		 pw.print(msg);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	//根据用户名查询用户ID
		public String FooduserID(HttpServletRequest request,HttpServletResponse response) {
			String userIphone = request.getParameter("usm");
			
			UserProperties userpt = userServiceImpl.querySomeone(userIphone);
			
			System.out.println(userpt);
			//用户ID
			return String.valueOf(userpt.getId());
			
		}
		
		
		//根据用户ID查询用户手机号
		public String UserIdQueryIphone(HttpServletRequest request,HttpServletResponse response) {
			
			String userID = request.getParameter("userID");
			UserProperties userpt = userServiceImpl.querySomeone(Integer.valueOf(userID));	
			return userpt.getPhone();
		}
		
		//用户注册
		public String userAdd(HttpServletRequest request,HttpServletResponse response) {
			String  strdata = request.getParameter("list");
			//将Json字符串转为 JAVA 对象
			JSON json = JSONObject.parseObject(strdata.toString());
			UserProperties userpt = JSONObject.toJavaObject(json, UserProperties.class);
			System.out.println(userpt.toString());
			
			//校验手机号是否存在
			if(!userServiceImpl.check(userpt.getPhone())) {
				return"抱歉，该手机号码已被注册";
			}
			userServiceImpl.add(userpt);
			return "注册成功";
			
		}

		//用户登录
		public String userRegister(HttpServletRequest request,HttpServletResponse response) {
			String  strdata = request.getParameter("list");
			//将Json字符串转为 JAVA 对象
			JSON json = JSONObject.parseObject(strdata.toString());
			UserProperties userpt = JSONObject.toJavaObject(json, UserProperties.class);
			//System.out.println("账号："+userpt.getPhone()+" 密码："+userpt.getPassword());
			UserProperties upro = userServiceImpl.querySomeone(userpt.getPhone());
			
			String password = userpt.getPassword();
			if(upro == null) {

				return "账号不存在";
			}
			if(!userServiceImpl.check(upro, password, userpt.getPhone())) {
			
				return "账号或者密码输入有误，账号与密码不匹配";
			}
			if("C".equals(upro.getUser_types().toUpperCase())) { // C代表普通会员
				//memberView.menu(upro);
				return "普通会员界面";
			}else {
				//adminView.menu();
				return "管理员界面";
			}
		}
}
