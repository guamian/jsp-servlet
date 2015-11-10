package model;

import beans.CustomerBean;
import beans.ItemBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataManager {
	private String dbURL = "";
	private String dbUserName = "";
	private String dbPassword = "";
	private String jdbcDriver = "";
	private Connection conn;
	private int idOfCurrentUser = 0;
	
	public DataManager() {
		
	}
	
	public DataManager(String jdbcDriver, String dbURL, String dbUserName, String dbPassword) {
		this.jdbcDriver = jdbcDriver;
		this.dbURL = dbURL;
		this.dbUserName = dbUserName;
		this.dbPassword = dbPassword;
		conn = getConnection();
	}
	
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	
    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }
    public String getDbURL() {
        return dbURL;
    }
    
    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }
    public String getDbUserName() {
        return dbUserName;
    }
    
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
    public String getDbPassword() {
        return dbPassword;
    }
    
    // Connect database
    public Connection getConnection() {
        Connection conn = null;
        
        try {
            Class.forName(getJdbcDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver ? ");
            e.printStackTrace();
        }
        
        try {
            conn = DriverManager.getConnection(getDbURL(), getDbUserName(), getDbPassword());
        } catch(SQLException e) {
            System.out.println("Could not connect to database");
            e.printStackTrace();
        }
        return conn;
    }
    
    // Close database connection
    public void closeConnection(Connection conn) {
    	
    }
    
    public boolean validate(String userName, String password) {
    	if(conn == null) {
    		conn = getConnection();
    	}
    	
    	if(conn != null) {
    		ResultSet rs = null;
    		Statement ps = null;
    		try {
    			ps = conn.createStatement();
    			rs = ps.executeQuery("select * from Customer ");
    			while(rs.next()) {
                    if(userName.equalsIgnoreCase(rs.getString("username")) &&
                       password.equalsIgnoreCase(rs.getString("password"))) {
                    	idOfCurrentUser = rs.getInt("id"); // Save id
                    	return true;
                    }
    			}
    			return false;
    			
    		} catch(SQLException ex) {
    			ex.printStackTrace();
    			return false;
    		}
    	}
		return false;
    }
    
    public void createCustomer(String userName, String password) {
		PreparedStatement ps = null;
        if(conn == null) {
        	conn = getConnection();
        }
        
        if(conn != null) {
        	try {
        		ps = conn.prepareStatement("insert into Customer(username,password) values(?, ?)");
    			ps.setString(1, userName);
    			ps.setString(2, password);
    			ps.execute();
    			//createShoppingCartForCustomer();
            } catch(SQLException ex) {
                ex.printStackTrace();
        	} finally {
        		try {
        			ps.close();
	            } catch(SQLException ex) {
	                ex.printStackTrace();
	            }
        	}
        }
    }
    
    /*
    // After a row of Customer added, always create an empty Shopping Cart that belongs to Customer.
    public void createShoppingCartForCustomer() {
		PreparedStatement ps = null;
        if(conn == null) {
        	conn = getConnection();
        }
        
        if(conn != null) {
        	try {
        		ps = conn.prepareStatement("insert into ShoppingCart() values()");

    			ps.execute();
            } catch(SQLException ex) {
                ex.printStackTrace();
        	} finally {
        		try {
        			ps.close();
	            } catch(SQLException ex) {
	                ex.printStackTrace();
	            }
        	}
        }
    }
    */
    public CustomerBean getCustomerData(String customerName) {
        CustomerBean customerBean = new CustomerBean();
        
        if(conn == null) {
        	conn = getConnection();
        }
        
        if(conn != null) {
            //ls.connOk(true); // print out the status of database connection
            ResultSet rs = null;
            Statement ps = null;
            try {
                //String strQuery = "select * from customer ";
                ps = conn.createStatement();
                rs = ps.executeQuery("select * from Customer ");
                
                // Pack up info. as a whole object, in order to return
                while(rs.next()) {
                	if(customerName.equalsIgnoreCase(rs.getString("username"))) {
                        customerBean.setId(rs.getInt("id"));
                        customerBean.setName(rs.getString("username"));
                        customerBean.setPassword(rs.getString("password"));
                	}
                }
            } catch(SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    rs.close();
                    ps.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
                closeConnection(conn);
            }
        }
        return customerBean;
    }
    
    public ArrayList<ItemBean> getFullItemList() {
    	ArrayList<ItemBean> listOfItems = new ArrayList<ItemBean>();
    	
    	if(conn == null) {
    		conn = getConnection();
    	}
    	
    	if(conn != null) {
            ResultSet rs = null;
            Statement ps = null;
            try {
	            ps = conn.createStatement();
	            rs = ps.executeQuery("select * from Item ");
                while(rs.next()) {
                	ItemBean item = new ItemBean();
                	item.setName(rs.getString("name"));
                	item.setPrice(rs.getDouble("price"));
                	listOfItems.add(item);
                }
            } catch(SQLException e) {
            	
    		} finally {
                try {
                    rs.close();
                    ps.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
                closeConnection(conn);
            }
    	}
    	return listOfItems;
    }
    
    public void addItemToShoppingCart(String userName, String itemName) {
    	ResultSet rs0 = null, rs1 = null;
		PreparedStatement ps0 = null, ps1 = null, ps2 = null;
		int customerid = 0, itemid = 0;
    	if(conn == null) {
    		conn = getConnection();
    	}
    	if(conn != null) {
    		try {
    			ps0 = conn.prepareStatement("select id from Customer where Customer.username = ? ");
    			ps0.setString(1, userName);
    			rs0 = ps0.executeQuery();
    			if(rs0.next() == true) {
    				customerid = rs0.getInt("id");
    			} else {
    				System.out.println("Error: Customer "+ userName +"not found!");
    			}
    			
    			ps1 = conn.prepareStatement("select id from Item where Item.name = ? ");
    			System.out.println(itemName);
    			ps1.setString(1, itemName);
    			rs1 = ps1.executeQuery();
    			if(rs1.next() == true) {
    				itemid = rs1.getInt("id");
    			} else {
    				System.out.println("Error: Item "+ itemName+"not found!");
    			}
    			
    			ps2 = conn.prepareStatement("insert into ShoppingCart(id, customerid, itemid) values(?,?,?) ");
    			ps2.setInt(1, customerid);
    			ps2.setInt(2, customerid);// yes, it is same as '1'
    			ps2.setInt(3, itemid);	
    			ps2.executeUpdate();
    		} catch(SQLException e) {
    			e.printStackTrace();
    		} finally {
    			try {
    				ps0.close();
    				ps1.close();
    				ps2.close();
    			} catch(SQLException e) {
    				
    			}
    		}
    	}
    }
    
    public ArrayList<ItemBean> showShoppingCart(String customerName) {
    	ArrayList<ItemBean> shoppingCartList = new ArrayList<ItemBean>();
    	if(conn == null) {
    		conn = getConnection();
    	}
    	if(conn != null) {
            ResultSet rs = null;
            Statement ps = null;
    		try {
	            ps = conn.createStatement();
	            rs = ps.executeQuery("select * from Item where id in (select itemid from ShoppingCart) ");
	            while(rs.next()) {
                	ItemBean item = new ItemBean();
                	item.setName(rs.getString("name"));
                	item.setPrice(rs.getDouble("price"));
                	System.out.println("in showShoppingCart:" + item.getName());
                	shoppingCartList.add(item);
	            }
    		} catch(SQLException e) {
    			
    		} finally {
                try {
                    rs.close();
                    ps.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
    		}
    	
    	}
    	return shoppingCartList;
    }
    
}
