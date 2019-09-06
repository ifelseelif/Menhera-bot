
package service.vk;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * @author Arthur Kupriyanov
 */


@Path("/vk/oauth")
public class OAuthService {
    @GET
    public void get(@QueryParam("access_token") String access_token,
                    @QueryParam("code") String code,
                    @QueryParam("state") String state){

    }
}

