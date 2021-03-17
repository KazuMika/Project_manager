package filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cdi.DBMap;
import constants.FunctionCons;

@WebFilter("/faces/common/customer/*")
public class CustomerFilter implements Filter {

	@Inject
	private DBMap map;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (!this.map.getFuncListForFilter().contains(FunctionCons.F_CUSTOMER)) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
		}

		chain.doFilter(request, response);

	}

}
