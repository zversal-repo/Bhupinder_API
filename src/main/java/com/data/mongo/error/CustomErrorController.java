package com.data.mongo.error;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import com.data.mongo.error.ErrorInJson;

@RestController
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Value("${debug}")
    private boolean debug;

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH)
    private ErrorInJson error(HttpServletResponse response, WebRequest webrequest) {
	return new ErrorInJson(response.getStatus(), getErrorAttributes(webrequest, debug));
    }

    private Map<String, Object> getErrorAttributes(WebRequest webrequest, boolean includeStackTrace) {
	return errorAttributes.getErrorAttributes(webrequest, includeStackTrace);
    }

    @Override
    public String getErrorPath() {
	return PATH;
    }

}
