package org.symbench.creointerferenceserver.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.symbench.creointerferenceserver.creo.InterferenceAnalyzer;
import org.symbench.creointerferenceserver.utils.LoggerFactory;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONHandler {
    ObjectMapper mapper = new ObjectMapper();

    private Map<String, JSONCommandHandler> commands = new HashMap();

    private static final Logger logger = LoggerFactory.getLogger(JSONHandler.class.getName());

    public JSONHandler() {
        commands.put(InterferenceHandler.COMMAND, new InterferenceHandler(InterferenceAnalyzer.getInstance()));
    }

    public String handleRequest(String request) {
        BaseRequest req = null;
        BaseResponse output = new BaseResponse();

        try {
            req = mapper.readValue(request, BaseRequest.class);
        } catch (Exception e) {
            createError(output, "Invalid JSON input" + request);
            try {
                return mapper.writeValueAsString(output);
            } catch (Exception ex) {
                e.printStackTrace();
                return null;
            }
        }

        try {
            output = handleRequest(req);
        } catch (Exception e) {
                createError(output, "Request Failed. " + e.getMessage());
                try {
                    return mapper.writeValueAsString(output);
                } catch (Exception ex) {
                    return null;
                }
        }

        if (output == null) {
            output = new BaseResponse();
        } if (output.getStatus() == null) {
            output.setStatus(new ResponseStatus());
        }

        try {
            return mapper.writeValueAsString(output);
        } catch (Exception e) {
            createError(output, "Invalid JSON output: " + request);
            try {
                return mapper.writeValueAsString(output);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    private BaseResponse handleRequest(BaseRequest request) throws Exception {
        BaseResponse resp = new BaseResponse();

        if (request==null) {
            createError(resp, "Empty request");
            return resp;
        }

        if(request.getCommand() == null) {
            createError(resp, "Request is missing the 'command' property");
            return resp;
        }

        JSONCommandHandler handler = commands.get(request.getCommand());


        if (handler==null) {
            createError(resp, "Invalid command: " + request.getCommand());
            return resp;
        }
        logger.info("Got Command " + request.getCommand() + ". Will be handled by " + handler.getClass().getName());

        Hashtable<String, Object> data = handler.handleFunction(request.getFunction(), request.getData());

        resp.setData(data);

        return resp;
    }

    private void createError(BaseResponse response, String message) {
        ResponseStatus status = new ResponseStatus();
        status.setError(true);
        status.setMessage(message);
        response.setStatus(status);
        logger.log(Level.WARNING, message);
    }
}
