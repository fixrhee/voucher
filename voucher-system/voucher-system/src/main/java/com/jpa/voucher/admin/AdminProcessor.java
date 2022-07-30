package com.jpa.voucher.admin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import com.jpa.voucher.admin.data.Login;
import com.jpa.voucher.admin.data.Outlet;
import com.jpa.voucher.admin.data.Product;

@Controller
public class AdminProcessor {

	public JSONObject submitLogin(Login login) throws MalformedURLException, IOException {
		String result = "";
		HttpPost post = new HttpPost("http://localhost:8900/optima/ovs/member/token");

		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("username", login.getUsername()));
		urlParameters.add(new BasicNameValuePair("password", login.getPassword()));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		return new JSONObject(result);
	}

	public JSONObject memberInfo(String token) throws MalformedURLException, IOException {
		String result = "";
		HttpGet get = new HttpGet("http://localhost:8900/optima/ovs/member");

		get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(get)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		return new JSONObject(result);
	}

	public Integer getAllVoucher(String token) throws MalformedURLException, IOException, URISyntaxException {
		// LocalDate today = LocalDate.now();
		// LocalDate beforeWeek = today.minus(1, ChronoUnit.WEEKS);

		String result = "";
		URIBuilder builder = new URIBuilder("http://localhost:8900/optima/ovs/voucher");
		builder.setParameter("currentPage", "0").setParameter("pageSize", "10");
		HttpGet get = new HttpGet(builder.build());

		get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(get)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		JSONObject o = new JSONObject(result);
		if (o.getString("status").equalsIgnoreCase("PROCESSED")) {
			return o.getJSONObject("payload").getInt("totalRecord");
		} else {
			return 0;
		}
	}

	public Integer getPublishedVoucher(String token) throws MalformedURLException, IOException, URISyntaxException {
		// LocalDate today = LocalDate.now();
		// LocalDate beforeWeek = today.minus(1, ChronoUnit.WEEKS);

		String result = "";
		URIBuilder builder = new URIBuilder("http://localhost:8900/optima/ovs/voucher/publish");
		builder.setParameter("currentPage", "0").setParameter("pageSize", "10");
		HttpGet get = new HttpGet(builder.build());

		get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(get)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		JSONObject o = new JSONObject(result);
		if (o.getString("status").equalsIgnoreCase("PROCESSED")) {
			return o.getJSONObject("payload").getInt("totalRecord");
		} else {
			return 0;
		}
	}

	public Integer getPublishedVoucherStat(String token, String status)
			throws MalformedURLException, IOException, URISyntaxException {
		// LocalDate today = LocalDate.now();
		// LocalDate beforeWeek = today.minus(1, ChronoUnit.WEEKS);

		String result = "";
		URIBuilder builder = new URIBuilder("http://localhost:8900/optima/ovs/voucher/publish/stat");
		builder.setParameter("status", status);
		HttpGet get = new HttpGet(builder.build());

		get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(get)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		JSONObject o = new JSONObject(result);
		if (o.getString("status").equalsIgnoreCase("PROCESSED")) {
			return o.getJSONObject("payload").getInt("totalRecord");
		} else {
			return 0;
		}
	}

	public JSONObject listAllOutlet(Integer currentPage, Integer pageSize, String token)
			throws URISyntaxException, ParseException, IOException {
		String result = "";
		URIBuilder builder = new URIBuilder("http://localhost:8900/optima/ovs/outlet");
		builder.setParameter("currentPage", String.valueOf(currentPage)).setParameter("pageSize",
				String.valueOf(pageSize));
		HttpGet get = new HttpGet(builder.build());

		get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(get)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		return new JSONObject(result);
	}

	public JSONObject listAllProduct(Integer currentPage, Integer pageSize, String token)
			throws URISyntaxException, ParseException, IOException {
		String result = "";
		URIBuilder builder = new URIBuilder("http://localhost:8900/optima/ovs/product");
		builder.setParameter("currentPage", String.valueOf(currentPage)).setParameter("pageSize",
				String.valueOf(pageSize));
		HttpGet get = new HttpGet(builder.build());

		get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(get)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		return new JSONObject(result);
	}

	public JSONObject listAllVoucher(Integer currentPage, Integer pageSize, String token)
			throws URISyntaxException, ParseException, IOException {
		String result = "";
		URIBuilder builder = new URIBuilder("http://localhost:8900/optima/ovs/voucher");
		builder.setParameter("currentPage", String.valueOf(currentPage)).setParameter("pageSize",
				String.valueOf(pageSize));
		HttpGet get = new HttpGet(builder.build());

		get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(get)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		return new JSONObject(result);
	}

	public JSONObject listAllPublished(Integer currentPage, Integer pageSize, String token)
			throws URISyntaxException, ParseException, IOException {
		String result = "";
		URIBuilder builder = new URIBuilder("http://localhost:8900/optima/ovs/voucher/publish");
		builder.setParameter("currentPage", String.valueOf(currentPage)).setParameter("pageSize",
				String.valueOf(pageSize));
		HttpGet get = new HttpGet(builder.build());

		get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(get)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		return new JSONObject(result);
	}

	public JSONObject createOutlet(Outlet outlet, String token) throws URISyntaxException, ParseException, IOException {
		String result = "";
		HttpPost post = new HttpPost("http://localhost:8900/optima/ovs/outlet");
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("name", outlet.getName()));
		urlParameters.add(new BasicNameValuePair("address", outlet.getAddress()));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		post.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		return new JSONObject(result);
	}

	public JSONObject createProduct(@Valid Product product, String token) throws Exception {
		String result = "";
		HttpPost post = new HttpPost("http://localhost:8900/optima/ovs/product");
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("name", product.getName()));
		urlParameters.add(new BasicNameValuePair("description", product.getDescription()));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		post.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		}

		return new JSONObject(result);
	}

}
