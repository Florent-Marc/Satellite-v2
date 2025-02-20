package be.afhistos.satellitev2.commands.music;

import be.afhistos.satellitev2.audio.AudioUtils;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class CommandSkip extends Command {

    public CommandSkip(){
        this.name = "skip";
        this.help = "Permet de sauter 1 ou plusieurs morceaux joués par le bot";
        this.guildOnly = true;
        this.category = new Category("Musique");
        this.arguments = "[nombre de sauts]";
    }
    @Override
    protected void execute(CommandEvent e) {
        int skip = 1;
        if(!e.getArgs().isEmpty()){
            try{
                skip = Integer.parseInt(e.getArgs());
            }catch (NumberFormatException ign){
                e.reply(e.getClient().getError()+" Nombre invalide! Saut de 1 morceau...");
            }
        }
        if(skip > AudioUtils.getInstance().getGuildAudioPlayer(e.getGuild()).scheduler.getQueue().size()){
            e.replyWarning("La playlist n'est pas aussi grande! arrêt de la musique en cours...", message -> {
                AudioUtils.getInstance().stopMusic(e.getGuild());
                message.addReaction(Emoji.fromUnicode(e.getClient().getSuccess())).queue();
            });
            return;

        }
        for (int i = 1; i <= skip; i++) {
            AudioUtils.getInstance().skipSong(e.getGuild());
        }
        e.reply("Saut de "+skip+" morceau(x).");
    }
}
