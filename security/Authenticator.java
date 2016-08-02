package security;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account;
import models.PasswordStorage;
import models.Queries;

public class Authenticator {

	public Authenticator() {

	}

	public Account login(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PasswordStorage.CannotPerformOperationException, PasswordStorage.InvalidHashException {
		Account account = null;
		
		Queries queries = new Queries();
		PasswordStorage passwordStorage = new PasswordStorage();
		
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		
		String hashedPassword = queries.getHashedPassword(username);
		
		if(passwordStorage.verifyPassword(password, hashedPassword)){
			account = queries.getAuthentication(username, hashedPassword);
			account.setIsLoggedIn(Boolean.TRUE);

			System.out.println(account.getUsername());
		}

		return account;
	}
}
