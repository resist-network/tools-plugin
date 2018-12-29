package net.resist.tools.Commands;
import net.resist.tools.Config;
import net.resist.tools.Main;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import java.util.Optional;
import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class setpassCMD implements CommandExecutor {
    private final Main plugin;
    public setpassCMD(Main instance) {
        plugin = instance;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Player player = (Player) src;
		String playerName = player.getName();
        plugin.sendMessage(player, Config.chatPrefix + Config.preLoginMsg);
        try {
            Optional<String> firstPass = args.<String>getOne("password");
            Optional<String> secondPass = args.<String>getOne("passwordAgain");
            String password = firstPass.get().toString();
            String passwordAgain = secondPass.get().toString();
            if (password.matches(passwordAgain)) {

            	
            	//start new
            	OkHttpClient client = new OkHttpClient();

            	MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
            	RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\ntest99\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
            	Request request = new Request.Builder()
            	  .url("https://resist.network/wp-json/wp/v2/users/21")
            	  .post(body)
            	  .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
            	  .addHeader("authorization", "Bearer "+plugin.wordpressToken)
            	  .addHeader("cache-control", "no-cache")
            	  .build();

            	Response response = client.newCall(request).execute();            	
            	//end new
            	
            	
				plugin.sendMessage(src, Config.chatPrefix + "Login Result: "+response.body().string());
	        } else {
	        	plugin.sendMessage(src, Config.chatPrefix + Config.passwordNoMatch);
	        }
        } catch (Exception e) {
        	plugin.sendMessage(src, Config.chatPrefix + "Login Error: "+e);
        }
        return CommandResult.success();
    }
}