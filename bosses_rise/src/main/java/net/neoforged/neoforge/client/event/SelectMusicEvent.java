package net.neoforged.neoforge.client.event;

import net.minecraft.sounds.Music;

public class SelectMusicEvent {
    public Music getMusic() { return null; }
    public void setMusic(Music music) {}
    public void overrideMusic(Music music) {}
    public net.minecraft.client.resources.sounds.SoundInstance getPlayingMusic() { return null; }
}
