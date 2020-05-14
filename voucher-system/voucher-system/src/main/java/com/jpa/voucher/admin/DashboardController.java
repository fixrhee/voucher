package com.jpa.voucher.admin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.ParseException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Controller
public class DashboardController {

	@Autowired
	private AdminProcessor adminProcessor;
	@Autowired
	private HazelcastInstance instance;

	@RequestMapping(value = "/index", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView dashboard(
			@CookieValue(value = "SessionID", defaultValue = "defaultCookieValue") String sessionID,
			HttpServletResponse response, Model model) {
		try {
			IMap<String, String> memberMap = instance.getMap("Member");
			String member = memberMap.get(sessionID);
			if (member == null) {
				return new ModelAndView("redirect:/login");
			}

			JSONObject memberInfo = adminProcessor.memberInfo(sessionID);
			Integer newVoucher = adminProcessor.getAllVoucher(sessionID);
			Integer newPublished = adminProcessor.getPublishedVoucherStat(sessionID, "PUBLISHED");
			Integer newRedemption = adminProcessor.getPublishedVoucherStat(sessionID, "USED");
			Integer expiredVoucher = adminProcessor.getPublishedVoucherStat(sessionID, "EXPIRED");

			model.addAttribute("name", memberInfo.getJSONObject("payload").getString("name"));
			model.addAttribute("menu", "dashboard");
			model.addAttribute("newVoucher", newVoucher);
			model.addAttribute("newPublished", newPublished);
			model.addAttribute("newRedemption", newRedemption);
			model.addAttribute("expiredVoucher", expiredVoucher);
			return new ModelAndView("index");
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("httpResponseCode", "500");
			model.addAttribute("status", "Oops !");
			model.addAttribute("description",
					"We are experiencing some trouble here, but don't worry our team are OTW to solve this");
			return new ModelAndView("page_exception");
		}
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ModelAndView product(@CookieValue(value = "SessionID", defaultValue = "defaultCookieValue") String sessionID,
			HttpServletResponse response, Model model) {
		try {
			IMap<String, String> memberMap = instance.getMap("Member");
			String member = memberMap.get(sessionID);
			if (member == null) {
				return new ModelAndView("redirect:/login");
			}

			JSONObject memberInfo = adminProcessor.memberInfo(sessionID);
			model.addAttribute("name", memberInfo.getJSONObject("payload").getString("name"));
			model.addAttribute("menu", "product");
			return new ModelAndView("product");
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("httpResponseCode", "500");
			model.addAttribute("status", "Oops !");
			model.addAttribute("description",
					"We are experiencing some trouble here, but don't worry our team are OTW to solve this");
			return new ModelAndView("page_exception");
		}
	}

	@RequestMapping(value = "/createProduct", method = RequestMethod.GET)
	public ModelAndView createProduct(
			@CookieValue(value = "SessionID", defaultValue = "defaultCookieValue") String sessionID,
			HttpServletResponse response, Model model) {
		try {
			IMap<String, String> memberMap = instance.getMap("Member");
			String member = memberMap.get(sessionID);
			if (member == null) {
				return new ModelAndView("redirect:/login");
			}

			JSONObject memberInfo = adminProcessor.memberInfo(sessionID);
			model.addAttribute("name", memberInfo.getJSONObject("payload").getString("name"));
			model.addAttribute("menu", "product");
			return new ModelAndView("createProduct");
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("httpResponseCode", "500");
			model.addAttribute("status", "Oops !");
			model.addAttribute("description",
					"We are experiencing some trouble here, but don't worry our team are OTW to solve this");
			return new ModelAndView("page_exception");
		}
	}

	@RequestMapping(value = "/outlet", method = RequestMethod.GET)
	public ModelAndView outlet(@CookieValue(value = "SessionID", defaultValue = "defaultCookieValue") String sessionID,
			HttpServletResponse response, Model model) {
		try {
			IMap<String, String> memberMap = instance.getMap("Member");
			String member = memberMap.get(sessionID);
			if (member == null) {
				return new ModelAndView("redirect:/login");
			}

			JSONObject memberInfo = adminProcessor.memberInfo(sessionID);
			model.addAttribute("name", memberInfo.getJSONObject("payload").getString("name"));
			model.addAttribute("menu", "outlet");
			return new ModelAndView("outlet");
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("httpResponseCode", "500");
			model.addAttribute("status", "Oops !");
			model.addAttribute("description",
					"We are experiencing some trouble here, but don't worry our team are OTW to solve this");
			return new ModelAndView("page_exception");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/viewOutlet", method = RequestMethod.GET)
	public String viewOutlet(@CookieValue(value = "SessionID", defaultValue = "defaultCookieValue") String sessionID,
			@RequestParam(value = "start") Integer start, @RequestParam(value = "length") Integer length)
			throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<Object> jsonList = new LinkedList<Object>();

		JSONObject jRes = adminProcessor.listAllOutlet(start, length, sessionID);
		JSONObject payload = jRes.optJSONObject("payload");

		for (int i = 0; i < payload.getJSONArray("body").length(); i++) {
			Map<String, Object> jsonData = new HashMap<String, Object>();
			jsonData.put("id", payload.getJSONArray("body").getJSONObject(i).get("id"));
			jsonData.put("name", payload.getJSONArray("body").getJSONObject(i).get("name"));
			jsonData.put("address", payload.getJSONArray("body").getJSONObject(i).get("address"));
			jsonData.put("createdDate", payload.getJSONArray("body").getJSONObject(i).get("createdDate"));
			jsonList.add(jsonData);
		}

		jsonMap.put("recordsTotal", payload.getInt("totalRecord"));
		jsonMap.put("recordsFiltered", payload.getInt("totalRecord"));
		jsonMap.put("data", jsonList);

		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = Obj.writeValueAsString(jsonMap);
		return jsonStr;
	}

	@RequestMapping(value = "/createOutlet", method = RequestMethod.GET)
	public ModelAndView createOutlet(
			@CookieValue(value = "SessionID", defaultValue = "defaultCookieValue") String sessionID,
			HttpServletResponse response, Model model) {
		try {
			IMap<String, String> memberMap = instance.getMap("Member");
			String member = memberMap.get(sessionID);
			if (member == null) {
				return new ModelAndView("redirect:/login");
			}

			JSONObject memberInfo = adminProcessor.memberInfo(sessionID);
			model.addAttribute("name", memberInfo.getJSONObject("payload").getString("name"));
			model.addAttribute("menu", "outlet");
			return new ModelAndView("createOutlet");
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("httpResponseCode", "500");
			model.addAttribute("status", "Oops !");
			model.addAttribute("description",
					"We are experiencing some trouble here, but don't worry our team are OTW to solve this");
			return new ModelAndView("page_exception");
		}
	}

	@RequestMapping(value = "/voucher", method = RequestMethod.GET)
	public ModelAndView voucher(@CookieValue(value = "SessionID", defaultValue = "defaultCookieValue") String sessionID,
			HttpServletResponse response, Model model) {
		try {
			IMap<String, String> memberMap = instance.getMap("Member");
			String member = memberMap.get(sessionID);
			if (member == null) {
				return new ModelAndView("redirect:/login");
			}

			JSONObject memberInfo = adminProcessor.memberInfo(sessionID);
			model.addAttribute("name", memberInfo.getJSONObject("payload").getString("name"));
			model.addAttribute("menu", "voucher");
			return new ModelAndView("voucher");
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("httpResponseCode", "500");
			model.addAttribute("status", "Oops !");
			model.addAttribute("description",
					"We are experiencing some trouble here, but don't worry our team are OTW to solve this");
			return new ModelAndView("page_exception");
		}
	}

	@RequestMapping(value = "/createVoucher", method = RequestMethod.GET)
	public ModelAndView createVoucher(
			@CookieValue(value = "SessionID", defaultValue = "defaultCookieValue") String sessionID,
			HttpServletResponse response, Model model) {
		try {
			IMap<String, String> memberMap = instance.getMap("Member");
			String member = memberMap.get(sessionID);
			if (member == null) {
				return new ModelAndView("redirect:/login");
			}

			JSONObject memberInfo = adminProcessor.memberInfo(sessionID);
			model.addAttribute("name", memberInfo.getJSONObject("payload").getString("name"));
			model.addAttribute("menu", "voucher");
			return new ModelAndView("createVoucher");
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("httpResponseCode", "500");
			model.addAttribute("status", "Oops !");
			model.addAttribute("description",
					"We are experiencing some trouble here, but don't worry our team are OTW to solve this");
			return new ModelAndView("page_exception");
		}
	}

	@RequestMapping(value = "/published", method = RequestMethod.GET)
	public ModelAndView published(
			@CookieValue(value = "SessionID", defaultValue = "defaultCookieValue") String sessionID,
			HttpServletResponse response, Model model) {
		try {
			IMap<String, String> memberMap = instance.getMap("Member");
			String member = memberMap.get(sessionID);
			if (member == null) {
				return new ModelAndView("redirect:/login");
			}

			JSONObject memberInfo = adminProcessor.memberInfo(sessionID);
			model.addAttribute("name", memberInfo.getJSONObject("payload").getString("name"));
			model.addAttribute("menu", "published");
			return new ModelAndView("published");
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("httpResponseCode", "500");
			model.addAttribute("status", "Oops !");
			model.addAttribute("description",
					"We are experiencing some trouble here, but don't worry our team are OTW to solve this");
			return new ModelAndView("page_exception");
		}
	}
}
