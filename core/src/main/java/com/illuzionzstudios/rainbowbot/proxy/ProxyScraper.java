package com.illuzionzstudios.rainbowbot.proxy;

import net.dv8tion.jda.internal.utils.tuple.ImmutablePair;
import net.dv8tion.jda.internal.utils.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright © 2020 Property of Illuzionz Studios, LLC
 * All rights reserved. No part of this publication may be reproduced, distributed, or
 * transmitted in any form or by any means, including photocopying, recording, or other
 * electronic or mechanical methods, without the prior written permission of the publisher,
 * except in the case of brief quotations embodied in critical reviews and certain other
 * noncommercial uses permitted by copyright law. Any licensing of this software overrides
 * this statement.
 */

public class ProxyScraper {

    /**
     * List of proxies bot has already connected too, used
     * so we can find new ones if connected ones fail
     */
    public static ArrayList<String> loadedProxies = new ArrayList<>();

    /**
     * Call to store a used proxy
     *
     * TODO: Proxies are sooooooooooooooooooooo slow, get better ones
     *
     * @param ip Ip of the proxy
     */
    public static void useProxy(String ip) {
        // Clean array after a few used as it refreshes
        // Avoid using too much memory
        if (loadedProxies.size() >= 10) {
            loadedProxies.clear();
        }

        loadedProxies.add(ip);
    }

    /**
     * Scrape some proxies
     *
     * @return A pair of ip and port
     * @throws IOException Exception if can't connect to website
     */
    public static List<Pair<String, Integer>> getProxies() throws IOException {
        // Here we create a document object and use JSoup to fetch the website
        Document doc = Jsoup.connect("https://www.sslproxies.org/").get();

        // Get proxy list
        Elements proxies = doc.select("tbody > tr");

        List<Pair<String, Integer>> pairs = new ArrayList<>();

        for (Element element : proxies) {
            // Get list in proxy row
            Elements list = element.getElementsByTag("td");

            try {
                String ip = list.get(0).text();
                int port = Integer.parseInt(list.get(1).text());

                // Not an ip
                if (!ip.contains(".")) continue;

                if (loadedProxies.contains(ip)) continue;

                pairs.add(new Pair<String, Integer>() {
                    @Override
                    public String getLeft() {
                        return ip;
                    }

                    @Override
                    public Integer getRight() {
                        return port;
                    }
                });
            } catch (Exception ignored) {
            }

        }

        return pairs;
    }

}
