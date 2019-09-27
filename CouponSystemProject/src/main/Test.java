package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import org.omg.CORBA.PUBLIC_MEMBER;

import beans.Company;
import beans.Customer;
import connections.ConnectionPool;
import dao.CompanyDAO;
import dao.CouponDAO;
import dbdao.CompanyDBDAO;
import dbdao.CustomerDBDAO;
import facades.AdminFacade;
import facades.ClientType;
import facades.CompanyFacade;
import facades.CouponClientFacade;
import facades.CustomerFacade;

public class Test {

	public static void main(String[] args) throws Exception {

		System.out.println("start");
		
		// DataBase.CreateAllTabales();
      //  DataBase.DropAllTables();

        System.out.println("end");
        
	//	Test1.Admin();
		
		 //Test1.Company();
		 
		 Test1.Customer();
	}

}
