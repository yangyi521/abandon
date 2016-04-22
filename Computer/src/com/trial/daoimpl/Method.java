package com.trial.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.trial.bean.Computerstate;
import com.trial.bean.Computerstate1;
import com.trial.bean.ConcreteComputer;
import com.trial.bean.Dev_allocation;
import com.trial.bean.Manager;
import com.trial.util.ODConnection;

public class Method {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ODConnection odc = new ODConnection();

	// 管理员登陆方法
	public Manager manager_login_maneger(String username, String password) {
		Manager manager = new Manager();

		String sql = "{call login(?,?)}";
		try {
			con = odc.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				manager.setUsername(rs.getString("username"));
				manager.setPassword(rs.getString("password"));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return manager;
	}

	// 根据某一台电脑的id号查询电脑的所有信息
	public ConcreteComputer queryConcrete(String id) {
		ConcreteComputer concretecomputer = new ConcreteComputer();
		con = odc.getConnection();
		String sql = "{call queryall(?)}";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				concretecomputer.setDate_length(rs.getString("date_length"));
				concretecomputer.setDev_brand(rs.getString("dev_brand"));
				concretecomputer
						.setDev_condition(rs.getString("dev_condition"));
				concretecomputer.setDev_id(rs.getString("dev_id"));
				concretecomputer.setDev_model(rs.getString("dev_model"));
				concretecomputer.setDev_OS(rs.getString("dev_OS"));
				concretecomputer.setDev_remark(rs.getString("dev_remark"));
				concretecomputer.setDev_SN(rs.getString("dev_SN"));
				concretecomputer.setDev_type(rs.getString("dev_type"));
				concretecomputer.setRelease_date(rs.getDate("release_date"));
				concretecomputer.setRelease_end(rs.getDate("release_end"));
				concretecomputer.setRepair_start(rs.getDate("repair_start"));
				concretecomputer.setRepair_end(rs.getDate("repair_end"));
				concretecomputer.setSupervision(rs.getString("supervision"));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return concretecomputer;
	}

	// 获取全部电脑的状态，即查询电脑状态
	public List<Dev_allocation> querycmpborrow() {
		List<Dev_allocation> list = new ArrayList<Dev_allocation>();
		con = odc.getConnection();
		String sql = "{call queryborrow}";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Dev_allocation cps = new Dev_allocation();
				cps.setDev_id(rs.getString("dev_id"));
				cps.setDev_SN(rs.getString("dev_SN"));
				cps.setGiven_person(rs.getString("given_person"));
				cps.setMenber_no(rs.getString("menber_no"));
				cps.setRecover_person(rs.getString("recover_person"));
				cps.setGiven_time(rs.getString("given_time"));
				cps.setRecover_time(rs.getString("recover_time"));
				list.add(cps);
			}
			System.out.println("houlai de list:" + list);
			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 电脑的回收方法
	public boolean returncmp(String menber, String person) {
		boolean flag = false;
		con = odc.getConnection();
		String sql = "{call returncmp(?,?,?)}";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, time);
			pstmt.setString(2, person);
			pstmt.setString(3, menber);
			// zhixing
			int i = pstmt.executeUpdate();
			if (i > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	// 对电脑进行分配，及对表进行插入
	public boolean insert(Dev_allocation allocation) {
		boolean flag = false;
		con = odc.getConnection();
		String sql = "{call allocation(?,?,?,?,?)}";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, allocation.getDev_id());
			pstmt.setString(2, allocation.getDev_SN());
			pstmt.setString(3, allocation.getMenber_no());
			pstmt.setString(4, time);
			pstmt.setString(5, allocation.getGiven_person());

			int i = pstmt.executeUpdate();
			if (i > 0) {
				flag = true;
			}

			pstmt.close();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	// 根据电脑的操作系统查看电脑的属性，为员工分配新的电脑
	public List<Computerstate> querybyos(String os) {
		List<Computerstate> list = new ArrayList<Computerstate>();
		con = odc.getConnection();
		String sql = "{call querybyos(?)}";
		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, os);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Computerstate com = new Computerstate();
				com.setDev_id(rs.getString("dev_id"));
				com.setDev_SN(rs.getString("dev_SN"));
				com.setDev_brand(rs.getString("dev_brand"));
				com.setDev_model(rs.getString("dev_model"));
				com.setDev_condition(rs.getString("dev_condition"));
				com.setDev_type(rs.getString("dev_type"));
				com.setDev_OS(os);
				list.add(com);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 根据sn获取电脑编号及其电脑的属性相关的value
	public Computerstate1 querysn(String sn) {
		Computerstate1 com = null;
		String sql = "{call querybysn(?)}";
		con = odc.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sn);
			rs = pstmt.executeQuery();
			com = new Computerstate1();
			if (rs.next()) {
				com.setDev_SN(sn);
				com.setRelease_date(rs.getDate("release_date"));
				com.setRelease_end(rs.getDate("release_end"));
				com.setDate_length(rs.getString("date_length"));
				com.setRepair_start(rs.getDate("repair_start"));
				com.setRepair_end(rs.getDate("repair_end"));
				com.setSupervision(rs.getString("supervision"));
				com.setDev_remark(rs.getString("dev_remark"));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return com;
	}

	// 获取全部电脑的状态，即查询电脑状态
	public List<Computerstate> querycmpstate() {
		List<Computerstate> list = new ArrayList<Computerstate>();
		con = odc.getConnection();
		String sql = "{call query}";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Computerstate cps = new Computerstate();
				cps.setDev_id(rs.getString("dev_id"));
				cps.setDev_SN(rs.getString("dev_SN"));
				cps.setDev_brand(rs.getString("dev_brand"));
				cps.setDev_model(rs.getString("dev_model"));
				cps.setDev_type(rs.getString("dev_type"));
				cps.setDev_condition(rs.getString("dev_condition"));
				cps.setDev_OS(rs.getString("dev_os"));
				list.add(cps);
			}
			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 管理员登陆方法
	public boolean manager_login(String username, String password) {
		boolean flag = false;
		try {

			con = odc.getConnection();
			String sql = "{call login(?,?)}";
			pstmt = con.prepareStatement(sql);
			System.out.println("pstmt的值是：" + pstmt);

			pstmt.setString(1, username);
			pstmt.setString(2, password);
			System.out.println(username);
			System.out.println(password);
			rs = pstmt.executeQuery();
			System.out.println("rs的value" + rs);
			/*
			 * while(rs.next()){ Manager m = new Manager();
			 * m.setUsername(rs.getString("username"));
			 * m.setPassword(rs.getString("password"));
			 * System.out.println("数据库中匹配元素:"+m); }
			 */
			if (rs.next()) {
				flag = true;
				System.out.println("flag的值是：" + flag);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("管理员登陆异常");
		}
		return flag;
	}

}
