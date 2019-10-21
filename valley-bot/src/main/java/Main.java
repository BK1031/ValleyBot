import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

import javax.security.auth.login.LoginException;
import java.util.List;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        CommandClientBuilder commandClientBuilder = new CommandClientBuilder();
        //our prefix is !!
        commandClientBuilder.setPrefix("!!");
        //"Type !!help"
        commandClientBuilder.useDefaultGame();
        commandClientBuilder.addCommand(new AddRole());

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(Constants.token);
        builder.addEventListener(new Main());
        builder.buildAsync();
    }

    public static class AddRole extends Command {
        public AddRole() {
            this.name = "helloworld";
            this.aliases = new String[]{"hw"};
            this.help = "says hello";
        }
        @Override
        protected void execute(CommandEvent commandEvent) {
            commandEvent.reply("Hello world!");
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String sender = event.getAuthor().getId();
        String message = event.getMessage().getContentRaw();
        String channel = event.getTextChannel().getName();
        GuildController gc = event.getGuild().getController();
        if (channel.equals("welcome")) {
            if (message.contains(",")) {
                String[] split = message.split(",");
                gc.setNickname(event.getMember(), split[0]).queue();
                List<Role> roles = event.getGuild().getRoles();
                if (split[1].equals("Freshman")) {
                    gc.addSingleRoleToMember(event.getMember(), roles.get(6)).queue();
                    gc.addSingleRoleToMember(event.getMember(), roles.get(8)).queue();
                } else if (split[1].equals("Sophomore")) {
                    gc.addSingleRoleToMember(event.getMember(), roles.get(5)).queue();
                    gc.addSingleRoleToMember(event.getMember(), roles.get(8)).queue();
                } else if (split[1].equals("Junior")) {
                    gc.addSingleRoleToMember(event.getMember(), roles.get(4)).queue();
                    gc.addSingleRoleToMember(event.getMember(), roles.get(8)).queue();
                } else if (split[1].equals("Senior")) {
                    gc.addSingleRoleToMember(event.getMember(), roles.get(3)).queue();
                    gc.addSingleRoleToMember(event.getMember(), roles.get(8)).queue();
                }

            }
            event.getTextChannel().deleteMessageById(event.getTextChannel().getLatestMessageId()).queue();
        }
    }

    /*@Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        GuildController gc = event.getGuild().getController();
        List<Role> roles = event.getGuild().getRoles();
        gc.addSingleRoleToMember(event.getMember(), roles.get(8)).queue();
    }*/
}
