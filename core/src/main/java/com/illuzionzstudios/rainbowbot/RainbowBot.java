package com.illuzionzstudios.rainbowbot;

import com.illuzionzstudios.discordlibrary.DiscordApplication;
import com.illuzionzstudios.discordlibrary.DiscordBot;
import com.illuzionzstudios.rainbowbot.command.RainbowCommand;
import com.illuzionzstudios.rainbowbot.controller.RainbowController;
import com.illuzionzstudios.rainbowbot.proxy.ProxyScraper;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import okhttp3.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Copyright © 2020 Property of Illuzionz Studios, LLC
 * All rights reserved. No part of this publication may be reproduced, distributed, or
 * transmitted in any form or by any means, including photocopying, recording, or other
 * electronic or mechanical methods, without the prior written permission of the publisher,
 * except in the case of brief quotations embodied in critical reviews and certain other
 * noncommercial uses permitted by copyright law. Any licensing of this software overrides
 * this statement.
 */

public class RainbowBot extends DiscordBot implements DiscordApplication {

    /**
     * Instance of our bot
     */
    private static RainbowBot INSTANCE;

    /**
     * Bot token passed from command args
     */
    private static String TOKEN;

    public RainbowBot() {
        super();

        INSTANCE = this;
    }

    /**
     * Get the instance of the bot
     */
    public static RainbowBot getInstance() {
        return INSTANCE;
    }

    /**
     * Entry point of bot
     */
    public static void main(String[] args) {
        if (args[0] == null) {
            System.err.println("You must provide a bot token to start the bot");
            return;
        }

        TOKEN = args[0];

        new RainbowBot();
    }

    @Override
    public void registerServices() {
        registerApplication(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void buildApplication() {
    }

    @Override
    public boolean overrideBuild() {
        useCommands();

        setToken(TOKEN);

        // Try building
        boolean retry;
        do {
            retry = forceBuild();
        } while (retry);

        return true;
    }

    public boolean forceBuild() {
        try {
            String ip = "";
            int port = 0;

            try {
                List<Pair<String, Integer>> proxies = ProxyScraper.getProxies();
                // Get first in list since we cycle them
                ip = proxies.get(0).getLeft();
                port = proxies.get(0).getRight();
                ProxyScraper.useProxy(ip);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Connecting with proxy " + ip + ":" + port);
            final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            botBuilder.setHttpClientBuilder(new OkHttpClient.Builder().proxy(proxy));

            botBuilder.build();
            return false;
        } catch (Exception ex) {
            System.out.println("Proxy timed out... connecting with new proxy");
            return true;
        }
    }

    @Override
    public void loaded() {
        setCommandPrefix(".");

        registerCommand(new RainbowCommand());

        RainbowController.INSTANCE.initialise(this);
    }
}
