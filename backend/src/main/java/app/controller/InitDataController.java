package app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import app.constants.Constant;
import app.mongo.model.AccessLevel;
import app.mongo.model.Roles;
import app.mongo.model.Users;
import app.repository.AccessLevelRepository;
import app.repository.RolesRepository;
import app.repository.UsersRepository;

@RestController
@RequestMapping("/api")
public class InitDataController extends BaseController {
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private AccessLevelRepository accLvlRepository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@RequestMapping(value = "/init_data", method = RequestMethod.GET)
	public ResponseEntity<String> initialData(HttpServletRequest request){
		JsonObject response;
		try {
			/* generate Access Level */
			AccessLevel accLvl = new AccessLevel();
			accLvl.setLevel(99);
			accLvl.setDescription("Can Do Anything");
			accLvlRepository.save(accLvl);
			
			Roles role = new Roles();
			role.setAccess_level(accLvl);
			role.setName("Super Admin");
			role.setStatus(true);
			role.setType("admin");
			rolesRepository.save(role);
			
			Users user = new Users();
			user.setUsername("adminmaribelajar");
			user.setPassword("adminmaribelajar123");
			user.setRoles(role);
			user.setStatus(true);
			user.setValidated(true);
			userRepository.save(user);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, "success Generate admin");
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

}
