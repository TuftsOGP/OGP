package org.OpenGeoPortal.Security;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class IframeLoginController {
	@Autowired
	@Qualifier("formLoginService")
	LoginService loginService;
	
	private @Value("${ogp.domain}") String localDomain;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value="restricted/weblogin", method=RequestMethod.GET)
	@ResponseBody public ModelAndView getStatus() throws IOException {
		logger.debug("Login status checked");
		
		  String sendingPage = localDomain;
		  //create the model to return
		  ModelAndView mav = new ModelAndView("iframeLogin"); 
		  LoginStatus status = loginService.getStatus();
		  
		  ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		  String json = ow.writeValueAsString(status);
		  
		  //test
		  //json = json.replace("false", "true");
		  mav.addObject("authStatus", json);
		  mav.addObject("sendingPage", sendingPage);

		  return mav;
	}

}
