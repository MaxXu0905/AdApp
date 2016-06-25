package com.ailk.cake.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ailk.api.UserIfc;
import com.ailk.api.UserIfc.UserStatSession;
import com.ailk.api.impl.AuditManager;
import com.ailk.api.impl.BaseRequest;
import com.ailk.api.impl.BaseResponse;
import com.ailk.cake.frame.util.Builder;
import com.ailk.common.ConstVariables;
import com.ailk.common.GlobalVariables;
import com.google.gson.Gson;

public class BaseAction {

	public static final Gson gson = new Gson();
	public static final String jsonSucc = "{\"code\":0}";
	public static final String jsonFail = "{\"code\":500}";
	public static final String JSON_DATA = "JSON";
	public static final String CHARSET = "UTF-8";

	public static final AuditManager auditManager = AuditManager.getInstance();

	public static Object buildFailJson(String message) {
		return buildFailJson(500, message);
	}

	public static Object buildSucessJson(String message) {
		return buildFailJson(0, message);
	}

	public static Object buildFailJson(int code, String message) {
		return Builder.asMap("code", code, "msg", message);
	}

	public static Object buildSucessJson(int code, String message) {
		return Builder.asMap("code", code, "msg", message);
	}

	public static String getParameter(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bos.write(ch);
		}

		return URLDecoder.decode(new String(bos.toByteArray()), CHARSET);
	}

	public static String getParameter(HttpServletRequest request) throws IOException {
		String data = request.getParameter(JSON_DATA);

		return URLDecoder.decode(data, CHARSET);
	}

	public static String toJson(HttpSession httpSession, BaseRequest request, BaseResponse response) {
		if (httpSession.isNew()) {
			switch (request.getOsType()) {
			case ConstVariables.OS_TYPE_ANDROID:
				response.setProperties(GlobalVariables.getAndroidproperties());
				break;
			default:
				break;
			}
		}

		return gson.toJson(response);
	}

	public static long getUserId(HttpSession session) {
		UserStatSession userStatSession = (UserStatSession) session.getAttribute(UserIfc.USER_STAT_ATTR);
		return userStatSession.getUserId();
	}

}
