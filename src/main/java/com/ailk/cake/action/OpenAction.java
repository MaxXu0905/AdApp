package com.ailk.cake.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.api.OpenIfc;
import com.ailk.api.impl.OpenManager;

@Controller
public class OpenAction extends BaseAction {

	@RequestMapping("/open/bindBdPushUser.do")
	public void bindBdPushUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(-1, data);

		OpenIfc.BindBdPushUserRequest requestObj = gson.fromJson(data, OpenIfc.BindBdPushUserRequest.class);
		OpenIfc ifc = new OpenManager();
		String result = toJson(session, requestObj, ifc.bindBdPushUser(requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

}