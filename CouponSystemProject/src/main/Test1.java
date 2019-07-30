package main;

import java.sql.Date;

import facades.AdminFacade;
import facades.ClientType;
import facades.CompanyFacade;
import facades.CustomerFacade;

public class Test1 {

	public static void Admin() throws Exception {

		System.out.println("start");
		CouponSystem couponSystem = CouponSystem.getInstance();
		AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234", ClientType.adminfacade);

		// adminFacade.insertCompany("Teva", "1234", "teva@gmail.com");
//		System.out.println(2);
		// adminFacade.insertCompany("TikunOlam", "1597", "TikunOlam@gmail.com");
		// adminFacade.insertCompany("Amdocs", "2345", "Amdocs@gmail.com");
//		adminFacade.insertCompany("BBB", "4567", "BBB@gmail.com");
//		adminFacade.insertCompany(127,"MOses", "98874", "moses@gmail.com");
//
		adminFacade.insertCustomer("BobiBoten", "7456");
		// adminFacade.insertCustomer(" Zamir Lazarev", "3112");
//		adminFacade.insertCustomer("Cristiano Ronaldo", "0707");
//		adminFacade.insertCustomer("Shuki Potash", "4458");
//		adminFacade.insertCustomer("Eli Taviv", "1556");
//		adminFacade.insertCustomer("Israel Israeli", "4898");

		// System.out.println("end");

		adminFacade.getallCompanies();
		adminFacade.getallCustomers();
//	
		// adminFacade.updateCompany((long) 1, "tevabari", "12345",
		// "tevabari@gmail.com");
		// adminFacade.getallCompanies();
		// adminFacade.updateCustomer((long) 1, "shuki", "5555");
//		
		// not working adminFacade.removeCompany(4);
		// adminFacade.removeCustomer(5);
		// adminFacade.getallCustomers();
		// adminFacade.removeCompany(2);
		// adminFacade.getallCompanies();
		couponSystem.getInstance().shutdown();

	}

	public static void Company() throws Exception {
		System.out.println("start");
		CouponSystem couponSystem = CouponSystem.getInstance();

		CompanyFacade companyFacade = (CompanyFacade) couponSystem.login("Teva", "1234", ClientType.companyfacade);
		Date startDate = new Date(DateEvents.getDateFromToday(0));
		Date endDate = new Date(DateEvents.getDateFromToday(1));

		//companyFacade.insertCoupon("Inbar", startDate, endDate, 20, CouponType.Health, "Good Indica", 2500, "");
		//companyFacade.insertCoupon("AK47", startDate, endDate, 20, CouponType.Health, "Good Indica", 4000, "");
		// companyFacade.insertCoupon("Hamburger", startDate, endDate, 20,
		// CouponType.Resturant, "200 Gram with bacon", 50,"");

		System.out.println("end");
		 //companyFacade.deleteCoupon(2);
		// companyFacade.getCouponsByPrice(4000);
		// companyFacade.getCompany();
		 companyFacade.updateCoupon(endDate, 3000, 6);
		// companyFacade.getCouponsByType(CouponType.Health);
		// companyFacade.getCouponsByEndDate(28, 07, 2019);
		couponSystem.getInstance().shutdown();

	}

	public static void Customer() throws Exception {
		CouponSystem couponSystem = CouponSystem.getInstance();
		CustomerFacade customerFacade = (CustomerFacade) couponSystem.login("Zamir Lazarev", "3112",
				ClientType.customerfacade);
		customerFacade.purchaseCouponById(1);
//	     customerFacade.getPurchaseHistory();
//	     customerFacade.getPurchaseHistoryByPrice(2500);
//	     customerFacade.getPurchaseHistoryByType(CouponType.Health);
		couponSystem.getInstance().shutdown();

	}
}
