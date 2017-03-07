package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MongoDBConnection;
import db.MySQLDBConnection;

/**
 * Servlet implementation class SearchRestaurants
 */
@WebServlet("/restaurants")
public class SearchRestaurants extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchRestaurants() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			DBConnection connection = new MySQLDBConnection();
			// allow access only if session exists
			HttpSession session = request.getSession();
			if (session.getAttribute("user") == null) {
				response.setStatus(403);
				return;
			} 
			JSONArray array = null;
			// allow access only if session exists
			/*
			 * if (!RpcParser.sessionValid(request, connection)) {
			 * response.setStatus(403); return; }
			 */
			if (request.getParameterMap().containsKey("user_id")) {
				String userId = request.getParameter("user_id");
				Set<String> visited_business_id = connection.getVisitedRestaurants(userId);
				array = new JSONArray();
				for (String id : visited_business_id) {
					array.put(connection.getRestaurantsById(id, true));
				}
				RpcParser.writeOutput(response, array);
			} else {
				RpcParser.writeOutput(response, new JSONObject().put("status", "InvalidParameter"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// allow access only if session exists
				/* HttpSession session = request.getSession();
				if (session.getAttribute("user") == null) {
					response.setStatus(403);
					return;
				} */
	}

}
