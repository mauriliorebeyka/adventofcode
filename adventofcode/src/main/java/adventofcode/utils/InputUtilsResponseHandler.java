package adventofcode.utils;

import java.io.IOException;

import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class InputUtilsResponseHandler implements HttpClientResponseHandler<String> {

	@Override
	public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
		int code = response.getCode();
		if (code > 200) {
			throw new HttpResponseException(code, EntityUtils.toString(response.getEntity()));
		}
		return EntityUtils.toString(response.getEntity());
	}
	
}
