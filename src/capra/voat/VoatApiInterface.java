package capra.voat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import org.json.*;

public class VoatApiInterface {

	private final String tokenURL = "https://fakevout.azurewebsites.net/api/token";
	
	private String username;
	private String pwd;
	private String apiKey;
	private String apiToken;
	
	public String getToken(String uname, String pwd, String apiKey) throws Exception{
		// set parameters
		String urlParamaters = "grant_type=password&username="+uname+"&password="+pwd;
		URL url = new URL(tokenURL);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "applicaiton/json");
		con.setRequestProperty("Voat-ApiKey", apiKey);
		// make POST
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParamaters);
		wr.flush();
		wr.close();
		// read response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// parse token from returned JSON
		JSONObject json = new JSONObject(response.toString());
		String token = json.getString("access_token");
		return token;
	}
	
	
	/*public void httpsGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "applicaiton/json");
		con.setRequestProperty("Voat-ApiKey", value);
		
	}*/
	
}
