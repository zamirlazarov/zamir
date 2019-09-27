package connections;

import main.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

	private static ConnectionPool instance = new ConnectionPool();

	private static final String URL = "jdbc:mysql://localhost:3306/couponsystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT";

	private static final String userName = "root";

	private static final String password = "root";

	public final static int MAX_CONNECTIONS = 10;

	private BlockingQueue<Connection> blockingQueue;

	private ConnectionPool() {
		blockingQueue = createConnections();

	}

	public Connection getConnection() {
		synchronized (blockingQueue) {
			if (blockingQueue.isEmpty()) {
				System.out.println("Waiting for connection...");
				try {
					blockingQueue.wait();
				} catch (InterruptedException e) {
					e.getMessage();
				}
			} else {
				try {
					return blockingQueue.take();
				} catch (InterruptedException e) {
					e.getMessage();
				}
			}
			return null;
		}
	}

	public void returnConnection(Connection connection) throws SQLException, InterruptedException {
		synchronized (blockingQueue) {
			connection = DriverManager.getConnection(URL, userName, password);
			blockingQueue.put(connection);
			blockingQueue.notify();
		}
	}

	public void closeAllConnections() {
		synchronized (blockingQueue) {
			while (!blockingQueue.isEmpty()) {
				try {
					blockingQueue.element().close();
					blockingQueue.remove();
				} catch (SQLException e) {
					e.getMessage();
				}
			}
		}
	}

	private BlockingQueue<Connection> createConnections() {
		BlockingQueue<Connection> connect = new ArrayBlockingQueue<Connection>(MAX_CONNECTIONS);
		while (connect.size() < MAX_CONNECTIONS) {
			try {
				Connection connection = DriverManager.getConnection(URL, userName, password);
				connect.offer(connection);
			} catch (SQLException e) {
				e.getMessage();
			}
		}
		return connect;
	}

	public static ConnectionPool getInstance() {
		return instance;
	}
}
