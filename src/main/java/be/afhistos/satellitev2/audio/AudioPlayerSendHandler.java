package be.afhistos.satellitev2.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;

public class AudioPlayerSendHandler implements AudioSendHandler {
    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    public AudioPlayerSendHandler(AudioPlayer player){this.audioPlayer = player;}

    @Override
    public boolean canProvide() {
        if(lastFrame == null){
            lastFrame = audioPlayer.provide();
        }
        return lastFrame != null;
    }

    @Nullable
    @Override
    public ByteBuffer provide20MsAudio() {
        if(lastFrame == null){
            lastFrame = audioPlayer.provide();
        }
        byte[] data = lastFrame != null ? lastFrame.getData() : null;
        lastFrame = null;
        return ByteBuffer.wrap(data);
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
