This project is archived as rainbow roles are against Discord TOS [https://twitter.com/discord/status/1055182857709256704?lang=en](https://twitter.com/discord/status/1055182857709256704?lang=en)

# RainbowBot

This is a discord bot which allows you to make roles that change colours (like a rainbow role). This can used for cosmetic or for special people in your server.

![Rainbow roles](https://i.imgur.com/EM5W99R.gif)

Disclaimer: This is currently experimental and a lot of features are partially working or need a lot of work on. This means the latest build works (but may have errors or run poorly). I'm trying to work on it making it more optimized and usable. The main system works, it's just the proxies that are sort of bugged as they are free ones so time out a lot. I will work on adding an option to add your own lists of proxies so you can run the bot on your own proxies.

This may mean there will be a lot of errors so don't come complaining saying there's a lot of errors (I know). Should you encounter any major errors or it stops working, just restart the bot. 

Note: This bot MUST run on proxies to bypass discord's rate limit. Also, you must run this bot yourself due to the more servers meaning more workload and requests.

## Getting the jar

In order to use and host the bot, you must get your own jar. First, go to the branch for which build you would like, eg Development, Feature-xxx, Stable.

Use the command

```bash
git clone https://github.com/IlluzionzDev/RainbowBot.git
```
to import the repository to your computer

Then run
```maven
mvn clean install
```
to build the jar. The final jar will be in `/core/target/RainbowBot.jar`. This is your jar you will use

## Running the bot

Now that you have your jar, you can start running the bot and using it.

First, you will have to create a discord application to run your own bot. So go to [Discord Developer Portal](https://discordapp.com/developers/applications) and make a new application. Do this by clicking on the `New Application` button.

Follow this [tutorial](https://discordpy.readthedocs.io/en/latest/discord.html) to create your bot user. You will want to save the bot token which you can find in the bot tab under the Username.

Now that you have your bot and your token you can run the bot.

Run a command line in the directory with your jar file, run this command here to start the bot
```bash
java -cp RainbowBot.jar com.illuzionzstudios.rainbowbot.RainbowBot TOKEN
```
replace TOKEN with your bot token. Now your bot should be online

## Usage
Once the bot is in your server and online, simply use `.rainbow role @Role`. This will set the rainbow role to change. In order for it to work, you must have the self-assigned bot role ABOVE this rainbow role. This is in order for it to modify it. Also to use the command you must have administrator permissions.

## Contributing
Feel free to submit a pull request and help with the development of this bot
