package adventofcode.challenge;

import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
import org.springframework.stereotype.Component;

@Component
public class InputReaderHttpClientFactory {

	private static String SESSION_COOKIE = "53616c7465645f5f52251f47df601a63e42221b5d7372352b51b6be625358e2f7e2b40b9cac8f550a3f757969ed2a4478971cdca561d9dfaf09898e8ddd15fa5";
	
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
