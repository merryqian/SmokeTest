package com.test.util;

import java.sql.*;

public class ConnectSqlite {
	Connection conn = null;

	public void getConnect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:Login.db");
			Statement stat = conn.createStatement();
			stat.executeUpdate("delete from test where username='merry';");
			stat.execute("insert into test values(2,'snail')");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	public static void main(String[] args) {
		ConnectSqlite cc = new ConnectSqlite();
		cc.getConnect();
	}
}
