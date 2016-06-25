package com.ailk.cake.frame.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.cake.frame.exception.DAOException;

public class DbUtils {

	private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);

	/**
	 * 数据库操作,
	 */
	public static Connection getConnection() throws DAOException {
		return DbConnection.getConnection();
	}

	// 将结果级转成List
	private static List<String[]> transtoStrsList(ResultSet rs) throws SQLException {
		List<String[]> ls = new ArrayList<String[]>();
		int cols = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			String strs[] = new String[cols];
			for (int i = 1; i <= cols; i++) {

				strs[i - 1] = rs.getString(i);
			}
			ls.add(strs);
		}
		return ls;

	}

	/**
	 * @param rs
	 *            二维结果
	 * @return list.get() 后得到的是 String[] 第一行是字段的Label ，第二行开始才是数据
	 */

	private static List<String[]> transtoStrsLabelList(ResultSet rs) throws SQLException {
		List<String[]> ls = new ArrayList<String[]>();
		int cols = rs.getMetaData().getColumnCount();
		String strs1[] = new String[cols];
		for (int i = 0; i < cols; i++) {
			strs1[i] = rs.getMetaData().getColumnLabel(i + 1);
		}
		ls.add(strs1);
		while (rs.next()) {
			String strs[] = new String[cols];
			for (int i = 1; i <= cols; i++) {

				strs[i - 1] = rs.getString(i);
			}
			ls.add(strs);
		}
		return ls;

	}

	// 查询得到List String[]格式
	public static List<String[]> getStrsList(String sql) {
		List<String[]> ls = new ArrayList<String[]>();
		Connection conn = null;

		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ls = transtoStrsList(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("执行查询方法异常");
		} finally {
			DbConnection.close(conn, ps, rs);
		}
		return ls;
	}

	public static List<String[]> getStrsList(Connection conn, String sql) {
		List<String[]> ls = new ArrayList<String[]>();

		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			// log.info("sql:" + sql);
			rs = ps.executeQuery();
			ls = transtoStrsList(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("执行查询方法异常");
		} finally {
			DbConnection.close(null, ps, rs);
		}
		return ls;
	}

	public static List<String[]> getStrsLabelList(String sql) {
		List<String[]> ls = new ArrayList<String[]>();
		Connection conn = null;

		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			// log.info("sql:" + sql);
			rs = ps.executeQuery();
			ls = transtoStrsLabelList(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("执行查询方法异常");
		} finally {
			DbConnection.close(conn, ps, rs);
		}
		return ls;
	}

	public static int update(String sql) {
		int n = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			logger.info("sql:" + sql);
			n = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("执行更新方法异常");
		} finally {
			DbConnection.close(conn, ps, null);
		}
		return n;
	}

	public static int update(Connection conn, String sql) {
		int n = 0;

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			// log.info("sql:" + sql);
			n = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("执行更新方法异常");
		} finally {
			DbConnection.close(null, ps, null);
		}
		return n;
	}

	public static int updateBatch(Connection conn, List<String> sqlList) {
		int n = 0;

		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			for (int i = 0; i < sqlList.size(); i++) {
				String sql = sqlList.get(i);
				st.addBatch(sql);
			}
			st.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("批量执行更新方法异常");
		} finally {
			DbConnection.close(null, st, null);
		}
		return n;
	}

	public static int updateBatch(List<String> sqlList) {
		int n = 0;
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			for (int i = 0; i < sqlList.size(); i++) {
				String sql = sqlList.get(i);
				st.addBatch(sql);
			}
			st.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("批量执行更新方法异常");
		} finally {
			DbConnection.close(conn, st, null);
		}
		return n;
	}

	// 得到一个String[]
	public static String[] getStrs(String sql) {
		List<String[]> list = getStrsList(sql);
		if (list.size() == 0) {
			return null;
		}
		return (String[]) list.get(0);
	}

	// 得到一个String[]
	public static String[] getStrs(Connection conn, String sql) {
		List<String[]> list = getStrsList(conn, sql);
		if (list.size() == 0) {
			return null;
		}
		return (String[]) list.get(0);
	}

	// 得到一个String
	public static String getString(String sql) {
		String str[] = getStrs(sql);
		if (str == null) {
			return null;
		}
		return str[0];
	}

	// 得到一个String
	public static String getString(Connection conn, String sql) {
		String str[] = getStrs(conn, sql);
		if (str == null) {
			return null;
		}
		return str[0];
	}

	// 日期转换方法
	protected java.util.Date transString2Date(String s, String format) {
		DateFormat df = new SimpleDateFormat(format);
		java.util.Date date = null;
		try {
			date = df.parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 功能：填充固定的长度。
	 * 
	 * @param src
	 * @param token
	 *            ：填充的字符串
	 * @param type
	 *            ：0--左填充，1--右填充
	 * @param max_len
	 *            ：返回的最大长度
	 * @return
	 */

	public static String pad(String src, String token, int type, int max_len) {
		String des = "";
		if (src.length() < max_len) {
			for (int i = 0; i < max_len - src.length(); i++) {
				des += token;
			}
			if (type == 0) {
				des = des + src;
			} else {
				des = src + des;
			}
		} else {
			des = src.substring(0, max_len);
		}

		return des;
	}

	// 将List转换成String,供局部刷新select用
	public static String toOptionString(List<String[]> list) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			String[] str = list.get(i);
			ret.append(str[0].trim() + "/" + str[1].trim() + ",");
		}
		return ret.toString();
	}

	// 将List转换成String,供局部刷新select用,包含all
	public static String toAllOptionString(List<String[]> list) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			String[] str = list.get(i);
			ret.append(str[0].trim() + "/" + str[1].trim() + ",");
		}
		return "**/全部...," + ret.toString();
	}

	public static String toDispString(List<String[]> list) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			String[] str = list.get(i);
			ret.append(str[0].trim() + ",");
		}
		if (ret.length() == 0)
			return "";
		return ret.substring(0, ret.length() - 1);
	}

	/**
	 * 根据指定的序列的名字得到序列的值
	 * 
	 * @param sequenceName
	 *            序列的名字
	 * @return 返回序列的值
	 */
	public static String getSequenceNextValue(String sequenceName) {
		String sql = "select " + sequenceName + ".nextVal from dual";
		String nextval = DbUtils.getString(sql).trim();
		return nextval;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
