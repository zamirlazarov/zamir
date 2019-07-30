package main;

import java.sql.SQLException;

import connections.ConnectionPool;
import facades.AdminFacade;
import facades.ClientType;
import facades.CompanyFacade;
import facades.CouponClientFacade;
import facades.CustomerFacade;

public class CouponSystem implements CouponClientFacade {

	private static CouponSystem instance = new CouponSystem();

	private AdminFacade adminFacade;

	private CompanyFacade companyFacade;

	private CustomerFacade customerFacade;

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	private DailyTask dailyTask;
	private Thread thread;

	private CouponSystem() {
		adminFacade = new AdminFacade();
		companyFacade = new CompanyFacade();
		customerFacade = new CustomerFacade();
		dailyTask = new DailyTask();
		thread = new Thread(dailyTask);
		thread.start();
	}

	public static CouponSystem getInstance() {
		return instance;
	}

	@Override
	public CouponClientFacade login(String userName, String password, ClientType type) throws Exception {
		switch (type) {
		case adminfacade:
			return adminFacade.login(userName, password, type);

		case companyfacade:
			return companyFacade.login(userName, password, type);

		case customerfacade:
			return customerFacade.login(userName, password, type);

		}
		return null;

	}
	public void shutdown() throws SQLException {
		connectionPool.closeAllConnections();
		dailyTask.stopTask();
	}
	}

