package com.ailk.cake.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.api.AdIfc;
import com.ailk.api.impl.AdManager;

@Controller
public class AdAction extends BaseAction {

	@RequestMapping("/auth/ad/getAds.do")
	public void getAds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		AdIfc.GetAdsRequest requestObj = gson.fromJson(data, AdIfc.GetAdsRequest.class);

		AdIfc ifc = new AdManager();
		String result = toJson(session, requestObj, ifc.getAds(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}
	
	@RequestMapping("/ad/getSplashAds.do")
	public void getSplashAds(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(-1L, data);

		AdIfc.GetAdsRequest requestObj = gson.fromJson(data, AdIfc.GetAdsRequest.class);

		AdIfc ifc = new AdManager();
		String result = toJson(session, requestObj, ifc.getSplashAds(requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

	@RequestMapping("/auth/ad/getPromotions.do")
	public void getPromotions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		AdIfc.GetPromotionsRequest requestObj = gson.fromJson(data, AdIfc.GetPromotionsRequest.class);

		AdIfc ifc = new AdManager();
		String result = toJson(session, requestObj, ifc.getPromotions(session, requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

}