package com.shu.chess.sounds;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * Created by Qjkobe on 2016/2/18.
 */
public class BasicPlayer extends Thread{

    private AudioInputStream ais=null;
    private AudioFormat af=null;
    private Clip clip=null;
    private SourceDataLine m_line;

    public BasicPlayer(String fileName){
        try {
            ais= AudioSystem.getAudioInputStream(getClass().getResourceAsStream(fileName));
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        play();
    }

    public void play(){
        af=ais.getFormat();

        if(af.getEncoding()!=AudioFormat.Encoding.PCM_SIGNED && af.getEncoding()!=AudioFormat.Encoding.PCM_UNSIGNED){
            System.out.println(af.getEncoding());
            System.out.println(AudioFormat.Encoding.PCM_SIGNED);
            AudioFormat decodedFormat=new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    af.getSampleRate(),
                    16,
                    af.getChannels(),
                    af.getFrameSize(),
                    af.getFrameRate(),
                    false//高序
            );
            ais=AudioSystem.getAudioInputStream(decodedFormat,ais);
        }
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, ais.getFormat(), AudioSystem.NOT_SPECIFIED);
            m_line = (SourceDataLine) AudioSystem.getLine(info);
            m_line.open(ais.getFormat(), m_line.getBufferSize());
            m_line.start();

            int numRead = 0;
            byte[] buf = new byte[m_line.getBufferSize()];
            while ((numRead = ais.read(buf, 0, buf.length)) >= 0) {
                int offset = 0;
                while (offset < numRead) {
                    offset += m_line.write(buf, offset, numRead - offset);
                }
            }
            m_line.drain();
            m_line.stop();
            m_line.close();
            ais.close();
        }catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getDuration(){
        return m_line.getBufferSize()/(m_line.getFormat().getFrameSize()*m_line.getFormat().getFrameRate());
    }

    public double getDecision(){
        return m_line.getMicrosecondPosition();
    }
}
