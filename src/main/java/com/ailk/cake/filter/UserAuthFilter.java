package com.ailk.cake.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ailk.api.UserIfc;
import com.ailk.api.UserIfc.UserStatSession;
import com.ailk.api.impl.BaseResponse;
import com.google.gson.Gson;

public class UserAuthFilter implements Filter {
	
	private static final Logger logger = Logger.getLogger(UserAuthFilter.class);
	
	private Gson gson = new Gson();

	static class UserAuthResponse extends BaseResponse {
		private Integer timeout;

		public Integer getTimeout() {
			return timeout;
		}

		public void setTimeout(Integer timeout) {
			this.timeout = timeout;
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession(true);

		UserStatSession userStatSession = (UserStatSession) session
				.getAttribute(UserIfc.USER_STAT_ATTR);
		if (userStatSession == null) {
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			BaseResponse result = new BaseResponse();
			
			logger.debug("�ỰʧЧ");
			result.setErrorCode(BaseResponse.ENOSESSION);

			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(gson.toJson(result));
			return;
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
	}

}
