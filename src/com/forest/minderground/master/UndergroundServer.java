package com.forest.minderground.master;

import java.lang.thread;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;


public class UndergroundServer extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public void UndergroundServer() {
        this.socket = new DatagramSocket(36523);
    }

    public void run() {
        running = true;

        while (running) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received = new String(packet.getData(), 0, packet.getLength());

            if (received.toLowerCase().equals("ready?")) {
                buf = msg.getBytes("ready.")
            }
            socket.send(packet);
        }
        socket.close();
    }
}
