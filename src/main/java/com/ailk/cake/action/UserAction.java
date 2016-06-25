package com.ailk.cake.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.api.UserIfc;
import com.ailk.api.impl.BaseRequest;
import com.ailk.api.impl.BaseResponse;
import com.ailk.api.impl.UserManager;

@Controller
public class UserAction extends BaseAction {

	@RequestMapping("/user/exists.do")
	public void exits(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(-1L, data);

		UserIfc.ExistsRequest requestObj = gson.fromJson(data, UserIfc.ExistsRequest.class);
		UserIfc ifc = new UserManager();
		String result = toJson(session, requestObj, ifc.exists(requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/user/register.do")
	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();

		UserIfc.RegisterRequest requestObj = gson.fromJson(data, UserIfc.RegisterRequest.class);
		UserIfc ifc = new UserManager();
		UserIfc.RegisterResponse responseObj = ifc.register(request.getSession(), requestObj);
		String result = toJson(session, requestObj, responseObj);

		if (responseObj.getErrorCode() == BaseResponse.SUCCESS)
			auditManager.audit(responseObj.getUserId(), data);
		else
			auditManager.audit(-1, data);

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/user/login.do")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();

		UserIfc.LoginRequest requestObj = gson.fromJson(data, UserIfc.LoginRequest.class);
		UserIfc ifc = new UserManager();
		UserIfc.LoginResponse responseObj = ifc.login(request.getSession(), requestObj);
		String result = toJson(session, requestObj, responseObj);

		if (responseObj.getErrorCode() == BaseResponse.SUCCESS)
			auditManager.audit(responseObj.getUserId(), data);
		else
			auditManager.audit(-1, data);

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/user/logout.do")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		UserIfc.LogoutRequest requestObj = gson.fromJson(data, UserIfc.LogoutRequest.class);
		UserIfc ifc = new UserManager();
		String result = toJson(session, requestObj, ifc.logout(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/user/sendVerify.do")
	public void sendVerify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(-1L, data);

		UserIfc.SendVerifyRequest requestObj = gson.fromJson(data, UserIfc.SendVerifyRequest.class);
		UserIfc ifc = new UserManager();
		String result = toJson(session, requestObj, ifc.sendVerify(request.getSession(), requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/user/getAsset.do")
	public void getAsset(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		UserIfc.GetAssetRequest requestObj = gson.fromJson(data, UserIfc.GetAssetRequest.class);
		UserIfc ifc = new UserManager();
		String result = toJson(session, requestObj, ifc.getAsset(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/user/update.do")
	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		UserIfc.UpdateRequest requestObj = gson.fromJson(data, UserIfc.UpdateRequest.class);
		UserIfc ifc = new UserManager();
		String result = toJson(session, requestObj, ifc.update(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/user/resetPassword.do")
	public void resetPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(-1L, data);

		UserIfc.ResetPasswordRequest requestObj = gson.fromJson(data, UserIfc.ResetPasswordRequest.class);
		UserIfc ifc = new UserManager();
		String result = toJson(session, requestObj, ifc.resetPassword(request.getSession(), requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/user/getRankBoard.do")
	public void getRankBoard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		UserIfc.GetRankBoardRequest requestObj = gson.fromJson(data, UserIfc.GetRankBoardRequest.class);
		UserIfc ifc = new UserManager();
		String result = toJson(session, requestObj, ifc.getRankBoard(requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/user/addFeedback.do")
	public void addFeedback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		UserIfc.AddFeedbackRequest requestObj = gson.fromJson(data, UserIfc.AddFeedbackRequest.class);
		UserIfc ifc = new UserManager();
		String result = toJson(session, requestObj, ifc.addFeedback(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/user/addBehaviors.do")
	public void addBehaviors(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();

		BaseRequest requestObj = gson.fromJson(data, BaseRequest.class);
		UserIfc ifc = new UserManager();
		String result = toJson(session, requestObj, ifc.addBehaviors(request.getSession(), data));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

}