package com.cjon.bank.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.service.BankCheckMemberService;
import com.cjon.bank.service.BankDepositService;
import com.cjon.bank.service.BankSelectAllMemberService;
import com.cjon.bank.service.BankSelectMemberService;
import com.cjon.bank.service.BankService;
import com.cjon.bank.service.BankTransferService;
import com.cjon.bank.service.BankWithrawService;

@Controller
public class BankController {
	private BankService service;
	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		System.out.println("0");
		this.dataSource = dataSource;
	}
	
	
	//view로 jsp를 이용하지 않으므로 리턴값이 String이 아닌 void
	//jsp를 이용할 때는 String반환
	//json값을 결과값으로 사용할 때는 Stream을 열어서 직접 클라이언트에게 json 전송할 것이므로 void 반환
	@RequestMapping(value="/selectAllMember")
	public void selectAllMember(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");

		//logic 처리
		service = new BankSelectAllMemberService();
		model.addAttribute("dataSource", dataSource);
		service.execute(model); //처리 결과를 model에 담는다
		
		//결과 처리
		//model에서 꺼내기 ->편하게 하기 위해 hashmap형태로 바꿈
		ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");
		
		String json = null;
		ObjectMapper om = new ObjectMapper();
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		
	}
	
	@RequestMapping(value="/selectMember")
	public void selectMember(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");

		//logic 처리
		service = new BankSelectMemberService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("memberId", request.getParameter("memberId"));
		System.out.println("memberId" + request.getParameter("memberId"));
		service.execute(model); //처리 결과를 model에 담는다
		
		//결과 처리
		//model에서 꺼내기 ->편하게 하기 위해 hashmap형태로 바꿈
		BankDTO dto = (BankDTO) model.asMap().get("RESULT");
		
		String json = null;
		ObjectMapper om = new ObjectMapper();
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(dto);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		
	}
	
	@RequestMapping(value="/deposit")
	public void deposit(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");

		//logic 처리
		service = new BankDepositService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model); //처리 결과를 model에 담는다
		//결과 처리
		//model에서 꺼내기 ->편하게 하기 위해 hashmap형태로 바꿈
		boolean result = (Boolean) model.asMap().get("RESULT"); //Boolean은 rapper 형식. boolean으로 타입캐스팅하면 안 됨.

		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/withraw")
	public void withraw(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");

		//logic 처리
		service = new BankWithrawService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model); //처리 결과를 model에 담는다
		//결과 처리
		//model에서 꺼내기 ->편하게 하기 위해 hashmap형태로 바꿈
		boolean result = (Boolean) model.asMap().get("RESULT"); //Boolean은 rapper 형식. boolean으로 타입캐스팅하면 안 됨.

		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/transfer")
	public void transfer(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");
		model.addAttribute("request", request);		

		//logic 처리
		service = new BankTransferService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model); //처리 결과를 model에 담는다
		//결과 처리
		//model에서 꺼내기 ->편하게 하기 위해 hashmap형태로 바꿈
		boolean result = (Boolean) model.asMap().get("RESULT"); //Boolean은 rapper 형식. boolean으로 타입캐스팅하면 안 됨.

		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/checkMember")
	public void checkMember(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");
		String checkMemberId = request.getParameter("checkMemberId");

		//logic 처리
		service = new BankCheckMemberService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("checkMemberId", checkMemberId);
		service.execute(model); //처리 결과를 model에 담는다
		//결과 처리
		//model에서 꺼내기 ->편하게 하기 위해 hashmap형태로 바꿈
ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");
		
		String json = null;
		ObjectMapper om = new ObjectMapper();
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
}
