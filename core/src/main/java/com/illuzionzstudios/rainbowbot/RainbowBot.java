package com.illuzionzstudios.rainbowbot;

import com.illuzionzstudios.discordlibrary.DiscordApplication;
import com.illuzionzstudios.discordlibrary.DiscordBot;
import com.illuzionzstudios.rainbowbot.command.RainbowCommand;
import com.illuzionzstudios.rainbowbot.controller.RainbowController;
import com.illuzionzstudios.rainbowbot.proxy.ProxyConnector;
import com.illuzionzstudios.rainbowbot.proxy.ProxyScraper;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import okhttp3.OkHttpClient;

import javax.security.auth.login.LoginException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

        // Repeating task to connect proxies
        new Timer().schedule(new ProxyConnector(botBuilder), 0, 5 * 60 * 1000);

        return true;
    }

    @Override
    public void loaded() {
        // Set command prefix
        setCommandPrefix(".");

        // Register commands
        registerCommand(new RainbowCommand());

        RainbowController.INSTANCE.initialise(this);
    }
}
