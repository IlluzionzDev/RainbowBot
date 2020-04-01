package com.illuzionzstudios.rainbowbot.command;

import com.illuzionzstudios.discordlibrary.command.type.Command;
import com.illuzionzstudios.rainbowbot.controller.RainbowController;
import com.illuzionzstudios.rainbowbot.embed.SetRoleEmbed;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;

/**
 * Copyright © 2020 Property of Illuzionz Studios, LLC
 * All rights reserved. No part of this publication may be reproduced, distributed, or
 * transmitted in any form or by any means, including photocopying, recording, or other
 * electronic or mechanical methods, without the prior written permission of the publisher,
 * except in the case of brief quotations embodied in critical reviews and certain other
 * noncommercial uses permitted by copyright law. Any licensing of this software overrides
 * this statement.
 */

/**
 * Command to enable rainbow
 */
public class RainbowCommand extends Command {

    public RainbowCommand() {
        super("rainbow");

        setPermission(Permission.ADMINISTRATOR);
    }

    @Override
    public void execute(Message message, String[] args) {
        // Not enough arguments
        if (args.length < 1) return;

        Guild guild = message.getGuild();

        if (args[0].equalsIgnoreCase("role")) {
            // Set the role to use
            if (args[1] != null) {
                if (message.getMentionedRoles().isEmpty()) return;
                Role role = message.getMentionedRoles().get(0);

                if (role != null) {
                    RainbowController.INSTANCE.getRoles().put(guild, role);
                    message.getTextChannel().sendMessage(new SetRoleEmbed().createEmbed(role)).queue();
                }
            }
        }
    }
}
