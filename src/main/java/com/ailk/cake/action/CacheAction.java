package com.ailk.cake.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.api.CacheIfc;
import com.ailk.api.impl.BaseRequest;
import com.ailk.api.impl.CacheManager;

@Controller
public class CacheAction extends BaseAction {

	@RequestMapping("/cache/reload.do")
	public void handshake(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(getUserId(session), data);

		BaseRequest requestObj = gson.fromJson(data, BaseRequest.class);
		CacheIfc ifc = new CacheManager();
		String result = toJson(session, requestObj, ifc.reload(requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

}