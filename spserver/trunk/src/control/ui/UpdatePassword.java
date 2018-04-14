package control.ui;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.LoginUtil;
import util.UserUtil;
import control.db.personDB.WebDB;
import enumPKG.EventIdList;
import frame.SingleFactory;

public class UpdatePassword extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdatePassword() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object fromWhere = request.getAttribute("from");

		if (fromWhere != null && fromWhere.toString().equals("ios")) {
			response.setContentType("text/json;charset=UTF-8");
		} else {
			response.setContentType("text/html;charset=UTF-8");
		}
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String pid = request.getParameter("pid");
		
		WebDB webDB = SingleFactory.ins().getWebDBIns();
		PrintWriter out = response.getWriter();
		
		if(!LoginUtil.checkLogin(request)){
			UserUtil.sendNormalErrorMsg(out,
					EventIdList.LOGIN_SESSION_ERROR);
			return;
		}
		//旧密码不正确
		if(!webDB.checkOldPassword(pid, oldPassword)){
			UserUtil.sendNormalErrorMsg(out,
					EventIdList.RESPONSE_OLD_PASSWORD_ERROR);
			return;
		}
		// 檢查密码是否过于简单
		if (!UserUtil.checkPass(newPassword)) {
			UserUtil.sendNormalErrorMsg(out,
					EventIdList.RESPONSE_PASS_TOO_EASE);
			return;
		}
		webDB.updatePassword(out, pid, oldPassword, newPassword);
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}