package me.david.messageserver.user;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import me.david.messagecore.netwok.packets.AuthPacket;
import me.david.messagecore.netwok.packets.AuthRespone;
import me.david.messagecore.netwok.packets.LoginPacket;
import me.david.messagecore.netwok.packets.LoginResponePacket;
import me.david.messagecore.utils.ChannelUtil;
import me.david.messagecore.utils.DigitUtil;
import me.david.messageserver.Server;
import me.david.messageserver.database.objects.DatabaseUser;
import me.david.messageserver.util.CryptoUtil;

public class Auth {

    public static String generateSessionid(String mac){
        return mac + ";" + System.currentTimeMillis()/1000/60 + ";" + DigitUtil.getInstance().groups(3).digits(4);
    }

    public static boolean handleLogin(LoginPacket packet, Channel channel){
        DatabaseUser user = Server.instance.connection.getUserbyName(packet.getUser());
        try {
            if(user != null && CryptoUtil.check(packet.getPassword(), user.getPassword())){
                user.setOnline(true);
                String session = Auth.generateSessionid(packet.getMac());
                user.setSessionid(session);
                user.setLastonline(System.currentTimeMillis());
                Server.instance.asynccon.updateUser(user, () -> ChannelUtil.sendPacket(new LoginResponePacket(session, user.getId()), channel));
                channel.closeFuture().addListener(new ChannelFutureListener(){
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        user.setOnline(false);
                        user.setLastonline(System.currentTimeMillis());
                        Server.instance.asynccon.updateUser(user, () -> {});
                    }
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
        if(user != null && user.getSessionid().equals(packet.getUserid())){
            ChannelUtil.sendPacket(new AuthRespone(false), channel);
            return true;
        }
        ChannelUtil.sendPacket(new AuthRespone(true), channel);
        return false;
    }
}
