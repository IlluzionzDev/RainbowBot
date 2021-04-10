#include <iostream>
#include <string>
#include <vector>
#include <unordered_map>
#include <chrono>
#include "cdltypes.hpp"
using namespace std::chrono_literals;
using namespace CDL;



namespace rainbow {
    // Array of some nice rainbow color, so they're not random
    const std::vector<int> colors = {
        0xF54242,
        0xF54275,
        0xF542BF,
        0xAD42F5,
        0x7E42F5,
        0x42C2F5,
        0x42EFF5,
        0x42F5AD,
        0x42F542,
        0xB3F542,
        0xF5DD42,
        0xF5AD42,
        0xF57842
    };

    // Rainbow roles, must be set before doing anything
    std::unordered_map<CDL::CGuild, CDL::Role*> roles;

    // If rainbow is enabled
    bool enabled = true;
};



class Bot {
    static void rainbow(CMessage msg, CChannel channel, cmdargs& args) {
        auto guild = channel->get_guild();

        if (not guild->has_perm(msg->author, Permissions::ADMINISTRATOR)) {
            // Missing permission
            return;
        } else if (args.size() != 2) {
            // Not enough arguments
            return;
        }

        if (extras::tolowers(args[0]) == "role") {
            // Set the role to use
            if (msg->mention_roles.empty()) {
                return;
            }

            auto role = guild->roles.find(msg->mention_roles[0]);
            if (role != guild->roles.end()) {
                rainbow::roles[guild] = role->second;
                channel->send_embed({
                                        {"title", "Rainbow Bot"},
                                        {"fields", {
                                             {
                                                 {"name", "SUCCESS"},
                                                 {"value", "Set rainbow role to "+role->second->get_mention()},
                                                 {"inline", false}
                                             }
                                         }
                                        }
                                    });
            }
        }
    }

    boost::asio::steady_timer *rainbowTimer = nullptr;
    std::vector<int>::const_iterator color = rainbow::colors.begin();
    void rainbowLoop() {
        if (rainbowTimer) delete rainbowTimer;

        // Next color
        if (++color == rainbow::colors.end()) {
            color = rainbow::colors.begin();
        }

        // Iterate through all roles
        for (const auto& [guild, role] : rainbow::roles) {
            role->color = *color;
            env.bot->call("PATCH", "/guilds/"+std::to_string(guild->id)+"/roles/"+std::to_string(role->id), {{"color", *color}}, [] (const bool, nlohmann::json) {});
        }

        rainbowTimer = new boost::asio::steady_timer(*env.aioc, 800ms);
        rainbowTimer->async_wait([=] (...) {
            rainbowLoop();
        });
    }


public:
    Bot(int argc, char **argv) {
        register_command("rainbow", rainbow, 2);

        intents::ready.push_back([=] () {
            rainbowLoop();
        });
        handlers::get_prefix = [] (auto, auto cb) {
            cb(".");
        };

        using namespace intent_vals;
        CDL::main(argc, argv, GUILD_MESSAGES);
    }
};


int main(int argc, char **argv) {
    if (argc == 1) {
        std::cerr << "You must provide a bot token to start the bot" << std::endl;
        return EXIT_FAILURE;
    }

    env.settings["token"] = argv[1];

    Bot(argc, argv);
}
