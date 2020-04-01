/**
 * Copyright © 2020 Property of Illuzionz Studios, LLC
 * All rights reserved. No part of this publication may be reproduced, distributed, or
 * transmitted in any form or by any means, including photocopying, recording, or other
 * electronic or mechanical methods, without the prior written permission of the publisher,
 * except in the case of brief quotations embodied in critical reviews and certain other
 * noncommercial uses permitted by copyright law. Any licensing of this software overrides
 * this statement.
 */
package com.illuzionzstudios.rainbowbot.proxy;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.TimerTask;

/**
 * Connect the bot to a proxy
 */
public class ProxyConnector extends TimerTask  {

    /**
     * Builder used for proxies
     */
    private JDABuilder builder;

    public ProxyConnector(JDABuilder builder) {
        this.builder = builder;
    }

    /**
     * @param builder Connect a bot builder to a proxy
     */
    public static void connectProxy(JDABuilder builder) throws Exception {
        // Set ip:port to be set
        String ip;
        int port;

        try {
            List<Pair<String, Integer>> proxies = ProxyScraper.getProxies();

            // Get first in list since we cycle them
            ip = proxies.get(0).getLeft();
            port = proxies.get(0).getRight();
            ProxyScraper.useProxy(ip);
        } catch (IOException ex) {
            System.out.println("Bot couldn't connect to proxy website");
            return;
        }

        // Connect proxy
        System.out.println("Connecting with proxy " + ip + ":" + port);
        final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        builder.setHttpClientBuilder(new OkHttpClient.Builder().proxy(proxy));

        builder.build();
    }

    @Override
    public void run() {
        // Try building
        boolean retry;
        do {
            try {
                ProxyConnector.connectProxy(builder);
                retry = false;
            } catch (Exception ex) {
                System.out.println("Proxy timed out... connecting with new proxy");
                retry = true;
            }
        } while (retry);
    }
}
