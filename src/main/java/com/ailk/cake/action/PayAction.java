package com.ailk.cake.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.api.PayIfc;
import com.ailk.api.impl.PayManager;

@Controller
public class PayAction extends BaseAction {

	@RequestMapping("/auth/pay/lock.do")
	public void lock(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		PayIfc.LockRequest requestObj = gson.fromJson(data, PayIfc.LockRequest.class);
		PayIfc ifc = new PayManager();
		String result = toJson(session, requestObj, ifc.lock(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/pay/unlock.do")
	public void unlock(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		PayIfc.UnlockRequest requestObj = gson.fromJson(data, PayIfc.UnlockRequest.class);
		PayIfc ifc = new PayManager();
		String result = toJson(session, requestObj, ifc.unlock(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/pay/charge.do")
	public void charge(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		PayIfc.ChargeRequest requestObj = gson.fromJson(data, PayIfc.ChargeRequest.class);
		PayIfc ifc = new PayManager();
		String result = toJson(session, requestObj, ifc.charge(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/pay/commitPromotion.do")
	public void commitPromotion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		PayIfc.CommitPromotionRequest requestObj = gson.fromJson(data, PayIfc.CommitPromotionRequest.class);
		PayIfc ifc = new PayManager();
		String result = toJson(session, requestObj, ifc.commitPromotion(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/pay/rqstPay.do")
	public void rqstPay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		PayIfc.RqstPayRequest requestObj = gson.fromJson(data, PayIfc.RqstPayRequest.class);
		PayIfc ifc = new PayManager();
		String result = toJson(session, requestObj, ifc.rqstPay(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/pay/getPayRqst.do")
	public void getPayRqst(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		PayIfc.GetPayRequest requestObj = gson.fromJson(data, PayIfc.GetPayRequest.class);
		PayIfc ifc = new PayManager();
		String result = toJson(session, requestObj, ifc.getPayRqst(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

}