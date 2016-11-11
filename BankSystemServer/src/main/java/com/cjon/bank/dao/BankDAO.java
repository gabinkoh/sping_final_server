package com.cjon.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dto.BankDTO;

public class BankDAO {

	private Connection con;
	
	public BankDAO(Connection con) {
		this.con = con;
	}

	public ArrayList<BankDTO> selectAllMember() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankDTO> list = new ArrayList<BankDTO>();
		
		try {
//			//tomcat이 가지고 있는 기능이므로 tomcat의 context.xml에서 설정
//			Context ctx = new InitialContext();
//			DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/mySQLDB");
//			con = dataSource.getConnection();

			String sql = "select member_id, member_name, member_account, member_balance from bank_member_tb";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BankDTO dto = new BankDTO();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getInt("member_balance"));
				list.add(dto);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}

	public BankDTO selectMember(String memberId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BankDTO dto = new BankDTO();
		System.out.println("..." + memberId);
		try {
//			//tomcat이 가지고 있는 기능이므로 tomcat의 context.xml에서 설정
//			Context ctx = new InitialContext();
//			DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/mySQLDB");
//			con = dataSource.getConnection();

			String sql = "select member_id, member_name, member_account, member_balance from bank_member_tb where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				dto.setMemberId(rs.getString("member_id"));
				System.out.println("rs.getString(member_id)"+rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getInt("member_balance"));
				System.out.println("rs.getInt(member_balance)"+rs.getInt("member_balance"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}
	public boolean updateDeposit(String memberId, String memberBalance) {
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {

			String sql = "update bank_member_tb set member_balance = member_balance + ? where member_id = ?";
			pstmt = con.prepareStatement(sql);
			System.out.println("memberBalance.." + memberBalance);
			System.out.println("memberId.." + memberId);
			pstmt.setInt(1, Integer.parseInt(memberBalance));
			pstmt.setString(2, memberId);
			
			int count = pstmt.executeUpdate();
			
			if (count == 1) {
				result = true;
			} else {
				result = false;
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				pstmt.close(); //트랜잭션 커밋/롤백한 뒤에 커넥션을 클로즈해야하므로 여기서는 con.close() 하면 안 됨.
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public boolean updateWithraw(String memberId, String memberBalance) {
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {

			String sql = "update bank_member_tb set member_balance = member_balance - ? where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(memberBalance));
			pstmt.setString(2, memberId);
			
			int count = pstmt.executeUpdate();
			
			if (count == 1) {
				result = true;
			} else {
				result = false;
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				pstmt.close(); //트랜잭션 커밋/롤백한 뒤에 커넥션을 클로즈해야하므로 여기서는 con.close() 하면 안 됨.
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean checkBalance(String memberId, String memberBalance) {
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {

			String sql = "update bank_member_tb set member_balance = member_balance - ? where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(memberBalance));
			pstmt.setString(2, memberId);
			
			int count = pstmt.executeUpdate();
			
			if (count == 1) {
				result = true;
			} else {
				result = false;
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				pstmt.close(); //트랜잭션 커밋/롤백한 뒤에 커넥션을 클로즈해야하므로 여기서는 con.close() 하면 안 됨.
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public boolean updateHistory(String sendMemberId, String receiveMemberId, String transferBalance) {
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {

			String sql = "insert into bank_transfer_history_tb (transfer_money, send_member_id, receive_member_id)"
					+ " values (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(transferBalance));
			pstmt.setString(2, sendMemberId);
			pstmt.setString(3, receiveMemberId);
			
			int count = pstmt.executeUpdate();
			
			if (count == 1) {
				result = true;
			} else {
				result = false;
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				pstmt.close(); //트랜잭션 커밋/롤백한 뒤에 커넥션을 클로즈해야하므로 여기서는 con.close() 하면 안 됨.
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public ArrayList<BankDTO> checkMemberId(String checkMemberId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankDTO> list = new ArrayList<BankDTO>();
		System.out.println("  .."+checkMemberId);
		
		try {
//			//tomcat이 가지고 있는 기능이므로 tomcat의 context.xml에서 설정
//			Context ctx = new InitialContext();
//			DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/mySQLDB");
//			con = dataSource.getConnection();

			String sql = "select s.money, s.member_id, s.kind, m.member_balance from bank_statement_tb s join bank_member_tb m on s.member_id = m.member_id where s.member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, checkMemberId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BankDTO dto = new BankDTO();
				dto.setMemberId(rs.getString("s.member_id"));
				dto.setMemberName(rs.getString("s.kind"));
				dto.setMemberBalance(rs.getInt("s.money"));
				dto.setMemberAccount(rs.getString("m.member_balance"));
				list.add(dto);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}

	public boolean updateStatement(String memberId, String memberBalance, String statement) {
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {

			String sql = "insert into bank_statement_tb (money, member_id, kind)"
					+ " values (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(memberBalance));
			pstmt.setString(2, memberId);
			pstmt.setString(3, statement);
			
			int count = pstmt.executeUpdate();
			
			if (count == 1) {
				result = true;
			} else {
				result = false;
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				pstmt.close(); //트랜잭션 커밋/롤백한 뒤에 커넥션을 클로즈해야하므로 여기서는 con.close() 하면 안 됨.
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	
}
