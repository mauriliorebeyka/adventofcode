package adventofcode.utils;

import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
import org.springframework.stereotype.Component;

@Component
public class InputReaderHttpClientFactory {

	private static String SESSION_COOKIE = "53616c7465645f5fd64f97bd2fca9a80bb2cbcba1e7c833763ec254ece5a1bd533514ab200c2a9eefb7e7abfebb63f7d6bac3e8d216570b0fdebbfe2c60f4bd9";
	
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
