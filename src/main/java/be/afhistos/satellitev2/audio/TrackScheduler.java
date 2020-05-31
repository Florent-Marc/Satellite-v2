package be.afhistos.satellitev2.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TrackScheduler extends AudioEventAdapter {
    private boolean repeatingOnce = false;
    private boolean repeatingPlaylist = false;
    private final AudioPlayer player;
    private final Queue<AudioTrack> queue;
    AudioTrack lastTrack;

    public TrackScheduler(AudioPlayer player){
        this.player = player;
        this.queue = new LinkedList<>();
    }

    public void queue(AudioTrack track){
        if(!player.startTrack(track, true)){
            queue.offer(track);
        }
    }

    public LinkedList<AudioTrack> getQueue() {
        LinkedList<AudioTrack> linkedList = new LinkedList<>();
        for (AudioTrack track : queue){
            linkedList.offer(track);
        }
        return linkedList;
    }
    public void shuffle(){
        Collections.shuffle((List<?>) queue);
    }
    public void skip(){player.startTrack(queue.poll(), false);}

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        this.lastTrack = track;
        if(endReason.mayStartNext){
            if(repeatingOnce) {//Repeating one song only
                player.startTrack(lastTrack.makeClone(), false);
            }else if(repeatingPlaylist){
                queue(lastTrack.makeClone());
            }else{
                skip();
            }
        }
    }

    public boolean isRepeatingOnce() {
        return repeatingOnce;
    }

    public void setRepeatingOnce(boolean repeatingOnce) {
        this.repeatingOnce = repeatingOnce;
    }
    public boolean invertRepeatingOnce(){ //renvoie true si la repetition est finalement activée
        this.repeatingOnce = !this.repeatingOnce;
        this.repeatingPlaylist = !this.repeatingOnce;
        return this.repeatingOnce;
    }

    public boolean isRepeatingPlaylist() {
        return repeatingPlaylist;
    }

    public void setRepeatingPlaylist(boolean repeatingPlaylist) {
        this.repeatingPlaylist = repeatingPlaylist;
    }
    public boolean invertRepeatingPlaylist(){
        this.repeatingPlaylist = !this.repeatingPlaylist;
        this.repeatingOnce = !this.repeatingPlaylist;
        return this.repeatingPlaylist;
    }
    public void clearQueue(){
        queue.clear();
    }
}
