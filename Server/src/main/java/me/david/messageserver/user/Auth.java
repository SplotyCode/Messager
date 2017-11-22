package me.david.messageserver.user;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import me.david.messagecore.netwok.packets.AuthPacket;
import me.david.messagecore.netwok.packets.AuthRespone;
import me.david.messagecore.netwok.packets.LoginPacket;
import me.david.messagecore.netwok.packets.LoginResponePacket;
import me.david.messagecore.utils.ChannelUtil;
import me.david.messagecore.utils.DigitUtil;
import me.david.messageserver.Server;
import me.david.messageserver.database.objects.DataBaseSession;
import me.david.messageserver.database.objects.DatabaseUser;
import me.david.messageserver.util.CryptoUtil;

public class Auth {

    private static String generateSessionid(String mac){
        String prefix =  mac + ";" + System.currentTimeMillis()/1000/60 + ";";
        while (true){
            String id = prefix + DigitUtil.getInstance().groups(3).digits(4);
            if(!Server.instance.connection.sessionIdExsits(id))
                return id;
        }
    }

    private static DataBaseSession getSessionbyUserAndMac(DatabaseUser user, String mac){
        for(String s : user.getSessions()) {
            DataBaseSession csession = Server.instance.connection.getSession(s);
            if (csession.getMac().equals(mac))
               return csession;
        }
        return null;
    }

    public static boolean handleLogin(LoginPacket packet, Channel channel){
        DatabaseUser user = Server.instance.connection.getUserbyName(packet.getUser());
        try {
            if(user != null && CryptoUtil.check(packet.getPassword(), user.getPassword())){
                DataBaseSession oldsession = getSessionbyUserAndMac(user, packet.getMac());
                boolean first = oldsession == null;
                final DataBaseSession session = oldsession != null?oldsession:new DataBaseSession();
                String sessionid = Auth.generateSessionid(packet.getMac());
                session.setOnline(true);
                session.setSession(sessionid);
                session.setLastonline(System.currentTimeMillis());
                if(first) Server.instance.asynccon.addSession(session, () -> ChannelUtil.sendPacket(new LoginResponePacket(sessionid, user.getId()), channel));
                else Server.instance.asynccon.updateSession(session, () -> ChannelUtil.sendPacket(new LoginResponePacket(sessionid, user.getId()), channel));
                channel.closeFuture().addListener((ChannelFutureListener) channelFuture -> {
                    session.setOnline(false);
                    session.setLastonline(System.currentTimeMillis());
                    Server.instance.asynccon.updateSession(session, () -> {});
                });
                return true;
            }else ChannelUtil.sendPacket(new LoginResponePacket(true), channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean handleAuth(AuthPacket packet, Channel channel){
        DatabaseUser user = Server.instance.connection.getUser(packet.getUserid());
        DataBaseSession session = getSessionbyUserAndMac(user, packet.getMac());
        if(user != null && session != null && session.getSession().equals(packet.getSession())){
            ChannelUtil.sendPacket(new AuthRespone(false), channel);
            return true;
        }
        ChannelUtil.sendPacket(new AuthRespone(true), channel);
        return false;
    }
}
