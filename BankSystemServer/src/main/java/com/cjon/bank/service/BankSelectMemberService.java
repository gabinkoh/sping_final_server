package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;

public class BankSelectMemberService implements BankService {

	@Override
	public void execute(Model model) {
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		String memberId = (String) model.asMap().get("memberId");
		System.out.println("ex...memberId" + memberId);
		Connection con = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false); //트랜잭션 시작
			BankDAO dao = new BankDAO(con);
			BankDTO dto = dao.selectMember(memberId);
			if (dto != null) {
				con.commit();
			} else {
				con.rollback();
			}
			model.addAttribute("RESULT", dto);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}

}
