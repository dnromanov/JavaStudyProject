package util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import dao.RolesDAO;
import dao.RolesDAOImpl;

import static util.AppConstants.*;

import model.Role;

public class RoleURLMappingUtil {
	
	public static boolean isContains(Map<Role, List<String>> map, String path) {
		
		for (Entry<Role, List<String>> entry : map.entrySet()) {
			if(entry.getValue().contains(path)) {
                return true;
            }
		}
		return false;
	}
	
	public static Map<Role, List<String>> getMapping() {
		Map<Role, List<String>> map = new HashMap<>();
		String fileName = AppSettings.getInstance().getProperty(ROLE_URL_MAP_FILE);
		Properties props = new Properties();
		RolesDAO roleDAO = new RolesDAOImpl();
		try {
			InputStream in = RoleURLMappingUtil.class.getResourceAsStream(fileName);
			props.load(in);
			Enumeration<?> names = props.propertyNames();
			while (names.hasMoreElements()) {
				String roleName = (String) names.nextElement();
				Role role = null;
				if (roleName.equals(DEFAULT_ROLE)) {
					role = new Role(roleName);
				}
				else {
					role = roleDAO.getRoleByName(new Role(roleName));
				}
				List<String> paths = Arrays.asList(((String)props.get(roleName)).split(";"));
				map.put(role, paths);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(getMapping().size());
	}
}
