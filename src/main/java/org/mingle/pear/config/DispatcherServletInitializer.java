package org.mingle.pear.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * DispatcherServlet初始化
 * 
 * @since 1.8
 * @author Mingle
 */
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {
			SecurityConfig.class,
			DataAccessConfig.class,
			WebMvcConfig.class,
			ApplicationConfig.class,
			WebViewConfig.class,
			MessageConfig.class,
			MailConfig.class,
			ScheduleConfig.class,
			AsyncConfig.class,
			AspectJConfig.class
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] {
			new HiddenHttpMethodFilter(),
			new OpenEntityManagerInViewFilter(),
			characterEncodingFilter
		};
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		/* Enable escaping of form submission contents */
		servletContext.setInitParameter("defaultHtmlEscape", "true");
		SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
		sessionCookieConfig.setMaxAge(60 * 10);
		super.onStartup(servletContext);
	}

}
