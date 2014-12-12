package serverProxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import com.sun.xml.internal.ws.util.StringUtils;

public class ServerProxy implements IServer {

	static final String COOKIES_HEADER = "Set-cookie";

	private String host;
	private int port;
	private CookieManager cookieManager;

	/**
	 * Creates a server proxy that points to the specified host and port
	 * 
	 * @param host
	 * @param port
	 */
	public ServerProxy(String host, int port) {
		this.setHost(host);
		this.setPort(port);
		this.cookieManager = new java.net.CookieManager();
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	private BufferedReader getResponse(IRequest request) throws Exception {
		// Set up the url connection
		String url = this.getBasePath() + request.getEndpoint();
		URL obj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

		// Set header values
		//System.out.println(request.getRequestMethod());
		//System.out.println(request.getEndpoint());
		connection.setRequestMethod(request.getRequestMethod());
		connection.setRequestProperty("Content-Type", "application/json");

		// Write Cookies
		if (cookieManager.getCookieStore().getCookies().size() > 0) {
			// Cookies are stored as a comma delimited list
			String delim = "";// This ensures that there is no comma before the
								// first value
			StringBuilder builder = new StringBuilder();
			for (HttpCookie i : cookieManager.getCookieStore().getCookies()) {
				builder.append(delim).append(i);
				delim = "; ";// faster than using an if statement every iteration
			}
			connection.setRequestProperty("Cookie", builder.toString());
		}

		
		connection.setDoOutput(true);
		
		// Write the body

		String bodyString = request.getBody();
		byte[] outputInBytes = bodyString.getBytes("UTF-8");
		OutputStream os = connection.getOutputStream();
		os.write(outputInBytes);
		os.close();
		int responseCode = connection.getResponseCode();
		if(responseCode != 200){
			throw new RequestException(responseCode);
		}

		BufferedReader response = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		// Save Cookies
		Map<String, List<String>> headerFields = connection.getHeaderFields();
		List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
		if (cookiesHeader != null) {
			for (String cookie : cookiesHeader) {
				cookieManager.getCookieStore().add(obj.toURI(),
						HttpCookie.parse(cookie).get(0));
			}
		}

		return response;
	}

	private String getBasePath() {
		return host + ":" + port;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IResponse> T sendCommand(IRequest request, T emptyResponse)
			throws Exception {
		BufferedReader response = this.getResponse(request);

		return (T) emptyResponse.fromResponse(response,
				cookieManager.getCookieStore());
	}
}
