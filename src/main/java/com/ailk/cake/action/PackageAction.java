package com.ailk.cake.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.api.PackageIfc;
import com.ailk.api.impl.PackageManager;

@Controller
public class PackageAction extends BaseAction {

	@RequestMapping("/package/getVersion.do")
	public void getVersion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = getParameter(request);
		HttpSession session = request.getSession();
		auditManager.audit(-1, data);

		PackageIfc.GetVersionRequest requestObj = gson.fromJson(data, PackageIfc.GetVersionRequest.class);
		PackageIfc ifc = new PackageManager();
		String result = toJson(session, requestObj, ifc.getVersion(requestObj));

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(result);
	}

}