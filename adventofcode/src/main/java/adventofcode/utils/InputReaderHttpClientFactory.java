package adventofcode.utils;

import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
import org.springframework.stereotype.Component;

@Component
public class InputReaderHttpClientFactory {

	private static String SESSION_COOKIE = "53616c7465645f5f90285f35e0a4e6cf7226fe5996865c3a52e7245c78c14def303fc0fe19ab4c795e636a68c80e1d7594eb9b578337f13c27b2080906fc474a";
	
	public CloseableHttpClient getHttpClient() {
		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie cookie = new BasicClientCookie("session", SESSION_COOKIE);
		cookie.setDomain(".adventofcode.com");
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
		HttpClients.custom().setDefaultCookieStore(cookieStore);
		return HttpClients.custom().setDefaultCookieStore(cookieStore).build();
	}
}
