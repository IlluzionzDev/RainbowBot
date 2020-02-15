package com.illuzionzstudios.rainbowbot.controller;

/**
 * Copyright © 2020 Property of Illuzionz Studios, LLC
 * All rights reserved. No part of this publication may be reproduced, distributed, or
 * transmitted in any form or by any means, including photocopying, recording, or other
 * electronic or mechanical methods, without the prior written permission of the publisher,
 * except in the case of brief quotations embodied in critical reviews and certain other
 * noncommercial uses permitted by copyright law. Any licensing of this software overrides
 * this statement.
 */

import com.illuzionzstudios.discordlibrary.DiscordBot;
import com.illuzionzstudios.discordlibrary.controller.BotController;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles colour changing of roles
 */
public enum RainbowController implements BotController {
    INSTANCE;

    /**
     * Array of all nice rainbow colours, so we don't get random ones happening
     */
    @Getter
    public final ArrayList<Color> rainbowColours = new ArrayList<>();

    /**
     * The set rainbow role to use
     * Must be set before doing anything
     */
    @Setter
    @Getter
    private HashMap<Guild, Role> roles = new HashMap<>();

    /**
     * If rainbow is enabled
     */
    @Setter
    @Getter
    private boolean enabled = true;

    @Override
    public void initialise(DiscordBot discordBot) {
        rainbowColours.add(new Color(245, 66, 66));
        rainbowColours.add(new Color(245, 66, 117));
        rainbowColours.add(new Color(245, 66, 191));
        rainbowColours.add(new Color(173, 66, 245));
        rainbowColours.add(new Color(126, 66, 245));
        rainbowColours.add(new Color(66, 158, 245));
        rainbowColours.add(new Color(66, 194, 245));
        rainbowColours.add(new Color(66, 239, 245));
        rainbowColours.add(new Color(66, 245, 173));
        rainbowColours.add(new Color(66, 245, 66));
        rainbowColours.add(new Color(179, 245, 66));
        rainbowColours.add(new Color(245, 221, 66));
        rainbowColours.add(new Color(245, 173, 66));
        rainbowColours.add(new Color(245, 120, 66));

        new Thread(new RainbowThread()).start();
    }
}
